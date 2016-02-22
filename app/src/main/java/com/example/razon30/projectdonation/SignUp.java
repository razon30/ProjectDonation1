package com.example.razon30.projectdonation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.font.FontAwesome;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {

    EditText etName, etMobile, etUserName, etPassword;
    BootstrapButton btnSignUp;
    AwesomeTextView logInLink;

    String strName, strMobile, strUserName, strPassword;

    ProgressBar progressBar;
    LinearLayout layout;

    ArrayList<String> userNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initialization();

        logInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, LoginActivity.class));
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                worksOnSignUp();
            }
        });


    }

    private void worksOnSignUp() {

        strName = etName.getText().toString();
        strMobile = etMobile.getText().toString();
        strUserName = etUserName.getText().toString();
        strPassword = etPassword.getText().toString();

        if (valid(strName, etName) && valid(strMobile, etMobile) && valid(strUserName, etUserName)
                && valid(strPassword, etPassword)) {

            if (isNetworkAvailable()) {
                showProgress(true);
                new TaskToConnectServer().execute();

            } else {
                AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(
                        SignUp.this);

                builderAlertDialog.setTitle("Connection Failed")
                        .setMessage("Try for connecting?")
                        .setIcon(android.R.drawable.stat_notify_error)
                        .setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));

                            }
                        })
                        .setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }


        }

    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            layout.setVisibility(show ? View.GONE : View.VISIBLE);
            layout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    layout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            layout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean valid(String strName, EditText etName) {

        if (strName.length() < 3 || strName == null || strName.equals("")) {

            etName.requestFocus();
            etName.setError("Must Be Greater than 3 digit");
            return false;
        }
        return true;
    }

    private void initialization() {

        etName = (EditText) findViewById(R.id.input_name);
        etMobile = (EditText) findViewById(R.id.input_phone);
        etUserName = (EditText) findViewById(R.id.input_userName);
        etPassword = (EditText) findViewById(R.id.input_password);

        btnSignUp = (BootstrapButton) findViewById(R.id.btn_signup);
        logInLink = (AwesomeTextView) findViewById(R.id.link_login);

        progressBar = (ProgressBar) findViewById(R.id.signup_progress);
        layout = (LinearLayout) findViewById(R.id.main_layout);

        userNameList = new ArrayList<String>();
        userNameList.add("abcd1");
        userNameList.add("abcd2");
        userNameList.add("abcd3");
        userNameList.add("abcd4");


    }

    private class TaskToConnectServer extends AsyncTask<Void, Void, Boolean> {

        Boolean aBoolean = false;

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
               aBoolean = false;
            }

            if (userNameList.contains(strUserName)) {
                aBoolean = true;
            }
            return aBoolean;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            showProgress(false);

            if (aBoolean) {

                btnSignUp.setBootstrapText(new BootstrapText.Builder(SignUp
                        .this).addFontAwesomeIcon(FontAwesome.FA_THUMBS_O_DOWN).addText(" " +
                        "User Name Already exists").build());
                btnSignUp.setBootstrapBrand(DefaultBootstrapBrand.DANGER);

                etUserName.requestFocus();
                etUserName.setError("User Name Already exists");

            } else {


                AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(
                       SignUp.this);

                builderAlertDialog.setTitle("Registration Successful")
                        .setMessage("We will contact with you very soon")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(new Intent(SignUp.this,MainActivity.class));

                            }
                        })

                        .show();


            }

        }
    }
}
