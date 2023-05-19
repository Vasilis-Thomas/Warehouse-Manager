package remote.database;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eshopapplication.MainActivity;
import com.example.eshopapplication.R;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>{


    private Context context;

    public OrderListAdapter(Context context){
        this.context = context;
    }


    @Override
    public OrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate the view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_card_view, parent, false);
        // set the view's and layout parameters
        // pass the view to ViewHolder
        OrderListViewHolder vh = new OrderListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(OrderListViewHolder holder, final int position) {
        // set the data in items

        holder.customerName.setText(Order_Info_Activity.orderList.get(position).getCustomerName());
        holder.date.setText(Order_Info_Activity.orderList.get(position).getOrderDate());


        String productName = MainActivity.myAppDatabase.myDao().getProductName(Order_Info_Activity.orderList.get(position).getProductID());
        holder.productName.setText(productName);

        holder.orderID.setText("OrderID: " + String.valueOf(Order_Info_Activity.orderList.get(position).getOrderID()));
        holder.quantity.setText("Quantity: " + String.valueOf(Order_Info_Activity.orderList.get(position).getQuantity()));


        double cost =  MainActivity.myAppDatabase.myDao().getProductPrice(Order_Info_Activity.orderList.get(position).getProductID()) * Order_Info_Activity.orderList.get(position).getQuantity();

        holder.cost.setText(String.valueOf(cost));

    }

    @Override
    public int getItemCount() {
        return Order_Info_Activity.orderList.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView customerName, date, productName, orderID, quantity, cost;


        public OrderListViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            customerName = (TextView) itemView.findViewById(R.id.text_customer_name);
            date = (TextView) itemView.findViewById(R.id.DateValue);
            productName = (TextView) itemView.findViewById(R.id.productName);
            orderID = (TextView) itemView.findViewById(R.id.orderID);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            cost = (TextView) itemView.findViewById(R.id.cost);
        }
    }

}
