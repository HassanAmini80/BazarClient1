package com.example.hassan.bazarclient.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hassan.bazarclient.R;
import com.example.hassan.bazarclient.models.GoodModel;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Hassan on 7/3/2017.
 */

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.GoodViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private GoodEventHandler mGoodEventHandler;
    private List<GoodModel> mData = Collections.emptyList();

    public GoodAdapter(Context context, GoodEventHandler goodEventHandler) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mGoodEventHandler = goodEventHandler;
    }

    public void updateAdapterData(List<GoodModel> data) {
        this.mData = data;
    }

    public GoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.good_row, parent, false);
        GoodViewHolder goodHolder = new GoodViewHolder(view);
        return goodHolder;
    }

    @Override
    public void onBindViewHolder(GoodViewHolder holder, int position) {

        GoodModel currentModel = mData.get(position);
        holder.setData(currentModel, position);
        holder.setListeners();
    }


    public int getItemCount() {
        return mData.size();
    }

    public void GoodInfo(int position, GoodModel good) {
        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+String.valueOf(mData.get(position)));
        if (mGoodEventHandler != null) {
            mGoodEventHandler.onGoodInfo(String.valueOf(mData.get(position)), position);
        }
    }

    public class GoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView mTxGoodName;
        private AppCompatTextView mTxGoodPrice;
        private AppCompatTextView description;
        private ImageView imGood;
        private GoodModel currentGood;
        private int position;

        public GoodViewHolder(View itemView) {
            super(itemView);
            mTxGoodName = (AppCompatTextView) itemView.findViewById(R.id.goodName);
            mTxGoodPrice = (AppCompatTextView) itemView.findViewById(R.id.goodPrice);
            description = (AppCompatTextView) itemView.findViewById(R.id.description);
            imGood = (ImageView) itemView.findViewById(R.id.im_good);
        }

        public void setData(GoodModel currentModel, int position) {
            this.mTxGoodName.setText(currentModel.goodName);
            this.mTxGoodPrice.setText(currentModel.goodPrice);
            this.description.setText(currentModel.goodDes);
            Picasso.with(mContext).load(currentModel.imageUrl).into(this.imGood);
            this.position = position;
            this.currentGood = currentModel;
        }

        public void setListeners() {
            imGood.setOnClickListener(GoodViewHolder.this);
        }

        public void onClick(View v) {
            Log.d(TAG, "On Click Before Operation Action!" + position + "  size:" + mData.size());
            switch (v.getId()) {
                case R.id.im_good:
                    GoodInfo(position, currentGood);
                    break;
            }

        }


    }

    public interface GoodEventHandler {
        void onGoodInfo(String goodId, int position);

    }

}