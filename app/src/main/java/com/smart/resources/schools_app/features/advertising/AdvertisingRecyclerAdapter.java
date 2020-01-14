package com.smart.resources.schools_app.features.advertising;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.resources.schools_app.databinding.ItemAdvertisingBinding;

import java.util.List;

import kotlin.Unit;
import ru.rhanza.constraintexpandablelayout.State;

public class AdvertisingRecyclerAdapter extends RecyclerView.Adapter<AdvertisingRecyclerAdapter.MyViewHolder> {
    private List<AdvertisingModel> dataList;

    public AdvertisingRecyclerAdapter(List<AdvertisingModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAdvertisingBinding binding = ItemAdvertisingBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AdvertisingModel model = dataList.get(position);
        holder.bind(model);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemAdvertisingBinding binding;

        public MyViewHolder(@NonNull ItemAdvertisingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.expandLayout.setOnStateChangeListener((oldState, newState)->{

                if(newState== State.Collapsed){
                    /*if(binding.contentText.getLineCount()<3){
                        binding.contentText.append("\u2026");
                    }else {*/
                        binding.contentText.setMaxLines(2);
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

        private void bind(AdvertisingModel model) {
            binding.setItemModel(model);
        }
    }

}


