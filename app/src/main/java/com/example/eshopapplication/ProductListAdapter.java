package com.example.eshopapplication;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    private List<ProductInfo> productInfo;

    public ProductListAdapter(List<ProductInfo> productInfo) {
        this.productInfo = productInfo;
    }

    //private int image = R.drawable.image;
    Context context;

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product_card_view, parent, false);
        // set the view's and layout parameters
        // pass the view to ViewHolder
        ProductListViewHolder vh = new ProductListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, final int position) {
        // set the data in items

        holder.productNameTxt.setText(productInfo.get(position).getProductName());
        holder.categoryTxt.setText(productInfo.get(position).getCategory());
        holder.supplierTxt.setText(productInfo.get(position).getSupplier());
        holder.stockTxt.setText("Stock: " + String.valueOf(productInfo.get(position).getStock()));
        holder.priceTxt.setText(String.valueOf(productInfo.get(position).getPrice())); // ***************************//

        holder.productImg.setImageBitmap(BitmapFactory.decodeByteArray(productInfo.get(position).getImage(), 0, productInfo.get(position).getImage().length));

        // detect item selection
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(view.getContext(), "The card "+(position+1)+" is selected", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productInfo.size();
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView productNameTxt, categoryTxt, supplierTxt, stockTxt, priceTxt;
        ImageView productImg;

        public ProductListViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's

            productNameTxt = (TextView) itemView.findViewById(R.id.text_product_item_name);
            categoryTxt = (TextView) itemView.findViewById(R.id.text_product_item_category);
            supplierTxt = (TextView) itemView.findViewById(R.id.text_product_supplier);
            stockTxt = (TextView) itemView.findViewById(R.id.product_stock);
            priceTxt = (TextView) itemView.findViewById(R.id.product_price);

            productImg = (ImageView) itemView.findViewById(R.id.productImageView);


        }
    }
}