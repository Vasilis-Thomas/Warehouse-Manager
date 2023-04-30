package com.example.eshopapplication;

import static com.example.eshopapplication.MainActivity.showInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import signuploginfirebase.LoginActivity;
import signuploginfirebase.sharedPreferenceConfig;

public class Logout_Activity extends AppCompatActivity {

    private signuploginfirebase.sharedPreferenceConfig sharedPreferenceConfig;

    public Toolbar toolbar;
    private Button logoutButton;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView email_text;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        builder = new AlertDialog.Builder(this);


        sharedPreferenceConfig = new sharedPreferenceConfig(getApplicationContext());

//        MainActivity toolbarButton = new MainActivity();
//        toolbarButton.makeToolbarButton(toolbar);
        makeToolbarButton(toolbar);
        logoutButton = findViewById(R.id.logout_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferenceConfig.writeLoginStatus(false);
                startActivity(new Intent(Logout_Activity.this, LoginActivity.class));
                finish();
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dr_home:
                        menuItem.setChecked(false);
                        startActivity(new Intent(Logout_Activity.this, MainActivity.class));
//                        Intent intent = new Intent(Logout_Activity.this, LoginActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("StringKey1", "from :- Logout Activity");
//                        Supplier_Fragment fv = new Supplier_Fragment();
//                        fv.setArguments(bundle);
//                        startActivity(intent);
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

                        drawerLayout.closeDrawers();

                        return true;
                    case R.id.dr_about:
                        menuItem.setChecked(true);
                        showInfo(builder);
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseuser != null){
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
        return toolbar;
    }

}