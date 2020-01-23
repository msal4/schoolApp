package com.smart.resources.schools_app.features.users

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ItemAccountBinding
import com.smart.resources.schools_app.features.profile.User

class UsersRecyclerAdapter(private val users:MutableList<User>?, private val listener: OnItemClickListener) : RecyclerView.Adapter<UsersRecyclerAdapter.MyViewHolder>(){
    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

    inner class MyViewHolder(val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(accountModel: User) {

            binding.itemModel = accountModel

        }
    }

    fun removeItem(position: Int){
        users?.removeAt(position)
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

    override fun getItemCount(): Int = users?.size?:0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        users?.get(position)?.let { user ->

            holder.itemView.setOnClickListener{
                listener.onItemClick(user)
            }
            holder.bind(user)

            if(user.uid== UsersRepository.instance.getCurrentUser()?.uid){

                holder.binding.profileImage.apply {
                    borderColor= ContextCompat.getColor(holder.itemView.context, R.color.green)
                    borderWidth= 2
                }

            }

        }
    }

}