package com.example.eshopapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import database.Supplier;

public class SupplierListAdapter extends RecyclerView.Adapter<SupplierListAdapter.SupplierListViewHolder> {
    private List<Supplier> suppliers = MainActivity.myAppDatabase.myDao().getSupplier();

    private Context context;

    public SupplierListAdapter(Context context){
        this.context = context;
    }


    @Override
    public SupplierListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate the view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_supplier_card_view, parent, false);
        // set the view's and layout parameters
        // pass the view to ViewHolder
        SupplierListViewHolder vh = new SupplierListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SupplierListViewHolder holder, final int position) {
        // set the data in items
        //holder.nameTxt.setText(text[position]);
        holder.nameTxt.setText(suppliers.get(position).getName());
        holder.addressTxt.setText(suppliers.get(position).getAddress());
        holder.phoneBtn.setText(suppliers.get(position).getPhone());
        holder.emailBtn.setText(suppliers.get(position).getEmail());

        holder.phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an implicit intent to open the phone app with the phone number
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + suppliers.get(position).getPhone()));

                // Start the activity with the intent
                context.startActivity(intent);
            }
        });



        holder.emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an implicit intent to open the email app with the email address
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + suppliers.get(position).getEmail()));

                // Start the activity with the intent
                context.startActivity(intent);
            }
        });

//        // detect item selection
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "The card "+(position+1)+" is selected", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return suppliers.size();
    }

    public class SupplierListViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView nameTxt, addressTxt;
        Button phoneBtn, emailBtn;

        public SupplierListViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            nameTxt = (TextView) itemView.findViewById(R.id.text_customer_name);
            addressTxt = (TextView) itemView.findViewById(R.id.Date);
            phoneBtn = (Button) itemView.findViewById(R.id.btn_supplier_phone);
            emailBtn = (Button)itemView.findViewById(R.id.btn_supplier_email);
        }
    }
}


