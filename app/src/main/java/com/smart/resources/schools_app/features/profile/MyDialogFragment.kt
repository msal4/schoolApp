package com.smart.resources.schools_app.features.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.core.adapters.SwipeAdapter
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.utils.showSnackBar
import com.smart.resources.schools_app.databinding.DialogAccountsBinding
import com.smart.resources.schools_app.features.login.CanLogout


class MyDialogFragment : DialogFragment(), CanLogout, AccountsRecyclerAdapter.OnItemClickListener {
    private lateinit var binding: DialogAccountsBinding
    private lateinit var adapter: AccountsRecyclerAdapter
    private var onFinish: (() -> Unit)? = null

    fun setOnFinish(onFinish: (() -> Unit)) {
        this.onFinish = onFinish
    }

    companion object {
        private lateinit var list1: MutableList<User>
        private var isLogout1: Boolean = false
        fun newInstance(list: MutableList<User>, isLogout: Boolean): MyDialogFragment {
            list1 = list
            isLogout1 = isLogout
            return MyDialogFragment()
        }
    }

    override fun onStart() {
        super.onStart()

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val maxHeight = (resources.displayMetrics.heightPixels * 0.70).toInt()

        dialog!!.window!!.setLayout(width, WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAccountsBinding.inflate(inflater, container, false).apply {
            adapter = AccountsRecyclerAdapter(list1, this@MyDialogFragment)
            ls.adapter = adapter
            lifecycleOwner = this@MyDialogFragment
        }

        if (isLogout1) {
            binding.addAccountBtn.visibility = View.GONE
        } else {
            binding.addAccountBtn.visibility = View.VISIBLE
        }


        val touchHelper = ItemTouchHelper(SwipeAdapter(::onSwipe))

        touchHelper.attachToRecyclerView(binding.ls)

        binding.addAccountBtn.setOnClickListener(View.OnClickListener {
            if (adapter.itemCount >= 6) {
                binding.linearId.showSnackBar("الحد الاقصى هو 6 حسابات \n(قم بحذف احدهم)")
            } else {
                if (activity is AppCompatActivity) {
                    logout(activity as AppCompatActivity)
                }
            }
        })
        return binding.root
    }


    private fun onSwipe(viewHolder: RecyclerView.ViewHolder) {

        if (viewHolder is AccountsRecyclerAdapter.MyViewHolder) {
            if (viewHolder.binding.itemModel?.uid != SharedPrefHelper.instance?.currentUser)
                viewHolder.binding.itemModel?.uid?.let {
                    deleteUser(
                        it
                    )
                    adapter.removeItem(viewHolder.adapterPosition)

                }
            else {
                viewHolder.binding.itemModel?.uid?.let {
                    deleteUser(
                        it
                    )
                    adapter.removeItem(viewHolder.adapterPosition)
                    if (activity is AppCompatActivity) {
                        logout(activity as AppCompatActivity)
                    }
                }
            }
        }

    }

    fun deleteUser(uid: Int) {
        AccountManager.instance?.deleteUser(uid)
    }


    override fun onItemClick(user: User) {

        if (user.uid == SharedPrefHelper.instance?.currentUser) {
            dismiss()
            return
        }
        SharedPrefHelper.instance?.currentUser = user.uid
        AccountManager.instance?.setCurrentUser(user)
        dismiss()
        onFinish?.let { it() }
    }


}

