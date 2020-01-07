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
        }

        private void bind(AdvertisingModel model) {
            binding.setItemModel(model);
            makeTextViewResizable(binding.imageView,binding.textView6, 2, "read more", true);
        }


        public void makeTextViewResizable(final CardView img, final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

            if (tv.getTag() == null) {
                tv.setTag(tv.getText());
            }
            ViewTreeObserver vto = tv.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    String text;
                    int lineEndIndex;
                    ViewTreeObserver obs = tv.getViewTreeObserver();
                    obs.removeGlobalOnLayoutListener(this);
                    if (maxLine == 0) {
                        lineEndIndex = tv.getLayout().getLineEnd(0);
                        text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                        lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                        text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    } else {
                        lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                        text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    }
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(img,Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore,expandText), TextView.BufferType.SPANNABLE);
                }
            });
        }

        private SpannableStringBuilder addClickablePartTextViewResizable(final CardView img,final Spanned strSpanned, final TextView tv,
                                                                         final int maxLine, final String spanableText, final boolean viewMore, final String expand) {
            String str = strSpanned.toString();
            SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

            if (str.contains(spanableText)) {
                ssb.setSpan(new ClickableSpan() {

                    @Override
                    public void onClick(View widget) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        if (viewMore) {
                            makeTextViewResizable(img,tv, -1, "read less", false);
                            img.setVisibility(View.VISIBLE);
                        } else {
                            makeTextViewResizable(img ,tv, 2, expand, true);
                            img.setVisibility(View.GONE);
                        }

                    }
                }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

            }
            return ssb;
        }
    }

}


