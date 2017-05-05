package me.gurpreetsk.emicalulator.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueButton;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

import me.gurpreetsk.emicalulator.R;

public class LoginActivity extends AppCompatActivity implements ITrueCallback {

//    @BindView(R.id.button_truecaller_login)
//    TrueButton buttonTrueButton;
    MaterialDialog progressDialog;

    TrueClient trueClient;

    private static final String TAG = LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        ButterKnife.bind(this);

        trueClient = new TrueClient(LoginActivity.this, this);
        TrueButton buttonTrueButton = (TrueButton) findViewById(R.id.button_truecaller_login);
        buttonTrueButton.setTrueClient(trueClient);
        buttonTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new MaterialDialog.Builder(LoginActivity.this)
                        .content("Logging in")
                        .progress(true, 0)
                        .cancelable(false)
                        .show();
            }
        });
    }


    @Override
    public void onSuccesProfileShared(@NonNull TrueProfile trueProfile) {
        Log.i(TAG, "onSuccessProfileShared: " + trueProfile.phoneNumber);
        progressDialog.dismiss();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
        Log.e(TAG, "onFailureProfileShared: " + trueError.toString());
        progressDialog.dismiss();
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
