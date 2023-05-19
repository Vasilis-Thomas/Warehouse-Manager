package remote.database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.eshopapplication.MainActivity;
import com.example.eshopapplication.Product_Inventory_Activity;
import com.example.eshopapplication.R;
import com.example.eshopapplication.SettingsActivity;
import com.example.eshopapplication.Supplier_Info_Activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Order_Info_Activity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter myAdapter;

    private signuploginfirebase.sharedPreferenceConfig sharedPreferenceConfig;
    TextView username_text, email_text, toolbarTitle;
    AlertDialog.Builder builder;


    public static List<Orders> orderList;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    CollectionReference collectionReference = firestore.collection("Orders");

    public int getOrdersCount() {
        return orderList.size();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);



        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        orderList = new ArrayList<>();

                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            Orders orders = documentSnapshot.toObject(Orders.class);
                            Integer orderId = orders.getOrderID();
                            Integer productId = orders.getProductID();
                            String cName = orders.getCustomerName();
                            String orderDate = orders.getOrderDate();
                            Integer quantity = orders.getQuantity();
                            orderList.add(orders);
                        }
//                        querytextresult.setText(result);
                        int ordersCount = getOrdersCount();

                        if (ordersCount == 0) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, new NoOrderFragment());
                            transaction.commit();
                        } else {
                            // continue showing the current fragment that displays supplier information
                            recyclerView = findViewById(R.id.recyclerView);
                            layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            OrderListAdapter orderListAdapter = new OrderListAdapter(getApplicationContext());
                            recyclerView.setAdapter(orderListAdapter);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Error retrieving data", e);
                        //Toast.makeText(getActivity(),"query operation failed.", Toast.LENGTH_LONG).show();
                    }
                });





//        MainActivity toolbarButton = new MainActivity();
//        toolbarButton.makeToolbarButton(toolbar);
        toolbar = makeToolbarButton();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.dr_order_info);
//        navigationView.setCheckedItem(selectedItem);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dr_database:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Order_Info_Activity.this, MainActivity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.dr_orders:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Order_Info_Activity.this, Orders_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.dr_order_info:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Order_Info_Activity.this, Order_Info_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.dr_product_inventory:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Order_Info_Activity.this, Product_Inventory_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.dr_supplier_info:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Order_Info_Activity.this, Supplier_Info_Activity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.settings:
                        menuItem.setChecked(true);
                        startActivity(new Intent(Order_Info_Activity.this, SettingsActivity.class));
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
        toolbarTitle.setText(R.string.toolbar_title_to_order_info_activity); //  edw na valoyme string "Supplier Info"
        return toolbar;
    }
}
