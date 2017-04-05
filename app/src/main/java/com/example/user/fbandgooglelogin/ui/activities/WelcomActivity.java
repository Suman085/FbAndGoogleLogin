package com.example.user.fbandgooglelogin.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user.fbandgooglelogin.mvp.view.WelcomeView;
import com.example.user.fbandgooglelogin.ui.fragments.HomeFragment;
import com.example.user.fbandgooglelogin.ui.fragments.ProfileFragment;
import com.example.user.fbandgooglelogin.R;
import com.example.user.fbandgooglelogin.ui.fragments.VIewPagerfragment;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class WelcomActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,WelcomeView{
    public static final int LOG_OUT_GOOGLEAPI_CLIENT =33 ;
    private static final String TAG = WelcomActivity.class.getSimpleName();
    private Profile mProfile;
    private GoogleSignInAccount mSignInAccount;
    private CircleImageView mFbProfilePic;
    private TextView emailField;
    private TextView nameField;
    private CircleImageView googleImage;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        getSupportFragmentManager().beginTransaction().replace(R.id.place_holder,new VIewPagerfragment()).commit();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_welcom, null);
        navigationView.addHeaderView(nav_header);
        googleImage=(CircleImageView)nav_header.findViewById(R.id.google_profile_pic);
        nameField= (TextView) nav_header.findViewById(R.id.profile_name);
        emailField=(TextView)nav_header.findViewById(R.id.profile_email);
        mFbProfilePic =(CircleImageView) nav_header.findViewById(R.id.profile_picture);
        navigationView.setNavigationItemSelectedListener(this);

            int flag=getIntent().getFlags();
        if (flag==2){
            mSignInAccount=getIntent().getParcelableExtra(LoginActivity.ACCOUNT_GOOGLE);
            nameField.setText(mSignInAccount.getDisplayName());
            emailField.setText(mSignInAccount.getEmail());
//            Log.e(TAG,mSignInAccount.getPhotoUrl().toString());
            Picasso.with(this).load(mSignInAccount.getPhotoUrl()).fit().placeholder(R.drawable.profile_icon).into(googleImage);
            //googleImage.setImageURI(mSignInAccount.getPhotoUrl());
            mFbProfilePic.setVisibility(View.INVISIBLE);

        }else{
            mProfile= getIntent().getParcelableExtra(LoginActivity.ACCOUNT_FACEBOOK);
            //mFbProfilePic.setImageURI(mProfile.getProfilePictureUri(80,80));
            nameField.setText(mProfile.getFirstName());
            Picasso.with(this).load(mProfile.getProfilePictureUri(80,80)).fit().into(mFbProfilePic);
            emailField.setText(mProfile.getLinkUri().toString());
            googleImage.setVisibility(View.INVISIBLE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    protected void onStart() {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestProfile()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            mGoogleApiClient.connect();
            super.onStart();
        }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.place_holder,new HomeFragment()).commit();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.my_profile) {
            ProfileFragment profileFragment=new ProfileFragment();
            if(mSignInAccount!=null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(LoginActivity.ACCOUNT_GOOGLE, mSignInAccount);
                profileFragment.setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.place_holder,profileFragment).commit();
        } else if (id == R.id.log_out) {
           if(mProfile!=null) {
               LoginManager.getInstance().logOut();
               Intent intent = new Intent(WelcomActivity.this, LoginActivity.class);
               startActivity(intent);
               finish();
           }else {

               Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                   @Override
                   public void onResult(@NonNull Status status) {

                       Intent intent=new Intent(WelcomActivity.this,LoginActivity.class);
                       startActivity(intent);
                   }
               });


           }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
