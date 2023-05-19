package com.example.eshopapplication;

//import static com.example.eshopapplication.MainActivity.showInfo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import remote.database.Orders_Activity;

public class Product_Inventory_Activity extends AppCompatActivity{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    FrameLayout frameLayout;

    RecyclerView.LayoutManager layoutManager;

    private signuploginfirebase.sharedPreferenceConfig sharedPreferenceConfig;
    TextView username_text, email_text, toolbarTitle;
    AlertDialog.Builder builder;

    private List<ProductInfo> products;
    private Spinner supplierSpinner, categorySpinner;
    private List<String> suppliersName, categoryNames;
    private ArrayAdapter<String> sAdapter, cAdapter;
    private String defaultSupplier = new String("All Suppliers"), defaultCategory = new String("All Categories");
    private String selectedCategory = defaultCategory, selectedSupplier = defaultSupplier;

    public int getProductsCount() {
        return products.size();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        builder = new AlertDialog.Builder(this);


        supplierSpinner = findViewById(R.id.supplierSpinner);
        suppliersName = new ArrayList<String>();
        sAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, suppliersName);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supplierSpinner.setAdapter(sAdapter);
        loadSuppliersName();

        categorySpinner = findViewById(R.id.categorySpinner);
        categoryNames = new ArrayList<String>();
        cAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(cAdapter);
        loadCategories();

        Button searchBtn = findViewById(R.id.searchProducts);

        // Create an event listener for the spinner
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the selected product
                selectedCategory = (String) adapterView.getItemAtPosition(position);

                // Do something with the selected product
                Toast.makeText(Product_Inventory_Activity.this, "Selected Category: " + selectedCategory, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });


        // Create an event listener for the spinner
        supplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the selected product
                selectedSupplier = (String) adapterView.getItemAtPosition(position);

                // Do something with the selected product
                Toast.makeText(Product_Inventory_Activity.this, "Selected Supplier: " + selectedSupplier, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedSupplier.equals(defaultSupplier) && selectedCategory.equals(defaultCategory)) {
                    products = MainActivity.myAppDatabase.myDao().getAllProductsCategoriesAllSuppliers();
                } else if (!selectedSupplier.equals(defaultSupplier) && selectedCategory.equals(defaultCategory)) {
                    products = MainActivity.myAppDatabase.myDao().getAllProductsCategories_Supplier(selectedSupplier);
                }else if(selectedSupplier.equals(defaultSupplier) && !selectedCategory.equals(defaultCategory)){
                    products = MainActivity.myAppDatabase.myDao().get_ProductsCategoriesAllSupplier(selectedCategory);
                }else {
                    products = MainActivity.myAppDatabase.myDao().get_ProductsCategories_Supplier(selectedSupplier, selectedCategory);
                }

                int productsCount = getProductsCount();

                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setAdapter(null);
                frameLayout = findViewById(R.id.fragment_container);
                frameLayout.setVisibility(View.INVISIBLE);

                if (productsCount == 0){
                    frameLayout.setVisibility(View.VISIBLE);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new NoProductFragment());
                    transaction.commit();
                }
                else {
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    ProductListAdapter productListAdapter = new ProductListAdapter(products);
                    recyclerView.setAdapter(productListAdapter);

                    AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
                    anim.setDuration(400);
                    recyclerView.startAnimation(anim);

//                    runLayoutAnimation(recyclerView);
                }

            }

            private void runLayoutAnimation(final RecyclerView recyclerView) {
                final Context context = recyclerView.getContext();
                final LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

                recyclerView.setLayoutAnimation(controller);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.scheduleLayoutAnimation();
            }

        });


        searchBtn.performClick();

        toolbar = makeToolbarButton();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.dr_product_inventory);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dr_database:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Product_Inventory_Activity.this, MainActivity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.dr_orders:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Product_Inventory_Activity.this, Orders_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.dr_product_inventory:
                        menuItem.setChecked(true);
                        return true;
                    case R.id.dr_supplier_info:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Product_Inventory_Activity.this, Supplier_Info_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.dr_about:
                        menuItem.setChecked(true);
                        //showInfo(builder);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.logout:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Product_Inventory_Activity.this, SettingsActivity.class));
//                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
//                        Intent n = new Intent(MainActivity.this, Logout_Activity.class);
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseuser != null) {
            String userName = firebaseuser.getDisplayName();
            String userEmail = firebaseuser.getEmail();
            View menu_drawer_head = navigationView.getHeaderView(0);
            username_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_username_text);
            email_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_email_text);
            username_text.setText(userName);
            email_text.setText(userEmail);
        } else {
            View menu_drawer_head = navigationView.getHeaderView(0);
            username_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_username_text);
            email_text = menu_drawer_head.findViewById(R.id.drawer_menu_header_email_text);
            username_text.setText("Username: Unknown");
            email_text.setText("Unknown");
        }

    }

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
        toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_button);
        actionBar.setTitle("");
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.app_name); //  edw na valoyme string "Product Inventory
        return toolbar;
    }


    @SuppressLint("StaticFieldLeak")
    private void loadSuppliersName() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return MainActivity.myAppDatabase.myDao().getSupplierName();
            }

            @Override
            protected void onPostExecute(List<String> sNames) {
                suppliersName.clear();
                //defaultSupplier = new String("All Suppliers");
                suppliersName.add(defaultSupplier);
                suppliersName.addAll(sNames);
                sAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadCategories() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return MainActivity.myAppDatabase.myDao().getCategoryNames();
            }

            @Override
            protected void onPostExecute(List<String> cNames) {
                categoryNames.clear();
                //defaultCategory = new String("All Categories");
                categoryNames.add(defaultCategory);
                categoryNames.addAll(cNames);
                cAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
