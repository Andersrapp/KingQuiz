package com.mr.poppa.kingquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Benjamin and Anders on 4/16/2015.
 */
public class PopupFragment extends Fragment {

    private Button newGameButton;
    private Button homeButton;
    private TextView finalScoreTextView;
    int score;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.popup_fragment, container, false);
        score = this.getArguments().getInt("score");

        homeButton = (Button) view.findViewById(R.id.homeButton);
        newGameButton = (Button) view.findViewById(R.id.newGameButton);
        finalScoreTextView = (TextView) view.findViewById(R.id.finalScoreTextView);

        finalScoreTextView.setText(String.valueOf(score));

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MenuActivity.class);
                startActivity(i);
            }
        });

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), getActivity().getClass());
                startActivity(i);
            }
        });

        return view;
    }
}
