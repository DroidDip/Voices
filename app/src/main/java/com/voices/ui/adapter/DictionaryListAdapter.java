package com.voices.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voices.R;
import com.voices.model.DictionaryModel;
import com.voices.ui.viewholder.DictionaryItemHolder;

import java.util.ArrayList;

public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryItemHolder> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private ArrayList<DictionaryModel> dictionaryArray;

    public DictionaryListAdapter(Context mContext) {
        this.mContext = mContext;
        this.dictionaryArray = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public DictionaryItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.inflate_dictionary_item, parent, false);
        return new DictionaryItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DictionaryItemHolder holder, int position) {
        holder.bindItem(dictionaryArray.get(position));
        holder.updateItem(dictionaryArray.get(position).isUpdated());
    }

    @Override
    public int getItemCount() {
        return (dictionaryArray != null && dictionaryArray.size() > 0) ? dictionaryArray.size() : 0;
    }

    public void refreshAdapter(ArrayList<DictionaryModel> _dictionaryArray) {
        dictionaryArray.clear();
        dictionaryArray.addAll(_dictionaryArray);
        notifyDataSetChanged();
    }
}
