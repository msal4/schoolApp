package com.smart.resources.schools_app.features.homework;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.resources.schools_app.databinding.ItemHomeworkBinding;

import java.util.List;

public class HomeworkRecyclerAdapter extends RecyclerView.Adapter<HomeworkRecyclerAdapter.MyViewHolder> {
    private List<HomeworkModel> dataList;

    public HomeworkRecyclerAdapter(List<HomeworkModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        ItemHomeworkBinding binding= ItemHomeworkBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HomeworkModel model= dataList.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemHomeworkBinding binding;

        public MyViewHolder(@NonNull ItemHomeworkBinding binding ) {
            super(binding.getRoot());
            this.binding= binding;
        }

        private void bind(HomeworkModel model){
            binding.setItemModel(model);
        }
    }


}