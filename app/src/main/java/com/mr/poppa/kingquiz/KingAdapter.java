package com.mr.poppa.kingquiz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Benjamin and Anders on 4/16/2015.
 */
public class KingAdapter extends ArrayAdapter<King> {
    Context mContext;
    int mLayoutResourceId;
    King[] mData = null;
    int numberOfGuesses;

    public KingAdapter(Context context, int resource, King[] data) {
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mData = data;
    }

    @Override
    public King getItem(int position) {

        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        KingHolder kingHolder = null;
        if (row == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            row = layoutInflater.inflate(mLayoutResourceId, parent, false);
            kingHolder = new KingHolder();
            kingHolder.kingImageView = (ImageView) row.findViewById(R.id.kingImageView);
            kingHolder.reignedYearsTextView = (TextView) row.findViewById(R.id.reignedYearsText);

            kingHolder.answerTextView = (TextView) row.findViewById(R.id.answerTextView);

            row.setTag(kingHolder);
        } else {
            kingHolder = (KingHolder) row.getTag();
        }

        King king = mData[position];

        try {
            numberOfGuesses = king.getAnswer().getNumberOfGuesses();

        } catch (NullPointerException e) {
            Log.v("Error: ", e.toString());
            numberOfGuesses = 0;

        }


        if (king.getAnswer().isCorrect()) {
            kingHolder.answerTextView.setText("Correct! " + king.getName());
        } else if ((numberOfGuesses < 3)) {
            kingHolder.answerTextView.setText("");
        } else if (numberOfGuesses == 3) {
            kingHolder.answerTextView.setText("FAILED!");
        }


        Integer kingId = king.getId();
        kingHolder.answerTextView.setTag(kingId);

        int resId = mContext.getResources().getIdentifier(king.getImageId(), "drawable", mContext.getPackageName());
        kingHolder.kingImageView.setImageResource(resId);

        kingHolder.reignedYearsTextView.setText(king.getReign());
        return row;
    }



    private static class KingHolder {
        ImageView kingImageView;
        TextView reignedYearsTextView;
        TextView answerTextView;
    }
}