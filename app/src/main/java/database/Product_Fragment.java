package database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.eshopapplication.MainActivity;
import com.example.eshopapplication.R;
import com.google.android.material.textfield.TextInputEditText;

public class Product_Fragment extends Fragment {

    Button insertButton, deleteButton, updateButton, queryButton;

    TextInputEditText id, name, category, price, stock;

    public Product_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        id = view.findViewById(R.id.product_id);
        name = view.findViewById(R.id.product_name);
        category = view.findViewById(R.id.product_category);
        price = view.findViewById(R.id.product_price);
        stock = view.findViewById(R.id.product_stock);

        insertButton = view.findViewById(R.id.insert_button);
        queryButton = view.findViewById(R.id.query_button);
        updateButton = view.findViewById(R.id.update_button);
        deleteButton = view.findViewById(R.id.delete_button);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int var_id = 0;
                try {
                    var_id = Integer.parseInt(id.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                String var_name = name.getText().toString();
                String var_category = category.getText().toString();
                double var_price = 0;
                try {
                    var_price = Double.parseDouble(price.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                int var_stock = 0;
                try {
                    var_stock = Integer.parseInt(stock.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }

                try {
                    Product product = new Product();
                    product.setId(var_id);
                    product.setName(var_name);
                    product.setCategory(var_category);
                    product.setPrice(var_price);
                    product.setStock(var_stock);
                    MainActivity.myAppDatabase.myDao().addProduct(product);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXEIS H PROSTHIKI TOY NEOY PROIONTOS
                } catch (Exception e) {
                    String message = e.getMessage();
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }
                id.setText("");
                name.setText("");
                category.setText("");
                price.setText("");
//                stock.setText("Stock: 0");
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

}