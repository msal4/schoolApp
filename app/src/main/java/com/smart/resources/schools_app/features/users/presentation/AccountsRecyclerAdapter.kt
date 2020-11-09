package com.smart.resources.schools_app.features.users.presentation

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ItemAccountBinding
import com.smart.resources.schools_app.features.users.data.UserAccount

class AccountsRecyclerAdapter : ListAdapter<UserAccount, AccountsRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK){

    private var listener: OnItemClickListener?= null

    fun setListener(listener: OnItemClickListener){
        this.listener= listener
    }

    interface OnItemClickListener {
        fun onItemClick(UserAccount: UserAccount)
    }

    private var selectedUserId:String?= null
    fun setSelectedUser(userId:String){
        selectedUserId= userId
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(accountModel: UserAccount) {
            binding.itemModel = accountModel
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemAccountBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { user ->

            holder.itemView.setOnClickListener{
                listener?.onItemClick(user)
            }
            holder.bind(user)

            if(user.uid== selectedUserId){
                holder.binding.profileImage.apply {
                    borderColor= ContextCompat.getColor(holder.itemView.context, R.color.light_green_a700)
                    borderWidth= 2
                }

            }

        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<UserAccount> =
            object : DiffUtil.ItemCallback<UserAccount>() {
                override fun areItemsTheSame(
                    oldItem: UserAccount,
                    newItem: UserAccount
                ): Boolean {
                    return oldItem.uid == newItem.uid
                }

                override fun areContentsTheSame(
                    oldItem: UserAccount,
                    newItem: UserAccount
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

}