package database;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.eshopapplication.MainActivity;
import com.example.eshopapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class Product_Fragment extends Fragment {
    private final static String TAG = "remote.database (Product_Fragment)";
    TextInputEditText id, name, category, price, stock;
//    TextInputLayout id, name, category, price;
    Button insertButton, deleteButton, updateButton, queryButton;
//    TextView stock;

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
        stock = view.findViewById(R.id.product_stock_tiet);
//        stock = view.findViewById(R.id.product_stock);

        insertButton = view.findViewById(R.id.insert_button);
        queryButton = view.findViewById(R.id.query_button);
        updateButton = view.findViewById(R.id.update_button);
        deleteButton = view.findViewById(R.id.delete_button);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(id.getText().length()==0)      { id.setError("You must fill this field");       }
                if(name.getText().length()==0)    { name.setError("You must fill this field");     }
                if(category.getText().length()==0){ category.setError("You must fill this field"); }
                if(price.getText().length()==0)   { price.setError("You must fill this field");    }
                if(stock.getText().length()==0)   { stock.setError("You must fill this field");    }

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
                int var_stock = 0;
                try {
                    var_stock = Integer.parseInt(stock.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }

                try {
                    if(name.getText().length()==0 || category.getText().length()==0 || price.getText().length()==0)
                        throw new Exception("Exception thrown");

                    Product product = new Product();
                    product.setPid(var_id);
                    product.setName(var_name);
                    product.setCategory(var_category);
                    product.setPrice(var_price);
                    product.setStock(var_stock);
                    MainActivity.myAppDatabase.myDao().addProduct(product);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXEIS H PROSTHIKI TOY NEOY PROIONTOS
                    Toast.makeText(getActivity(),"Record added.",Toast.LENGTH_LONG).show(); // AYTO THA FYGEI

                    id.setText("");
                    name.setText("");
                    category.setText("");
                    price.setText("");
                    stock.setText("");
                    setErrorMessagesToNull();
//                stock.setText("Stock: 0");
                } catch (Exception e) {
                    String message = e.getMessage();
                    //ANTISTOIXA MPOROYME NA VALOYME TO NOTIFICATION GIA UNSUCCESFUL YLOPOIHSH
//                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();  // AYTO THA FYGEI

                    boolean flagProductID = false;
                    List<Product> aproduct= MainActivity.myAppDatabase.myDao().getProduct();
                    for(Product i: aproduct){
                        int var_productID_for_check = i.getPid();
                        if(var_productID_for_check == var_id){
                            flagProductID = true;  // THA GINEI true APO TH STIGMH POY TO var_productID YPARXEI STHN VASH DHLADH STON PINAKA product
                            Log.i(TAG,"flagProductID set true");
                            break;
                        }
                    }
                    if(id.getText().length()==0){
                    id.setError("You must fill this field");
                    }
                    else if(flagProductID){
                        id.setError("The productID you filled has already registered\nPlease give another productID");
                    }
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
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
                int var_stock = 0;
                try {
                    var_stock = Integer.parseInt(stock.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }

                boolean flagProductID = false;
                String currentName="Unknown", currentCategory="Unknown";
                double currentPrice = 0.0;
                int currentStock= 0;
                List<Product> aproduct= MainActivity.myAppDatabase.myDao().getProduct();
                for(Product i: aproduct){
                    int var_productID_for_check = i.getPid();
                    if(var_productID_for_check == var_id){
                        currentName = i.getName();
                        currentCategory = i.getCategory();
                        currentPrice = i.getPrice();
                        currentStock = i.getStock();
                        flagProductID = true;  // THA GINEI true APO TH STIGMH POY TO var_productID YPARXEI STHN VASH DHLADH STON PINAKA product
                        Log.i(TAG,"To flagProductID egine true");
                        break;
                    }
                }
                try {
                    if (id.getText().toString().isEmpty() || !flagProductID)
                        throw new Exception("Exception thrown");

                    Product product = new Product();
                    product.setPid(var_id);
                    if(var_name.isEmpty()) product.setName(currentName);    else product.setName(var_name);
                    if(var_category.isEmpty()) product.setCategory(currentCategory); else product.setCategory(var_category);
                    if(var_price == 0.0) product.setPrice(currentPrice); else product.setPrice(var_price);
                    if(var_stock == 0) product.setStock(currentStock); else product.setStock(var_stock);

                    MainActivity.myAppDatabase.myDao().updateProduct(product);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXEIS H PROSTHIKI TOY NEOY PROIONTOS
                    Toast.makeText(getActivity(),"Record added.",Toast.LENGTH_LONG).show(); // AYTO THA FYGEI

                    id.setText("");
                    name.setText("");
                    category.setText("");
                    price.setText("");
                    stock.setText("");
                    setErrorMessagesToNull();
//                stock.setText("Stock: 0");
                }catch (Exception e){
                    String message = e.getMessage();
                    //ANTISTOIXA MPROYME NA VALOYME TO NOTIFICATION GIA UNSUCCESFUL YLOPOIHSH
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show(); // AYTO THA FYGEI

                    if (id.getText().length() == 0) {
                        id.setError("You must fill this field");
                    } else if (!flagProductID) {  // EAN DEN YPARXEI STH VASH TO SYGKEKRIMENO productID
                        id.setError("The productID you filled does not exist\nPlease give an already recorded productID");
                    }
                }
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int var_id = 0;
                try {
                    var_id = Integer.parseInt(id.getText().toString());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse" + ex);
                }

                    boolean flagProductID = false;
                    List<Product> aproduct = MainActivity.myAppDatabase.myDao().getProduct();
                    for (Product i : aproduct) {
                        int var_productID_for_check = i.getPid();
                        if (var_productID_for_check == var_id) {
                            flagProductID = true;
                            break;
                        }
                    }
                try {
                    if(id.getText().length() == 0 || !flagProductID)
                        throw new Exception("Exception thrown");
                    Product product = new Product();
                    product.setPid(var_id);
                    MainActivity.myAppDatabase.myDao().deleteProduct(product);
                    Toast.makeText(getActivity(), "Product delete successfully", Toast.LENGTH_LONG).show();
                    id.setText("");
                    name.setText("");
                    category.setText("");
                    price.setText("");
                    stock.setText("");
                    setErrorMessagesToNull();
                } catch (Exception e) {
                    String message = e.getMessage();
                    Toast.makeText(getActivity(), "Product delete dint happend", Toast.LENGTH_LONG).show();
                    if(id.getText().length() == 0)
                        id.setError("You must fill this field");
                    else if(!flagProductID)
                        id.setError("The productID you filled does not exist");
                }
            }
        });
        return view;
    }

    public void setErrorMessagesToNull(){
        if(id.getText().length()==0)      { id.setError(null);       }
        if(name.getText().length()==0)    { name.setError(null);     }
        if(category.getText().length()==0){ category.setError(null); }
        if(price.getText().length()==0)   { price.setError(null);    }
        if(stock.getText().length()==0)   { stock.setError(null);    }
    }

}