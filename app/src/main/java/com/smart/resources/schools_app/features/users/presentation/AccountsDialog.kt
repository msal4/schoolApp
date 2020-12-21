package com.smart.resources.schools_app.features.users.presentation

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.callbacks.SwipeAdapter
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.databinding.DialogAccountsBinding
import com.smart.resources.schools_app.features.login.LoginActivity
import com.smart.resources.schools_app.features.users.data.model.Account
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountsDialog : DialogFragment(),
    AccountsRecyclerAdapter.OnItemClickListener {
    private lateinit var binding: DialogAccountsBinding
    private lateinit var adapter: AccountsRecyclerAdapter
    private var onAccountChanged: (() -> Unit)? = null
    private val viewModel:AccountsViewModel by viewModels()

    fun setOnAccountChanged(onFinish: (() -> Unit)) {
        this.onAccountChanged = onFinish
    }

    companion object {
        private const val MAX_ACCOUNTS = 6

        fun newInstance(): AccountsDialog {
            return AccountsDialog()
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
        dialog?.window?.setLayout(width, WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        }
        binding = DialogAccountsBinding.inflate(inflater, container, false)
        adapter = AccountsRecyclerAdapter()
        adapter.setListener(this)
        binding.accountsRecycler.adapter= adapter

        setupUsers()
        viewModel.logoutEvent.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { eventHappened ->
                if(eventHappened){
                    requireActivity().setResult(Activity.RESULT_CANCELED)
                    requireActivity().finishAffinity()
                    LoginActivity.newInstance(requireContext())
                }
            }
        }

        val touchHelper = ItemTouchHelper(SwipeAdapter(onSwiped= ::onSwiped))
        touchHelper.attachToRecyclerView(binding.accountsRecycler)

        binding.addAccountBtn.setOnClickListener {
            if (adapter.itemCount >= MAX_ACCOUNTS) {
                binding.rootLayout.showSnackBar(getString(R.string.max_accounts_error))
            } else {
                LoginActivity.newInstance(requireContext(), true)
            }
        }

        return binding.root
    }

    private fun setupUsers() {
        viewModel.accounts.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        viewModel.currentUserId.observe(viewLifecycleOwner){
            if (it != null) {
                adapter.setSelectedUser(it)
            }
        }
    }

    private fun onSwiped(swipeDirection:Int, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is AccountsRecyclerAdapter.MyViewHolder) {
            viewHolder.binding.itemModel?.userId?.let {
                viewModel.deleteUser(it.toString())
            }
        }
    }

    override fun onItemClick(Account: Account) {
        if (Account.userId.toString() != viewModel.currentUserId.value) {
            UserRepository.instance.setCurrentAccount(Account)
            onAccountChanged?.invoke()
        }

        dismiss()
    }
}

