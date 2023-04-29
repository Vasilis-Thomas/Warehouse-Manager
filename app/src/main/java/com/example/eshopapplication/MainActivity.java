package com.example.eshopapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import database.AppDataBaseActivity;
import database.MyAppDatabase;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "com.example.applicationwithmenu";
    Toolbar toolbar;
    public static FragmentManager fragmentManager;
    public static MyAppDatabase myAppDatabase;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView email_text, toolbarTitle;
    AlertDialog.Builder builder;

    private ListView mDrawerList;


//    FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();
//    if (fbuser != null){
//     for (UserInfo profile : fbuser.getProviderData()) {
//        // Id of the provider (ex: google.com)
//        String providerId = profile.getProviderId();
//
//        // UID specific to the provider
//        String uid = profile.getUid();
//
//        // Name, email address, and profile photo Url
//        String name = profile.getDisplayName();
//        String fbuseremail = profile.getEmail();
//        Uri photoUrl = profile.getPhotoUrl();
////
//    }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState !=null){
            Bundle b = getIntent().getExtras();
            String title = b.getString("stringaki");
        }
        builder = new AlertDialog.Builder(MainActivity.this);
        Log.i(TAG,"onCreate callback method");




        makeToolbarButton(toolbar);

        drawerLayout   = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dr_home:
//                           menuItem.setChecked(false);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.dr_contacts:
                           menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.dr_find:
                          menuItem.setChecked(true);
                        return true;

                    case R.id.dr_marketplace:
                        menuItem.setChecked(true);
                        startActivity(new Intent(MainActivity.this, AppDataBaseActivity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.dr_about:
                          menuItem.setChecked(true);
                        showInfo(builder);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.logout_activity:
                        menuItem.setChecked(true);
//                        startActivity(new Intent(MainActivity.this, Logout_Activity.class));
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
//                        Intent n = new Intent(MainActivity.this, Logout_Activity.class);
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseuser != null){
//            String userMame = firebaseUser.getDisplayName();
//            Uri userphoto = firebaseUser.getPhotoUrl();
//            String userID = firebaseUser.getUid();
//            boolean emailVerified = firebaseUser.isEmailVerified();

//            String textaki = String.valueOf(navigationView.getHeaderCount());  // Για να βρω τον αριθμο των Headers του NavigationView
            String userEmail = firebaseuser.getEmail();
            View menu_drawer_head = navigationView.getHeaderView(0);
            email_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_email_text);
            email_text.setText(userEmail);
        }
        else{
            View menu_drawer_head = navigationView.getHeaderView(0);
            email_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_email_text);
            email_text.setText("Unknown");
        }


        fragmentManager = getSupportFragmentManager();
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class,"reservesBD").allowMainThreadQueries().build();
        if(findViewById(R.id.fragment_container)!=null){
            if(savedInstanceState!=null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container, new Fragment_room()).commit();
}
//        String message = "hello";
//        Fragment_room fv = new Fragment_room();
//        Bundle bundle = new Bundle();
//        bundle.putString("message",message);
//        fv.setArguments(bundle);
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
////        fragmentTransaction.replace(R.id.fragment_container, fv, null);
//        fragmentTransaction.replace(R.id.fragment_container, fv, null);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer((GravityCompat.START));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public Toolbar makeToolbarButton(Toolbar toolbar){
        toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_button);
        actionBar.setTitle("");
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.app_name); //        toolbarTitle.setText("Economy E-shop Application");
        return toolbar;
    }

    public static void showInfo(AlertDialog.Builder b){
        b.setTitle("Application Information").
                setMessage("Constructors: Nikolas Sarakenidis, Vasilis Thomas\n"
                        + "Institution: International University of Greece\n"
                        + "Supervisor: Euklidis Keramopoylos\n"
                        + "Project name: Economy E-shop Application\n"
                        + "Application: Android\n"
                        + "MinimumSdk: 24").show();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i(TAG,"onRestart callback method");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG,"onStart callback method");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG,"onResume callback method");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG,"onPause callback method");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
            super.onSaveInstanceState(outState);
        Log.i(TAG,"onSaveInstanceState callback method");
    }


    @Override
    protected  void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG,"onRestoreInstanceState callback method");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG,"onStop callback method");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG,"onDestroy callback method");
    }

}

