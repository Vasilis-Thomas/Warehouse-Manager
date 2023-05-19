package database;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.eshopapplication.MainActivity;
import com.example.eshopapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Product_Fragment extends Fragment {
    private final static String TAG = "remote.database (Product_Fragment)";
    TextInputEditText id, name, category, price, stock;
//    TextInputLayout id, name, category, price;
    Button insertButton, deleteButton, updateButton, queryButton, addImageButton;
//    TextView stock;
    ImageView imageView;

    Bitmap bitmap = null;

    public Product_Fragment() {
        // Required empty public constructor
    }



    @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

        // Check if the result is for the image picker
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageView = getView().findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);

        }
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

//        // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
//        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
////                        if (result.getResultCode() == Activity.RESULT_OK) {
////                            // There are no request codes
//                            Intent data = result.getData();
//                        if (result.getResultCode() == Activity.RESULT_OK && data != null) {
//                            Uri imageUri = data.getData();
//                            try {
//                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            imageView = getView().findViewById(R.id.imageView);
//                            imageView.setImageBitmap(bitmap);
//                        }
//                    }
//                });

//        id = view.findViewById(R.id.product_id_til);
//        name = view.findViewById(R.id.product_name_til);
//        category = view.findViewById(R.id.product_category_til);
//        price = view.findViewById(R.id.product_price_til);
//        stock = view.findViewById(R.id.product_stock);

        id = view.findViewById(R.id.product_id_tiet);
        name = view.findViewById(R.id.product_name_tiet);
        category = view.findViewById(R.id.product_category_tiet);
        price = view.findViewById(R.id.product_price_tiet);
        stock = view.findViewById(R.id.product_stock_tiet);

        addImageButton = view.findViewById(R.id.add_image_button);
        imageView = view.findViewById(R.id.imageView);

        insertButton = view.findViewById(R.id.insert_button);
        queryButton = view.findViewById(R.id.query_button);
        updateButton = view.findViewById(R.id.update_button);
        deleteButton = view.findViewById(R.id.delete_button);


        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start the intent and wait for the user to pick an image
                startActivityForResult(intent, 1);
            }
        });


        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(id.getText().length()==0)      { id.setError("You must fill this field");       }
                if(name.getText().length()==0)    { name.setError("You must fill this field");     }
                if(category.getText().length()==0){ category.setError("You must fill this field"); }
                if(price.getText().length()==0)   { price.setError("You must fill this field");    }
                if(stock.getText().length()==0)   { stock.setError("You must fill this field");    }
                if(imageView.getDrawable() == null) {addImageButton.setError("You must pick an image for the product"); }


                // get the bitmap from the ImageView
                bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                // convert the bitmap to a byte[] array
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] imageData = outputStream.toByteArray();



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

                    product.setImage(imageData);

                    MainActivity.myAppDatabase.myDao().addProduct(product);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXEIS H PROSTHIKI TOY NEOY PROIONTOS
                    Toast.makeText(getActivity(),"Record added.",Toast.LENGTH_LONG).show(); // AYTO THA FYGEI


                    id.setText("");
                    name.setText("");
                    category.setText("");
                    price.setText("");
                    stock.setText("");
                    imageView.setImageResource(R.drawable.photo);
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



                // get the bitmap from the ImageView
                bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                // convert the bitmap to a byte[] array
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] var_image = outputStream.toByteArray();


                // the default image is placed to imageView so that there are no differences in resolution
                imageView.setImageResource(R.drawable.photo);
                //Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);

                Bitmap defaultBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                ByteArrayOutputStream outputStreamdef = new ByteArrayOutputStream();
                defaultBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStreamdef);
                byte[] def_image = outputStreamdef.toByteArray();




                boolean flagProductID = false;
                String currentName="Unknown", currentCategory="Unknown";
                double currentPrice = 0.0;
                int currentStock= 0;
                byte[] currentImage = null;

                Bitmap currentBitmap = null;

                List<Product> aproduct= MainActivity.myAppDatabase.myDao().getProduct();
                for(Product i: aproduct){
                    int var_productID_for_check = i.getPid();
                    if(var_productID_for_check == var_id){
                        currentName = i.getName();
                        currentCategory = i.getCategory();
                        currentPrice = i.getPrice();
                        currentStock = i.getStock();
                        currentImage = i.getImage();
                        currentBitmap = BitmapFactory.decodeByteArray( currentImage, 0, currentImage.length);
//                        currentBitmap = BitmapFactory.decodeByteArray(i.getImage(), 0, currentImage.length);
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
                    if(compareBitmaps(bitmap, currentBitmap)){
                        product.setImage(currentImage);
                    }else if(compareBitmaps(bitmap, defaultBitmap)){
                        product.setImage(currentImage);
                    }
                    else{
                        product.setImage(var_image);
                    }

                    MainActivity.myAppDatabase.myDao().updateProduct(product);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXEIS H PROSTHIKI TOY NEOY PROIONTOS
                    Toast.makeText(getActivity(),"Record added.",Toast.LENGTH_LONG).show(); // AYTO THA FYGEI

                    id.setText("");
                    name.setText("");
                    category.setText("");
                    price.setText("");
                    stock.setText("");
                    imageView.setImageResource(R.drawable.photo);
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

    public boolean compareBitmaps(Bitmap bitmap1, Bitmap bitmap2) {
        if (bitmap1 == null || bitmap2 == null) {
            return false;
        }

        int width1 = bitmap1.getWidth();
        int height1 = bitmap1.getHeight();
        int width2 = bitmap2.getWidth();
        int height2 = bitmap2.getHeight();

        if (width1 * height1 > width2 * height2) {
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, width2, height2, true);
        } else {
            bitmap2 = Bitmap.createScaledBitmap(bitmap2, width1, height1, true);
        }


        if (bitmap1.sameAs(bitmap2)) {
            // The images are equal
            return true;
        } else {
            // The images are not equal
            return false;
        }
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("id_pr",id.getText().toString());
//        outState.putString("name_pr",name.getText().toString());
//        outState.putString("category_pr",category.getText().toString());
//        outState.putString("price",price.getText().toString());
//        outState.putString("stock",stock.getText().toString());
//    }
//
//    public String getTextFieldName() {
//        if (name != null) {
//            return name.getText().toString();
//        }
//        return null;
//    }
//
//    public void setTextFieldName(String productName) {
//        if (name != null) {
//            name.setText(productName);
//        }
//    }
}