package com.mr.poppa.kingquiz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Benjamin and Anders on 4/16/2015.
 */
public class ReignActivity extends FragmentActivity {
    King[] myKingsArray = new King[]{
            new King(1, "Karl XII", "1697-1718", "karl_den_xii"),
            new King(2, "Gustav Vasa", "1523-1560", "gustav_vasa"),
            new King(3, "Gustav III", "1771-1792", "gustav_iii"),
            new King(4, "Carl XIV Johan", "1818-1844", "carl_xiv_johan"),
            new King(5, "Gustav IV Adolf", "1796-1809", "gustav_iv_adolf"),
            new King(6, "Johan III", "1568-1592", "johan_iii"),
            new King(7, "Erik XIV", "1560-1569", "erik_xiv"),
            new King(8, "Gustav II Adolf", "1611-1632", "gustav_ii_adolf"),
            new King(9, "Karl X Gustav", "1654-1660", "karl_x_gustav"),
            new King(10, "Karl XI", "1672-1697", "karl_xi")
    };
    private ListView mListView;
    private SharedPreferences mSharedPreferences;
    private static final String PREFS = "prefs";
    private static final String PREF_NAME = "name";
    private static final String PREF_REIGN_SCORE = "reign_score";
    private ReignAdapter mReignAdapter;
    private TextView questionView;
    private TextView scoreView;
    private TextView answerView;
    private List<King> questionArray = new ArrayList<>();
    private int questionNumber = 0;
    private int score = 0;
    public static int kingsAnswered = 0;
    private int MAX_QUESTIONS = myKingsArray.length;
    private King kingInQuestion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        kingsAnswered = 0;

        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        int previousScore = mSharedPreferences.getInt(PREF_REIGN_SCORE, 0);
        Toast.makeText(this, "Your highest score on reign is, " + previousScore + "!", Toast.LENGTH_SHORT).show();

        for (int i = 0; i < myKingsArray.length; ++i) {
            this.questionArray.add(myKingsArray[i]);
            Collections.shuffle(questionArray);
        }


        Collections.shuffle(Arrays.asList(myKingsArray));
        questionView = (TextView) findViewById(R.id.questionView);
        scoreView = (TextView) findViewById(R.id.scoreView);
        mListView = (ListView) findViewById(R.id.myListView);

        mReignAdapter = new ReignAdapter(getApplicationContext(), R.layout.reign_row, myKingsArray);

        if (mListView != null) {
            mListView.setAdapter(mReignAdapter);
        }

        askQuestion();


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                answerView = (TextView) view.findViewById(R.id.answerTextView);

                int kingId = (int) answerView.getTag();

                King king = null;

                for (King k : myKingsArray) {
                    if (k.getId() == kingId) {
                        king = k;
                    }
                }
                King currentQuestion = kingInQuestion;

                int questionId = (int) questionView.getTag();
                boolean isCorrect = checkAnswer(king.getId(), questionId);
                if (!king.isAnswered() && king.getAnswer().getNumberOfGuesses() != 3) {

                    kingInQuestion.getAnswer().guess();
                    int numberOfGuesses = kingInQuestion.getAnswer().getNumberOfGuesses();

                    for (King k : questionArray) {
                        setFailedStatus(k);
                    }

                    if (isCorrect) {
                        king.setAnswered(true);
                        king.getAnswer().setCorrect(true);
                        kingsAnswered++;
                        switch (numberOfGuesses) {
                            case 1:
                                score += 100;
                                break;
                            case 2:
                                score += 50;
                                break;
                            case 3:
                                score += 25;
                                break;
                            default:
                                break;
                        }
                        setCorrectStatus(king);
                        checkGameStatus();
                    } else if (!isCorrect) {
                        answerView.setText("WRONG!");
                        checkGameStatus();
                    }
                    if(numberOfGuesses == 3 && !currentQuestion.isAnswered() && !currentQuestion.getAnswer().isCorrect()) {
                        currentQuestion.setAnswered(true);
                        kingsAnswered++;
                    }
                }
            }
        });
    }

    private void setFailedStatus(King king) {
        for (int j = 0; j < mListView.getChildCount(); ++j) {
            int guesses = king.getAnswer().getNumberOfGuesses();
            LinearLayout rowLayout = (LinearLayout) mListView.getChildAt(j);
            TextView failedAnswerView = (TextView) rowLayout.findViewById(R.id.answerTextView);
            if (failedAnswerView.getTag() == king.getId() && guesses == 3 && !king.getAnswer().isCorrect()) {
                failedAnswerView.setText("FAILED!");
            }
        }
    }

    public void setCorrectStatus(King king) {
        answerView.setText("Correct! " + king.getReign());
        scoreView.setText("Score: " + score);
    }


    public boolean checkAnswer(int kingId, int questionId) {
        return kingId == questionId;
    }

    public void checkGameStatus() {
        if (kingsAnswered < MAX_QUESTIONS) {
            askQuestion();
        } else {
            storeHighScore();
            createGameOverPopup();
        }
    }
    public void storeHighScore() {
        mSharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        int storedValue = mSharedPreferences.getInt(PREF_REIGN_SCORE, 0);

        int highScore = 0;
        if (storedValue > score) {
            highScore = storedValue;
        } else {
            highScore = score;
        }
        SharedPreferences.Editor e = mSharedPreferences.edit();

        e.putInt(PREF_REIGN_SCORE, highScore);
        e.commit();
    }

    public void createGameOverPopup() {
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        PopupFragment popupFragment = new PopupFragment();
        popupFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, popupFragment);
        ft.commit();
    }

    public void askQuestion() {
        boolean lastQuestion = questionNumber == questionArray.size();
        if (kingInQuestion == null) {
            kingInQuestion = questionArray.get(0);
        } else if (!lastQuestion) {
            kingInQuestion = questionArray.get(questionNumber);
        } else {
            questionNumber = 0;
            Collections.shuffle(questionArray);
            kingInQuestion = questionArray.get(questionNumber);
        }

        if (kingInQuestion.isAnswered()) {
            questionNumber++;
            checkGameStatus();
        } else {

            kingInQuestion = questionArray.get(questionNumber);

            questionView.setText("Who reigned " + kingInQuestion.getReign() + "? (" + (kingInQuestion.getAnswer().getNumberOfGuesses() + 1) + "/3)");
            questionView.setTag(kingInQuestion.getId());
            questionNumber++;
        }
    }
}