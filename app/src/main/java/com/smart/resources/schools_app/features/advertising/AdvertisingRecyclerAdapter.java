package com.smart.resources.schools_app.features.advertising;

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
        AdvertisingModel model = new AdvertisingModel("ammar","The Data Binding Library generates binding classes that are used to access the layout's variables and views. This page shows how to create and customize generated binding classes.\n" +
                "\n" +
                "The generated binding class links the layout variables with the views within the layout. The name and package of the binding class can be customized. All generated binding classes inherit from the ViewDataBinding class.\n" +
                "\n" +
                "A binding class is generated for each layout file. By default, the name of the class is based on the name of the layout file, converting it to Pascal case and adding the Binding suffix to it. The above layout filename is activity_main.xml so the corresponding generated class is ActivityMainBinding. This class holds all the bindings from the layout properties (for example, the user variable) to the layout's views and knows how to assign values for the binding expressions.");
        holder.bind(model);
    }


    @Override
    public int getItemCount() {
        return 25;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemAdvertisingBinding binding;

        public MyViewHolder(@NonNull ItemAdvertisingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.expandLayout.setOnStateChangeListener((oldState, newState)->{
                if(newState== State.Collapsed){
                    binding.contentText.setMaxLines(2);
                }else if(newState== State.Expanded|| newState == State.Expanding){
                    binding.contentText.setMaxLines(25);
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


