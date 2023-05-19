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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import database.MyAppDatabase;
import database.Product_Fragment;
import database.Supplier_Fragment;
import database.Supplies_Fragment;
import remote.database.Order_Info_Activity;
import remote.database.Orders_Activity;


public class MainActivity extends AppCompatActivity {
    private static final String SELECTED_TAB_KEY = "selected_tab";
    private final static String TAG = "com.example.applicationwithmenu";
    Toolbar toolbar;
    public static FragmentManager fragmentManager;
    public static MyAppDatabase myAppDatabase;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView username_text, email_text, toolbarTitle;
    AlertDialog.Builder builder;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    TabLayout tablayout;

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
//        if(savedInstanceState !=null){

////            return;
//        }


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("myChLocalDatabase", "MyChannelLocalDatabase", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }

        builder = new AlertDialog.Builder(MainActivity.this);
        Log.i(TAG, "onCreate callback method");


        toolbar = makeToolbarButton();


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.dr_database);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dr_database:
//                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.dr_orders:
                        menuItem.setChecked(true);
                        startActivity(new Intent(MainActivity.this, Orders_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.dr_order_info:
                        menuItem.setChecked(true);
                        startActivity(new Intent(MainActivity.this, Order_Info_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.dr_product_inventory:
                        menuItem.setChecked(true);
                        startActivity(new Intent(MainActivity.this, Product_Inventory_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.dr_supplier_info:
                        menuItem.setChecked(true);
                        startActivity(new Intent(MainActivity.this, Supplier_Info_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.settings:
                        menuItem.setChecked(true);
//                        startActivity(new Intent(MainActivity.this, Logout_Activity.class));
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseuser != null) {
//            Uri userphoto = firebaseUser.getPhotoUrl();
//            String userID = firebaseUser.getUid();
//            boolean emailVerified = firebaseUser.isEmailVerified();

//            String textaki = String.valueOf(navigationView.getHeaderCount());  // Για να βρω τον αριθμο των Headers του NavigationView
            String userName = firebaseuser.getDisplayName();
            String userEmail = firebaseuser.getEmail();
            View menu_drawer_head = navigationView.getHeaderView(0);
            username_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_username_text);
            email_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_email_text);
            username_text.setText(userName);
            email_text.setText(userEmail);
            Log.i(TAG, "username_text: " + email_text.getText());
        } else {
            View menu_drawer_head = navigationView.getHeaderView(0);
            email_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_email_text);
            email_text.setText("Unknown");
        }


        tablayout = findViewById(R.id.tabLayout);
        fragmentManager = getSupportFragmentManager();
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "reservesBD").allowMainThreadQueries().build();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
//                return;
                int selectedTabPosition = savedInstanceState.getInt(SELECTED_TAB_KEY, 0);
                tablayout.getTabAt(selectedTabPosition).select();
                // Restore the corresponding fragment based on the selected tab position
                switch (selectedTabPosition) {
                    case 0:
                        fragment = new Product_Fragment();
//                        if(savedInstanceState.containsKey("product_name")){
//                            String product_name = savedInstanceState.getString("product_name");
//                            ((Product_Fragment)fragment).setTextFieldName(product_name);
//                        }
                        break;
                    case 1:
                        fragment = new Supplier_Fragment();
                        break;
                    case 2:
                        fragment = new Supplies_Fragment();
                        break;
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
//            fragmentManager.beginTransaction().add(R.id.fragment_container, new Product_Fragment()).setTransition(fragmentManager.TRANSIT_FRAGMENT_OPEN).commit();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, new Product_Fragment());
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new Product_Fragment();
                        break;

                    case 1:
                        fragment = new Supplier_Fragment();
                        break;

                    case 2:
                        fragment = new Supplies_Fragment();
                        break;
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

//    public void pushNotification() {
//        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(fragment.requireContext(), "myChLocalDatabase")
//                .setSmallIcon(R.drawable.warning)
//                .setContentTitle("Notification")
////        if(fragment.requireContext().equals(Product_Fragment.class.getName()))
//                .setContentText("Order successfully added!");
////        else if(fragment.requireContext().equals(Supplier_Fragment.class.getName()))
////            builder.setContentText("Supplier successfully added!");
////        else
////            builder.setContentText("Supply successfully added!!!");
//
////        Notification notification = builder.build();
//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(fragment.requireContext());
//
//        if (ActivityCompat.checkSelfPermission(fragment.requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        notificationManagerCompat.notify(1, builder1.build());
//    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer((GravityCompat.START));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public Toolbar makeToolbarButton() {
        Toolbar toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_button);
        actionBar.setTitle("");
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.toolbar_title_to_main_activity); //        toolbarTitle.setText("Economy E-shop Application");
        return toolbar;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart callback method");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart callback method");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume callback method");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause callback method");
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState){
//            super.onSaveInstanceState(outState);
//        Log.i(TAG,"onSaveInstanceState callback method");
//    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState callback method");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop callback method");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy callback method");
    }


//    @Override
//    public void onBackPressed() {
//        // Get the next activity that will be launched
//        Intent nextIntent = new Intent(this, LoginActivity.class);  // Replace with the specific activity you want to check against
//
//        PackageManager packageManager = getPackageManager();
//        ComponentName componentName = nextIntent.resolveActivity(packageManager);
//
//        // Check if the next activity is the one you want to handle differently which is LoginActivity.class
//        if (componentName != null && componentName.getClassName().equals(LoginActivity.class.getName())) {
//            // Do nothing
//            // Since the next activity is the specific activity, we skip the super.onBackPressed() call
//        } else {
//            // Call the superclass implementation to allow the default behavior
//            super.onBackPressed();
//        }
//    }

}


