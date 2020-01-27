package com.smart.resources.schools_app.features.users

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
import com.smart.resources.schools_app.core.adapters.SwipeAdapter
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.databinding.DialogAccountsBinding
import com.smart.resources.schools_app.features.login.CanLogout
import com.smart.resources.schools_app.features.profile.User
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UsersDialog : DialogFragment(), CanLogout,
    UsersRecyclerAdapter.OnItemClickListener {
    private lateinit var binding: DialogAccountsBinding
    private lateinit var adapter: UsersRecyclerAdapter
    private val accountsManager = UsersRepository.instance
    private var onAccountChanged: (() -> Unit)? = null

    fun setOnAccountChanged(onFinish: (() -> Unit)) {
        this.onAccountChanged = onFinish
    }

    companion object {
        private const val MAX_ACCOUNTS = 6

        fun newInstance(): UsersDialog {

            return UsersDialog()
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog?.window?.setLayout(width, WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = DialogAccountsBinding.inflate(inflater, container, false)
        setupUsers()

        val touchHelper = ItemTouchHelper(SwipeAdapter(::onSwipe))
        touchHelper.attachToRecyclerView(binding.accountsRecycler)


        binding.addAccountBtn.setOnClickListener {

            if (adapter.itemCount >= MAX_ACCOUNTS) {
                binding.rootLayout.showSnackBar(getString(R.string.max_accounts_error))
            } else {
                context?.let { con -> logout(con) }
            }
        }

        return binding.root
    }

    private fun setupUsers() {
        lifecycleScope.launch {
            val users = accountsManager.getUsers()?.toMutableList()
            withContext(Main) {
                adapter =
                    UsersRecyclerAdapter(users, this@UsersDialog)
                binding.accountsRecycler.adapter = adapter
            }
        }
    }


    private fun onSwipe(viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is UsersRecyclerAdapter.MyViewHolder) {
            viewHolder.binding.itemModel?.uid?.let {
                accountsManager.deleteUser(it)
                adapter.removeItem(viewHolder.adapterPosition)
                logoutIfCurrentUser(viewHolder)
            }
        }
    }

    private fun logoutIfCurrentUser(viewHolder: UsersRecyclerAdapter.MyViewHolder) {
        lifecycleScope.launch {
            if (viewHolder.binding.itemModel?.uid == accountsManager.getCurrentUser()?.uid) {
                withContext(Main) { context?.let { logout(it) } }
            }
        }
    }

    override fun onItemClick(user: User) {
        if (user != accountsManager.getCurrentUser()) {
            UsersRepository.instance.setCurrentUser(user)
            onAccountChanged?.invoke()
        }

        dismiss()
    }
}

