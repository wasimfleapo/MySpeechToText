package com.example.myspeechtotextdemo.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myspeechtotextdemo.R;
import com.example.myspeechtotextdemo.model.DictionaryModel;

import java.util.List;


public class RecyclerAdapter  extends RecyclerView.Adapter {

    Activity mActivity;
    private List<DictionaryModel> mdetails ;




    public RecyclerAdapter(Activity mActivity, List<DictionaryModel> mdetails) {
        this.mActivity = mActivity;
        this.mdetails = mdetails;


    }

public static class UnselectedHolder extends RecyclerView.ViewHolder {

    private TextView word,frequency;
    private RelativeLayout ryt_item;

    public UnselectedHolder(View itemView) {
        super(itemView);



        this.word = (TextView) itemView.findViewById(R.id.txt_word);
        this.frequency = (TextView) itemView.findViewById(R.id.txt_frequency);
        this.ryt_item = (RelativeLayout) itemView.findViewById(R.id.ryt_item);
    }
}

public static class SelectedHolder extends RecyclerView.ViewHolder {

    private TextView word,frequency;
    private RelativeLayout ryt_item;

    public SelectedHolder(View itemView) {
        super(itemView);

        this.word = (TextView) itemView.findViewById(R.id.txt_word);
        this.frequency = (TextView) itemView.findViewById(R.id.txt_frequency);
        this.ryt_item = (RelativeLayout) itemView.findViewById(R.id.ryt_item);
    }
}





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recyclerv_view_item, parent, false);
                return new UnselectedHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recyclerv_view_item_selected, parent, false);
                return new SelectedHolder(view);

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        if(mdetails.get(position).isBackgroundColoured()){

            return 1;

        }else {
            return 0;
        }



    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        if(mdetails.get(listPosition).isBackgroundColoured()){

            ((SelectedHolder) holder).word.setText(mdetails.get(listPosition).getWord());
            ((SelectedHolder) holder).frequency.setText(""+mdetails.get(listPosition).getFrequency());
        }else{

            ((UnselectedHolder) holder).word.setText(mdetails.get(listPosition).getWord());
            ((UnselectedHolder) holder).frequency.setText(""+mdetails.get(listPosition).getFrequency());
        }



    }

    @Override
    public int getItemCount() {
        return mdetails.size();
    }
}

