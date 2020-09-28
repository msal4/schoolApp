package com.smart.resources.schools_app.features.users.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.callbacks.SwipeAdapter
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.databinding.DialogAccountsBinding
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.login.LoginActivity
import com.smart.resources.schools_app.features.users.data.UserAccount
import com.smart.resources.schools_app.features.users.data.UserRepository
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AccountsDialog : DialogFragment(), CanLogout,
    AccountsRecyclerAdapter.OnItemClickListener {
    private lateinit var binding: DialogAccountsBinding
    private lateinit var adapter: AccountsRecyclerAdapter
    private val accountsManager = UserRepository.instance
    private var onAccountChanged: (() -> Unit)? = null

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
        setupUsers()

        val touchHelper = ItemTouchHelper(SwipeAdapter(::onSwipe))
        touchHelper.attachToRecyclerView(binding.accountsRecycler)


        binding.addAccountBtn.setOnClickListener {

            if (adapter.itemCount >= MAX_ACCOUNTS) {
                binding.rootLayout.showSnackBar(getString(R.string.max_accounts_error))
            } else {
                context?.let { LoginActivity.newInstance(it, true) }
            }
        }

        return binding.root
    }

    private fun setupUsers() {
        lifecycleScope.launch {
            val users = accountsManager.getUsers().toMutableList()
            withContext(Main) {
                adapter =
                    AccountsRecyclerAdapter(users, this@AccountsDialog)
                binding.accountsRecycler.adapter = adapter
            }
        }
    }


    private fun onSwipe(viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is AccountsRecyclerAdapter.MyViewHolder) {
            viewHolder.binding.itemModel?.uid?.let {
                accountsManager.deleteUser(it)
                adapter.removeItem(viewHolder.adapterPosition)
                logoutIfCurrentUser(viewHolder)
            }
        }
    }

    private fun logoutIfCurrentUser(viewHolder: AccountsRecyclerAdapter.MyViewHolder) {
        lifecycleScope.launch {
            if (viewHolder.binding.itemModel?.uid == accountsManager.getCurrentUserAccount()?.uid) {
                withContext(Main) { context?.let { logout(it) } }
            }
        }
    }

    override fun onItemClick(UserAccount: UserAccount) {
        if (UserAccount != accountsManager.getCurrentUserAccount()) {
            UserRepository.instance.setCurrentUser(UserAccount)
            onAccountChanged?.invoke()
        }

        dismiss()
    }
}

