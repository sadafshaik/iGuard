package com.sadaf.iguardapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    private LinearLayout Profile_Section;
    private LinearLayout App_Logo;
    private Button SignOut;
    private SignInButton SignIn;
    private TextView Name, Email;
    private ImageView Profile_Pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing all the variables!
        Profile_Section = (LinearLayout)findViewById(R.id.profile_section);
        App_Logo = (LinearLayout)findViewById(R.id.app_logo);
        SignOut = (Button)findViewById(R.id.bn_logout);
        SignIn = (SignInButton)findViewById(R.id.bn_login);
        Name = (TextView)findViewById(R.id.name);
        Email = (TextView)findViewById(R.id.email);
        Profile_Pic = (ImageView)findViewById(R.id.profile_pic);

        // Setting onClick Listeners for singIn and signOut buttons
        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);

        // Initially not displaying user information before signIn
        Profile_Section.setVisibility(View.GONE);

        // This will extract the basic user info like picture, email, etc..
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        // Initialize the Google Api Client with the respective parameters and the connection failed listener.
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();

    }

    @Override
    public void onClick(View v) {

        // Handling the onClick using a switch to handle each case (signIn and signOut)
        switch(v.getId())
        {
            case R.id.bn_login:
                signIn();
                break;

            case R.id.bn_logout:
                signOut();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn(){

        // Need an intent to show changes
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        // This starts the activity along with the signIn information
        startActivityForResult(intent, REQ_CODE);
    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status){
                updateUI(false);
            }
        });
    }

    // Handles the results generated from signIn
    private void handleResult(GoogleSignInResult result){

        // Check if the signIn worked
        if(result.isSuccess()){

            // Accessing the account information
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();

            // Setting the information on the screen into the textviews
            Name.setText(name);
            Email.setText(email);
            Glide.with(this).load(img_url).into(Profile_Pic);

            // Updating the UI
            updateUI(true);
        }

        else{
            // If the user couldn't login then you don't update the UI
            updateUI(false);
        }
    }

    private void updateUI(boolean isLoggedIn){
        // Checking if the user successfully logged in
        if(isLoggedIn){
            Profile_Section.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
        }
        else{
            Profile_Section.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
}
