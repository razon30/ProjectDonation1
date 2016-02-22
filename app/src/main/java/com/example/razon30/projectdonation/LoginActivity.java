package com.example.razon30.projectdonation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "a@b.c:razon1", "d@e.f:razon2"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUserName;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    BootstrapButton mEmailSignInButton;
    AwesomeTextView register, forget;

    AlertDialog.Builder builderAlertDialog;
    AlertDialog ad;
    RadioButton rad1, rad2;
    String catagory = "";

//    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
//    ProgressGenerator progressGenerator;
//    ActionProcessButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

//        progressGenerator = new ProgressGenerator(LoginActivity.this);
//        btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);


        forget = (AwesomeTextView) findViewById(R.id.forget);

        final View view = getLayoutInflater().inflate(R.layout.layoutdialog, null);
        EditText editText = (EditText) view.findViewById(R.id.phone);

        builderAlertDialog = new AlertDialog.Builder(LoginActivity.this);

        builderAlertDialog
                .setView(view)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        ad.cancel();
                    }
                });


        forget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (view.getParent() != null)
                    ((ViewGroup) view.getParent()).removeView(view); // <- fix

                ad = builderAlertDialog.create();
                ad.show();

            }
        });

        rad1 = (RadioButton) findViewById(R.id.radio_memeber);
        rad2 = (RadioButton) findViewById(R.id.radio_donar);

        // Set up the login form.
        mUserName = (AutoCompleteTextView) findViewById(R.id.email);
        //  populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {

                    if (rad1.isChecked())
                    {
                        catagory = "member";
                        attemptLogin();
                    }else
                    if (rad2.isChecked())
                    {
                        catagory = "donar";
                        attemptLogin();
                    }else {
                        mEmailSignInButton.setBootstrapText(new BootstrapText.Builder(LoginActivity
                                .this).addFontAwesomeIcon(FontAwesome.FA_THUMBS_O_DOWN).addText
                                ("Please Select a catagory.").build());
                        mEmailSignInButton.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    }


                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (BootstrapButton) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rad1.isChecked())
                {
                    catagory = "member";
                    attemptLogin();
                }else
                if (rad2.isChecked())
                {
                    catagory = "donar";
                    attemptLogin();
                }else {
                    mEmailSignInButton.setBootstrapText(new BootstrapText.Builder(LoginActivity
                            .this).addFontAwesomeIcon(FontAwesome.FA_THUMBS_O_DOWN).addText
                            (" Please Select a catagory.").build());
                    mEmailSignInButton.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                }
            }
        });

//        btnSignIn.setOnClickNormalState(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                attemptLogin();
//            }
//        }).build();;

        mLoginFormView = findViewById(R.id.email_login_form);
        mProgressView = findViewById(R.id.login_progress);


        register = (AwesomeTextView) findViewById(R.id.noaccount);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUp.class));
            }
        });


    }

//    private void populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return;
//        }
//
//        getLoaderManager().initLoader(0, null, this);
//    }
//
//    private boolean mayRequestContacts() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new View.OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        } else {
//            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return false;
//    }

    /**
     * Callback received when a permissions request has been completed.
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
//            }
//        }
//    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserName.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userName = mUserName.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        // Check for a valid email address.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userName)) {
            mUserName.setError(getString(R.string.error_field_required));
            focusView = mUserName;
            cancel = true;
        } else if (!isEmailValid(userName)) {
            mUserName.setError(getString(R.string.error_invalid_email));
            focusView = mUserName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(userName, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return new CursorLoader(this,
//                // Retrieve data rows for the device user's 'profile' contact.
//                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
//                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
//
//                // Select only email addresses.
//                ContactsContract.Contacts.Data.MIMETYPE +
//                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
//                .CONTENT_ITEM_TYPE},
//
//                // Show primary email addresses first. Note that there won't be
//                // a primary email address if the user hasn't specified one.
//                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
//    }

//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//        List<String> emails = new ArrayList<>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            emails.add(cursor.getString(ProfileQuery.ADDRESS));
//            cursor.moveToNext();
//        }
//
//        addEmailsToAutoComplete(emails);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//    }

//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(LoginActivity.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
//
//        mEmailView.setAdapter(adapter);
//    }


//    private interface ProfileQuery {
//        String[] PROJECTION = {
//                ContactsContract.CommonDataKinds.Email.ADDRESS,
//                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
//        };
//
//        int ADDRESS = 0;
//        int IS_PRIMARY = 1;
//    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        String userName;
        String mPassword;
        String aBoolean = "a";

        UserLoginTask(String userName, String mPassword) {
            this.userName = userName;
            this.mPassword = mPassword;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressGenerator.start(btnSignIn);
//            btnSignIn.setEnabled(false);
            mUserName.setEnabled(false);
            mPasswordView.setEnabled(false);
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                aBoolean = "b";
            }

//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(userName)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }


            // TODO: register the new account here.
            return aBoolean;
        }

        @Override
        protected void onPostExecute(String success) {
            mAuthTask = null;

            if ("razon".equals(userName)) {
                if ("razon1".equals(mPassword)) {

                    mEmailSignInButton.setBootstrapText(new BootstrapText.Builder(LoginActivity
                            .this).addFontAwesomeIcon(FontAwesome.FA_THUMBS_O_UP).addText(" " +
                            "Logged" +
                            " In").build());
                    mEmailSignInButton.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);
                    mUserName.setEnabled(true);
                    mPasswordView.setEnabled(true);
                    forget.setVisibility(View.GONE);
                } else {

                    mEmailSignInButton.setBootstrapText(new BootstrapText.Builder(LoginActivity
                            .this).addFontAwesomeIcon(FontAwesome.FA_THUMBS_O_DOWN).addText(" " +
                            "Wrong " +
                            "Password").build());
                    mEmailSignInButton.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                    mUserName.setEnabled(true);
                    mPasswordView.setEnabled(true);
                    forget.setVisibility(View.VISIBLE);
                }
            } else {

                mEmailSignInButton.setBootstrapText(new BootstrapText.Builder(LoginActivity
                        .this).addFontAwesomeIcon(FontAwesome.FA_THUMBS_O_DOWN).addText(" Wrong " +
                        "Username").build());
                mEmailSignInButton.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                mUserName.setError("This User Name is incorrect");
                mUserName.requestFocus();
                mUserName.setEnabled(true);
                mPasswordView.setEnabled(true);
                forget.setVisibility(View.VISIBLE);
            }


            showProgress(false);

//            if (success) {
//
//            } else {
//                Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
//                mEmailSignInButton.setText("Error Occurred,Try Again");
//                mEmailSignInButton.setBackgroundColor(getResources().getColor(R.color.bootstrap_brand_danger));
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//                mUserName.setEnabled(true);
//                mPasswordView.setEnabled(true);
//            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //  showProgress(false);
        }
    }
}

