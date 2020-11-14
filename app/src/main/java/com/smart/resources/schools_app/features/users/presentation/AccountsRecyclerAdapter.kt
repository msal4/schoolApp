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
import com.smart.resources.schools_app.features.users.data.model.Account

class AccountsRecyclerAdapter : ListAdapter<Account, AccountsRecyclerAdapter.MyViewHolder>(DIFF_CALLBACK){

    private var listener: OnItemClickListener?= null

    fun setListener(listener: OnItemClickListener){
        this.listener= listener
    }

    interface OnItemClickListener {
        fun onItemClick(Account: Account)
    }

    private var selectedUserId:String?= null
    fun setSelectedUser(userId:String){
        selectedUserId= userId
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(accountModel: Account) {
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

            if(user.userId.toString() == selectedUserId){
                holder.binding.profileImage.apply {
                    borderColor= ContextCompat.getColor(holder.itemView.context, R.color.light_green_a700)
                    borderWidth= 2
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Account> =
            object : DiffUtil.ItemCallback<Account>() {
                override fun areItemsTheSame(
                    oldItem: Account,
                    newItem: Account
                ): Boolean {
                    return oldItem.userId == newItem.userId
                }

                override fun areContentsTheSame(
                    oldItem: Account,
                    newItem: Account
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

}