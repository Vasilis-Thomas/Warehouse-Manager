package database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.eshopapplication.MainActivity;
import com.example.eshopapplication.R;
import com.google.android.material.textfield.TextInputEditText;

public class Product_Fragment extends Fragment {


    TextInputEditText id, name, category, price;
//    TextInputLayout id, name, category, price;
    Button insertButton, deleteButton, updateButton, queryButton;
    TextView stock;


    public Product_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

//        id = view.findViewById(R.id.product_id_til);
//        name = view.findViewById(R.id.product_name_til);
//        category = view.findViewById(R.id.product_category_til);
//        price = view.findViewById(R.id.product_price_til);


        id = view.findViewById(R.id.product_id_tiet);
        name = view.findViewById(R.id.product_name_tiet);
        category = view.findViewById(R.id.product_category_tiet);
        price = view.findViewById(R.id.product_price_tiet);
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
//                    var_id = Integer.parseInt(id.getEditText().getText().toString()); // EAN TO id HTAN TYPOU TextInputLayout
                    var_id = Integer.parseInt(id.getText().toString());  // EDW TO id EINAI TPYOU TextInputEditText
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
//                int var_stock = 0;
//                try {
//                    var_stock = Integer.parseInt(stock.getText().toString());
//                } catch (NumberFormatException exception) {
//                    System.out.println("Could not parse" + exception);
//                }
                try {
                    Product product = new Product();
                    product.setPid(var_id);
                    product.setName(var_name);
                    product.setCategory(var_category);
                    product.setPrice(var_price);
//                    product.setStock(var_stock);
                    MainActivity.myAppDatabase.myDao().addProduct(product);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXEIS H PROSTHIKI TOY NEOY PROIONTOS
                    Toast.makeText(getActivity(),"Record added.",Toast.LENGTH_LONG).show(); // AYTO THA FYGEI
                } catch (Exception e) {
                    String message = e.getMessage();
                    //ANTISTOIXA MPOROYME NA VALOYME TO NOTIFICATION GIA UNSUCCESFUL YLOPOIHSH
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();  // AYTO THA FYGEI
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


        //AYTH H METHOD YPARXEI GIA THN DIAXEIRISI TWN ERRORS STA TextInputEditText
//    private void setupFloatingLabelError() {
//        final TextInputLayout floatingUsernameLabel = (TextInputLayout) view.findViewById(R.id.product_id);
//        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
//            // ...
//            @Override
//            public void onTextChanged(CharSequence text, int start, int count, int after) {
//                if (text.length() > 0 && text.length() <= 4) {
//                    floatingUsernameLabel.setError("Username is required");
//                    floatingUsernameLabel.setErrorEnabled(true);
//                } else {
//                    floatingUsernameLabel.setErrorEnabled(false);
//                }
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                floatingUsernameLabel.setHintTextColor(ColorStateList.valueOf(floatingUsernameLabel.getContext().getColor(R.color.teal_200)));
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }

    }

}