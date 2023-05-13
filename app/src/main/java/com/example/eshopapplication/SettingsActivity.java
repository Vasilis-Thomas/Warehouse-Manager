package com.example.eshopapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import remote.database.Orders_Activity;
import signuploginfirebase.LoginActivity;
import signuploginfirebase.sharedPreferenceConfig;


public class SettingsActivity extends AppCompatActivity {
    private signuploginfirebase.sharedPreferenceConfig sharedPreferenceConfig;
    Toolbar toolbar;
    private final static String TAG = "com.example.applicationwithmenu (Setting Activity)";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView username_text, email_text;
    AlertDialog.Builder builder;
    TextView toolbarTitle, usernameTxt, emailTxt, reportABugTxt, infoTxt, logoutTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_settings);
        sharedPreferenceConfig = new sharedPreferenceConfig(getApplicationContext());

        toolbar = makeToolbarButtonArrowBack();


        usernameTxt = findViewById(R.id.username_txt);
        emailTxt = findViewById(R.id.email_txt);
        reportABugTxt = findViewById(R.id.report_a_bug_txt);
        infoTxt = findViewById(R.id.info_txt);
        logoutTxt = findViewById(R.id.logout_txt);
//        versionTxt = findViewById(R.id.version_txt);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dr_database:
                        menuItem.setChecked(true);
                        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                        drawerLayout.closeDrawers();
                        return true;


                    case R.id.dr_orders:
                        menuItem.setChecked(true);
                        startActivity(new Intent(SettingsActivity.this, Orders_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.dr_product_inventory:
                        menuItem.setChecked(true);
                        startActivity(new Intent(SettingsActivity.this, Product_Inventory_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.dr_supplier_info:
                        menuItem.setChecked(true);
                        startActivity(new Intent(SettingsActivity.this, Supplier_Info_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.dr_about:
                        menuItem.setChecked(true);
                        //showInfo(builder);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.logout:
                        menuItem.setChecked(true);
//                        startActivity(new Intent(MainActivity.this, Logout_Activity.class));
//                        startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
//                        Intent n = new Intent(MainActivity.this, Logout_Activity.class);
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseuser != null) {
            String userName = firebaseuser.getDisplayName();
            String userEmail = firebaseuser.getEmail();
            View menu_drawer_head = navigationView.getHeaderView(0);
            username_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_username_text);
            email_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_email_text);
            username_text.setText(userName);
            email_text.setText(userEmail);
//        }
//        else{
//            View menu_drawer_head = navigationView.getHeaderView(0);
//            username_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_username_text);
//            email_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_email_text);
//            username_text.setText("Username: Unknown");
//            email_text.setText("Unknown");
//        }
            usernameTxt.setText(userName);
            emailTxt.setText(userEmail);

            reportABugTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // IMPLICIT sto gmail
                }
            });

            infoTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder = new AlertDialog.Builder(SettingsActivity.this);
                    showInfo(builder);
                }
            });


//        versionTxt.setText(BuildConfig.VERSION_CODE);

            logoutTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setTitle("Confirmation message");
                    builder.setMessage("You are log in as " + userName + ".\nAre you sure you want to logout?");
                    builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            sharedPreferenceConfig.writeLoginStatus(false);
//                            startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferenceConfig.writeLoginStatus(false);
                            startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                onBackPressed();
                drawerLayout.openDrawer((GravityCompat.START));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public Toolbar makeToolbarButtonArrowBack(){
        toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
            Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);
            actionBar.setTitle("");
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.set_toolbar_title);
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

}