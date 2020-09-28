package com.smart.resources.schools_app.features.users.presentation

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ItemAccountBinding
import com.smart.resources.schools_app.features.users.data.UserAccount
import com.smart.resources.schools_app.features.users.data.UserRepository

class AccountsRecyclerAdapter(private val UserAccounts:MutableList<UserAccount>?, private val listener: OnItemClickListener) : RecyclerView.Adapter<AccountsRecyclerAdapter.MyViewHolder>(){
    interface OnItemClickListener {
        fun onItemClick(UserAccount: UserAccount)
    }

    inner class MyViewHolder(val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(accountModel: UserAccount) {

            binding.itemModel = accountModel

        }
    }

    fun removeItem(position: Int){
        UserAccounts?.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val binding= ItemAccountBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = UserAccounts?.size?:0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        UserAccounts?.get(position)?.let { user ->

            holder.itemView.setOnClickListener{
                listener.onItemClick(user)
            }
            holder.bind(user)

            if(user.uid== UserRepository.instance.getCurrentUserAccount()?.uid){

                holder.binding.profileImage.apply {
                    borderColor= ContextCompat.getColor(holder.itemView.context, R.color.green)
                    borderWidth= 2
                }

            }

        }
    }

}