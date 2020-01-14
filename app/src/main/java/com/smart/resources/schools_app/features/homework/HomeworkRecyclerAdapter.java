package com.smart.resources.schools_app.features.homework;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.smart.resources.schools_app.databinding.ItemHomeworkBinding;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import ru.rhanza.constraintexpandablelayout.State;

public class HomeworkRecyclerAdapter extends RecyclerView.Adapter<HomeworkRecyclerAdapter.MyViewHolder> {
    private List<HomeworkModel> dataList= new ArrayList<HomeworkModel> ();

    public void addHomework(HomeworkModel homeworkModel){
        Logger.i("item inserted: " + homeworkModel);
        dataList.add(0, homeworkModel);
        notifyItemInserted(0);
    }

    static List<HomeworkModel> dataLis;
    public void updateData(List<HomeworkModel> dataList){
        this.dataList= dataList;
        dataLis=dataList;
        notifyDataSetChanged();
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

    public static HomeworkModel getHomeworkAt(int position){
        return dataLis.get(position);
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemHomeworkBinding binding;

        public MyViewHolder(@NonNull ItemHomeworkBinding binding ) {
            super(binding.getRoot());
            this.binding = binding;
            binding.expandLayout.setOnStateChangeListener((oldState, newState)->{

                if(newState== State.Collapsed){
                    /*if(binding.contentText.getLineCount()<3){
                        binding.contentText.append("\u2026");
                    }else {*/
                    binding.contentText.setMaxLines(1);
                    // }
                }else if(newState== State.Expanded|| newState == State.Expanding){
                    binding.contentText.setMaxLines(100);
                   /* String text=binding.contentText.getText().toString();
                    binding.contentText.setText(text.replace('\u2026',' ').trim());*/
                }

                return Unit.INSTANCE;
            });

            binding.expandLayout.setOnClickListener(v -> {
                binding.expandLayout.toggle(true);

            });
        }

        private void bind(HomeworkModel model){
            binding.setItemModel(model);
        }
    }


}