package com.example.eshopapplication;

//import static com.example.eshopapplication.MainActivity.showInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import database.Supplier;
import remote.database.Orders_Activity;

public class Supplier_Info_Activity extends AppCompatActivity {

    private List<Supplier> suppliers = MainActivity.myAppDatabase.myDao().getSupplier();

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter myAdapter;

    private signuploginfirebase.sharedPreferenceConfig sharedPreferenceConfig;
    TextView username_text, email_text, toolbarTitle;
    AlertDialog.Builder builder;

    public int getSuppliersCount() {
        return suppliers.size();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_info);
        builder = new AlertDialog.Builder(this);
//    int selectedItem = getIntent().getIntExtra("selectedItem",-1);

        int supplierCount = getSuppliersCount();

        if (supplierCount == 0) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new NoSupplierFragment());
            transaction.commit();
        } else {
            // continue showing the current fragment that displays supplier information
            recyclerView = findViewById(R.id.recyclerView);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            SupplierListAdapter supplierListAdapter = new SupplierListAdapter(this);
            recyclerView.setAdapter(supplierListAdapter);
        }


//        MainActivity toolbarButton = new MainActivity();
//        toolbarButton.makeToolbarButton(toolbar);
        toolbar = makeToolbarButton();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.dr_supplier_info);
//        navigationView.setCheckedItem(selectedItem);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dr_database:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Supplier_Info_Activity.this, MainActivity.class));
//                        Intent intent = new Intent(Logout_Activity.this, LoginActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("StringKey1", "from :- Logout Activity");
//                        Supplier_Fragment fv = new Supplier_Fragment();
//                        fv.setArguments(bundle);
//                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.dr_orders:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Supplier_Info_Activity.this, Orders_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.dr_product_inventory:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Supplier_Info_Activity.this, Product_Inventory_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.dr_supplier_info:
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.dr_about:
                        menuItem.setChecked(true);
                        //showInfo(builder);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.logout:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Supplier_Info_Activity.this, SettingsActivity.class));
//                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
//                        Intent n = new Intent(MainActivity.this, Logout_Activity.class);
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseuser != null){
            String userName = firebaseuser.getDisplayName();
            String userEmail = firebaseuser.getEmail();
            View menu_drawer_head = navigationView.getHeaderView(0);
            username_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_username_text);
            email_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_email_text);
            username_text.setText(userName);
            email_text.setText(userEmail);
        }
        else{
            View menu_drawer_head = navigationView.getHeaderView(0);
            username_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_username_text);
            email_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_email_text);
            username_text.setText("Username: Unknown");
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

    public Toolbar makeToolbarButton(){
        toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_button);
        actionBar.setTitle("");
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.app_name); //  edw na valoyme string "Supplier Info"
        return toolbar;
    }

//    @Override
//    public void onBackPressed(){
//        Intent intent = new Intent(Supplier_Info_Activity.this,SettingsActivity.class);
//        intent.putExtra("selectedItem", R.id.logout);
//        startActivity(intent);
//        finish();
//    }

}
