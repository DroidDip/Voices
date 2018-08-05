package com.voices.ui.viewholder;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.voices.R;
import com.voices.model.DictionaryModel;

public class DictionaryItemHolder extends RecyclerView.ViewHolder {

    private AppCompatTextView tv_word, tv_frequency;
    private CardView cv_word_bg, cv_freq_bg;

    public DictionaryItemHolder(View itemView) {
        super(itemView);
        tv_word = (AppCompatTextView) itemView.findViewById(R.id.tv_word);
        tv_frequency = (AppCompatTextView) itemView.findViewById(R.id.tv_frequency);

        cv_word_bg = (CardView) itemView.findViewById(R.id.cv_word_bg);
        cv_freq_bg = (CardView) itemView.findViewById(R.id.cv_freq_bg);
    }

    public void bindItem(DictionaryModel dictionaryModel) {
        tv_word.setText(dictionaryModel.getDictionaryWord());
        tv_frequency.setText("" + dictionaryModel.getSpeechFrequency());
    }

    public void updateItem(boolean isUpdated) {
        if (isUpdated) {
            cv_word_bg.setCardBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorHighLight));
            cv_freq_bg.setCardBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorHighLight));
        } else {
            cv_word_bg.setCardBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorWhite));
            cv_freq_bg.setCardBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorWhite));
        }
    }
}
