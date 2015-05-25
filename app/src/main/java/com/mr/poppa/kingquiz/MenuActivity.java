package com.mr.poppa.kingquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mr.poppa.kingquiz.serverapi.XMPPClient;

import java.util.Random;

/**
 * Created by Benjamin and Anders on 4/16/2015.
 */
public class MenuActivity extends ActionBarActivity {

    private Button kingButton = null;
    private Button reignButton = null;
    private Button messageButton = null;
//    private SharedPreferences mSharedPreferences;
//    private static final String PREFS = "prefs";
//    private static final String PREF_NAME = "name";
//    private static final String PREF_KING_SCORE = "king_score";
//    private static final String PREF_REIGN_SCORE = "reign_score";
//    private ShareActionProvider mShareActionProvider;
    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        kingButton = (Button) findViewById(R.id.buttonKings);
        reignButton = (Button) findViewById(R.id.buttonReign);
        messageButton = (Button) findViewById(R.id.buttonMessage);

        kingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), KingActivity.class);
                startActivity(i);
            }
        });

        reignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ReignActivity.class);
                startActivity(i);
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(v.getContext(), XMPPClient.class);
                        startActivity(i);
                    }
                });
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                Random rn = new Random();
                int randomInt = rn.nextInt(2) + 1;

                switch (randomInt) {
                    case 1:
                        Intent randomIntent1 = new Intent(getApplicationContext(), ReignActivity.class);
                        startActivity(randomIntent1);
                        break;
                    case 2:
                        Intent randomIntent2 = new Intent(getApplicationContext(), KingActivity.class);
                        startActivity(randomIntent2);
                        break;
                    default:
                        break;
                }
            }
        });

//        displayWelcome();

    }

//    public void displayWelcome() {
//        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
//
//        String name = mSharedPreferences.getString(PREF_NAME, "");
//        int kingHighScore = mSharedPreferences.getInt(PREF_KING_SCORE, 0);
//        int reignHighScore = mSharedPreferences.getInt(PREF_REIGN_SCORE, 0);
//
//        if (name.length() > 0) {
//            Toast.makeText(this, "Welcome back, " + name + "!", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "Your highest score on kings is " + kingHighScore
//                    + "\r\n and your highest score on reign is " + reignHighScore + "!", Toast.LENGTH_SHORT).show();
//        } else {
//            setNewUser();
//        }
//    }

//    public void setNewUser() {
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Hello!");
//        alert.setMessage("What is your name?");
//
//        final EditText input = new EditText(this);
//        alert.setView(input);
//
//        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int whichButton) {
//                String inputName = input.getText().toString();
//                SharedPreferences.Editor e = mSharedPreferences.edit();
//                if (inputName.equals("")) {
//                    inputName = "anonymous player";
//                }
//                e.putString(PREF_NAME, inputName);
//                e.commit();
//
//                Toast.makeText(getApplicationContext(), "Welcome, " + inputName + "!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String name = mSharedPreferences.getString(PREF_NAME, "");
//
//                if (name != "") {
//                    Toast.makeText(getApplicationContext(), "Welcome, " + name + "!", Toast.LENGTH_SHORT).show();
//                } else {
//                    SharedPreferences.Editor e = mSharedPreferences.edit();
//                    String defaultName = "anonymous player";
//                    e.putString(PREF_NAME, defaultName );
//                    e.commit();
//                    Toast.makeText(getApplicationContext(), "Welcome, " + defaultName + "!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        alert.show();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        MenuItem changeUser = menu.findItem(R.id.change_user);
        MenuItem exitApp = menu.findItem(R.id.exit_the_app);

        exitApp.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                return true;
            }
        });

//        changeUser.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                setNewUser();
//                return true;
//            }
//        });

//        if (shareItem != null) {
//            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
//        }
//        setShareIntent();
        return true;
    }

//    private void setShareIntent() {
//        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
//
//        String name = mSharedPreferences.getString(PREF_NAME, "");
//        int kingHighScore = mSharedPreferences.getInt(PREF_KING_SCORE, 0);
//        int reignHighScore = mSharedPreferences.getInt(PREF_REIGN_SCORE, 0);
//        String message = name + " highest score on kings is " + kingHighScore
//                + "\r\n and on reign is " + reignHighScore + "!";
//
//        if (mShareActionProvider != null) {
//
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            shareIntent.setType("text/plain");
//            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "High scores");
//            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
//
//            mShareActionProvider.setShareIntent(shareIntent);
//        }
//    }

    @Override
    protected void onResume() {
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
