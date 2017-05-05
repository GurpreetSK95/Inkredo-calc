package me.gurpreetsk.emicalulator.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueButton;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

import me.gurpreetsk.emicalulator.R;

public class LoginActivity extends AppCompatActivity implements ITrueCallback {

    TrueClient trueClient;
    SharedPreferences preferences;

    private static final String TAG = LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        if (preferences.getBoolean(getString(R.string.is_login), false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            setContentView(R.layout.activity_login);
            trueClient = new TrueClient(LoginActivity.this, this);
            TrueButton buttonTrueButton = (TrueButton) findViewById(R.id.com_truecaller_android_sdk_truebutton);
            buttonTrueButton.setTrueClient(trueClient);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onSuccesProfileShared(@NonNull TrueProfile trueProfile) {
        Log.i(TAG, "onSuccessProfileShared: " + trueProfile.phoneNumber);
        preferences.edit()
                .putBoolean(getString(R.string.is_login), true)
                .putString(getString(R.string.name), trueProfile.firstName)
                .putString(getString(R.string.contact), trueProfile.phoneNumber)
                .apply();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
        Log.e(TAG, "onFailureProfileShared: " + trueError.toString());
        Toast.makeText(this, "Failed to Login. Try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (trueClient.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

}