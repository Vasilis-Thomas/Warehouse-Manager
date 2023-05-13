package remote.database;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eshopapplication.MainActivity;
import com.example.eshopapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.List;

import database.Product;

public class Orders_Activity extends AppCompatActivity {
    private final static String TAG = "remote.database (Orders Activity)";
    protected static FirebaseFirestore db;
    //    TextInputEditText orderID, productID, customerName, orderDate, quantity; // msrp = recommended supplier price
    TextInputEditText orderID, productID, customerName, quantity; // msrp = recommended supplier price
    Button insertButton, deleteButton, updateButton, queryButton;
    TextView displaySupplyError, questionText;
    EditText dateEdt;
    TextView orderDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        displaySupplyError = findViewById(R.id.supply_error_textview);
        questionText = findViewById(R.id.question_text);

        orderID = findViewById(R.id.orders_orderid_tiet);
        productID = findViewById(R.id.orders_productid_tiet);
        customerName = findViewById(R.id.orders_customernameid_tiet);
        orderDate = findViewById(R.id.orders_orderdate_tiet);
        quantity = findViewById(R.id.orders_quantity_tiet);

        insertButton = findViewById(R.id.insert_button);
        queryButton = findViewById(R.id.query_button);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);

        dateEdt = findViewById(R.id.orders_orderdate_tiet);
        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        Orders_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String yearString = Integer.toString(year); // WE ARE DOING THIS AND THE NEXT LINE
                                year = Integer.parseInt(yearString.substring(1)); // TO REMOVE THE FIRST 2 DIGITS FROM LIKE 2023 ->> 23
//                                dateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                if (dayOfMonth < 10 && monthOfYear < 10)
                                    dateEdt.setText("0" + dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                                else if (dayOfMonth < 10)
                                    dateEdt.setText("0" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                else if (monthOfYear < 10)
                                    dateEdt.setText("" + dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                                else
                                    dateEdt.setText("" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySupplyError.setText("");
                if (orderID.getText().length() == 0) {
                    orderID.setError("You must fill this field");
                }
                if (productID.getText().length() == 0) {
                    productID.setError("You must fill this field");
                }
                if (customerName.getText().length() == 0) {
                    customerName.setError("You must fill this field");
                }
                if (orderDate.getText().length() == 0) {
                    orderDate.setError("You must fill this field");
                }
                if (quantity.getText().length() == 0) {
                    quantity.setError("You must fill this field");
                }

                int var_orderID = 0;
                try {
                    var_orderID = Integer.parseInt(orderID.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                int var_productID = 0;
                try {
                    var_productID = Integer.parseInt(productID.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                String var_customerName = customerName.getText().toString();
                String var_orderDate = orderDate.getText().toString();
                int var_quantity = 0;
                try {
                    var_quantity = Integer.parseInt(quantity.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }

//                if(productID.getText().length()==0 || supplierID.getText().length()==0 ||
//                        supplyDate.getText().length()==0 || quantity.getText().length()==0 ||
//                        msrp.getText().length()==0){
//                    return;
//                }

                try {
                    boolean flagProductID = false;
                    List<Product> aproduct = MainActivity.myAppDatabase.myDao().getProduct();
                    for (Product i : aproduct) {
                        int var_productID_for_check = i.getPid();
                        if (var_productID_for_check == var_productID) {
                            flagProductID = true;  // THA GINEI true APO TH STIGMH POY TO var_productID YPARXEI STHN VASH DHLADH STON PINAKA product
                            Log.i(TAG, "TO flagProductID egine true");
                            break;
                        }
                    }

                    if (orderID.getText().length() == 0 || productID.getText().length() == 0 || var_customerName.isEmpty()
                            || var_orderDate.isEmpty() || !var_orderDate.matches("\\d{2}-\\d{2}-\\d{2}")
                            || var_quantity == 0 || quantity.getText().toString().isEmpty() || !flagProductID)
                        throw new Exception("Exception thrown");


//                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
//                        Date dateCurrent = new Date();
//                        Timestamp dateGiven = Timestamp.valueOf("2023-05-01 09:01:16");
//                        String currentDate = formatter.format(dateCurrent);
//                        String givenDateByUser = formatter.format(dateGiven);
////                        System.out.println(formatter.format(currentDate));
////                        System.out.println(formatter.format(givenDateByUser));
//                        //compares ts1 with ts2
//                        int checkDate = currentDate.compareTo(givenDateByUser);
//                        if(checkDate>0){
//                            // EAN TO CURRENT DATE EINAI > TOY GIVENDATEBYUSER TOTE SHMAINEI OTI EXEI
//                            // PERASEI AYTH H HMEROMHNIA OPOTE DN MPOREI NA SYMVEI ORDER (PARAGGELIA)
//                            System.out.println("CurrentDate value is less than giveDateByUser");
//                        }
////                        else if(checkDate>0){
////                            System.out.println("TimeSpan1 value is greater");
////                        }
//                        else{
//                            // EAN TO CURRENT DATE EINAI <= TOY GIVENDATEBYUSER TOTE SHMAINEI OTI h
//                            // CURRENTDATE EINAI H HMERA THS PARAGGELIAS 'H OTI H PARAGGELIA THA GINEI KAPOIA EPOMENH MERA (STHN GIVENDATEBYUSER) AYTH
//                            System.out.println("CurrentDate value is greater or equal to giveDateByUser");
//                        }

                    Orders order = new Orders();
                    order.setOrderID(var_orderID);
                    order.setProductID(var_productID);
                    order.setCustomerName(var_customerName);
                    order.setOrderDate(var_orderDate);
                    order.setQuantity(var_quantity);

                    int finalVar_orderID = var_orderID;
                    db.collection("Orders").
                            document("" + var_orderID).
                            set(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // EDW THA MPAINEI ENA NOTIFICATION EPITYXIAS
                                    Toast.makeText(getApplicationContext(), "Order added: " + finalVar_orderID, Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // EDW THA MPAINEI ENA NOTIFICATION APOTYXIAS
                                    Toast.makeText(getApplicationContext(), "Order error: " + finalVar_orderID, Toast.LENGTH_LONG).show();
                                }
                            });
                    orderID.setText("");
                    productID.setText("");
                    customerName.setText("");
                    orderDate.setText("");
                    quantity.setText("");
                    setErrorMessagesToNull();

                } catch (Exception e) {
                    String message = e.getMessage();
                    Log.i(TAG, e.getMessage());
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI APOTYXHS H PROSTHIKI TOY NEOY PROIONTOS
//                    if(message.equals("FOREIGN KEY constraint failed (code 787 SQLITE_CONSTRAINT_FOREIGNKEY)")){
//                        Toast.makeText(getActivity(),"The productID or the supplier or the supplyDate you submitted is not registered.", Toast.LENGTH_LONG).show();
//                    }

                    boolean flagProductID = false;
                    List<Product> aproduct = MainActivity.myAppDatabase.myDao().getProduct();
                    for (Product i : aproduct) {
                        int var_productID_for_check = i.getPid();
                        if (var_productID_for_check == var_productID) {
                            flagProductID = true;  // THA GINEI true APO TH STIGMH POY TO var_productID YPARXEI STHN VASH DHLADH STON PINAKA product
                            Log.i(TAG, "TO flagProductID egine true");
                            break;
                        }
                    }

//                    boolean flagSupplyExists = false;
//                    List<Supplies> asupply = MainActivity.myAppDatabase.myDao().getSupplies();
//                    for (Supplies i : asupply) {
//                        int var_productID_for_check = i.getProductID();
//                        int var_supplierID_for_check = i.getSupplierID();
//                        String var_supplyDate_for_check = i.getSupply_date();
//                        if ((var_productID_for_check == var_productID) && (var_supplierID_for_check == var_supplierID) &&
//                                (var_supplyDate_for_check.equals(var_supplyDate))) {
//                            flagSupplyExists = true;  // THA GINEI true APO TH STIGMH POY TO DOSMENO supply YPARXEI HDH STHN VASH DHLADH STON PINAKA supplies
//                            Log.i(TAG, "TO flagSupplyExists egine true");
//                            break;
//                        }
//                    }

                    if (!flagProductID)
                        productID.setError("The productID you filled is not registered");
//                    if (!flagSupplierID)
//                        supplierID.setError("The supplierID you filled is not registered");
                    if (quantity.getText().toString().isEmpty())
                        quantity.setError("You must fill this field");
                    if (var_orderDate.isEmpty())
                        orderDate.setError("You must fill this field");
                    else if (!var_orderDate.matches("\\d{2}-\\d{2}-\\d{2}"))
                        orderDate.setError("Required format is dd-mm-yy");
//                    if (flagSupplyExists)
//                        displaySupplyError.setText("The supply you want to make has already been recorded.");
                    else
                        displaySupplyError.setText("");
                    if (quantity.getText().length() == 0)
                        quantity.setError("You must fill this field");
                }
            }
        });

//        updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                displaySupplyError.setText("");
//
//                int var_productID = 0;
//                try {
//                    var_productID = Integer.parseInt(productID.getText().toString());
//                } catch (NumberFormatException exception) {
//                    System.out.println("Could not parse" + exception);
//                }
//                int var_supplierID = 0;
//                try {
//                    var_supplierID = Integer.parseInt(supplierID.getText().toString());
//                } catch (NumberFormatException exception) {
//                    System.out.println("Could not parse" + exception);
//                }
//                String var_supplyDate = supplyDate.getText().toString();
//                int var_quantity = 0;
//                try {
//                    var_quantity = Integer.parseInt(quantity.getText().toString());
//                } catch (NumberFormatException exception) {
//                    System.out.println("Could not parse" + exception);
//                }
//                double var_msrp = 0.0;
//                try {
//                    var_msrp = Integer.parseInt(msrp.getText().toString());
//                } catch (NumberFormatException exception) {
//                    System.out.println("Could not parse" + exception);
//                }
//
//                boolean flagSupplyExists = false;
//                int currentQuantity = 0;
//                double currentMsrp = 0.0;
//                List<Supplies> asupply = MainActivity.myAppDatabase.myDao().getSupplies();
//                for(Supplies i: asupply){
//                    int var_productID_for_check = i.getProductID();
//                    int var_supplierID_for_check = i.getSupplierID();
//                    String var_supplyDate_for_check = i.getSupply_date();
//                    if((var_productID_for_check == var_productID) && (var_supplierID_for_check == var_supplierID) &&
//                            (var_supplyDate_for_check.equals(var_supplyDate)) ){
//                        currentQuantity = i.getQuantity();
//                        currentMsrp = i.getMsrp();
//                        flagSupplyExists = true;
//                        break;
//                    }
//                }
//
//                try{
//                    if(productID.getText().length() == 0 || supplierID.getText().length() == 0
//                            || supplyDate.getText().length() == 0 || !var_supplyDate.matches("\\d{2}-\\d{2}-\\d{2}") || !flagSupplyExists){
//                        throw new Exception("Exception Error");
//                    }
//                    Supplies supply = new Supplies();
//                    supply.setProductID(var_productID);
//                    supply.setSupplierID(var_supplierID);
//                    supply.setSupply_date(var_supplyDate);
//                    if(var_quantity == 0) supply.setQuantity(currentQuantity); else supply.setQuantity(var_quantity);
//                    if(var_msrp == 0.0) supply.setMsrp(currentMsrp); else supply.setMsrp(var_msrp);
////                    supply.setQuantity(var_quantity);
////                    supply.setMsrp(var_msrp);
//                    MainActivity.myAppDatabase.myDao().updateSupply(supply);
//                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXHS H PROSTHIKI TOY NEOY PROIONTOS
//                    Toast.makeText(getActivity(), "Record added.", Toast.LENGTH_LONG).show(); // AYTO THA FYGEI
//                    productID.setText("");
//                    supplierID.setText("");
//                    supplyDate.setText("");
//                    quantity.setText("");
//                    msrp.setText("");
//                    setErrorMessagesToNull();
//                }catch (Exception e) {
//                    String message = e.getMessage();
//                    Log.i(TAG,e.getMessage());
//                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
////                        Toast.makeText(getActivity(),"The productID or the supplier you submitted is not registered.", Toast.LENGTH_LONG).show();
////                    }
//                    if(productID.length() == 0)
//                        productID.setError("You must fill this field");
//                    if(supplierID.length() == 0)
//                        supplierID.setError("You must fill this field");
//                    if(var_supplyDate.isEmpty())
//                        supplyDate.setError("You must fill this field");
//                    else if (!var_supplyDate.matches("\\d{2}-\\d{2}-\\d{2}"))
//                        supplyDate.setError("Required format is dd-mm-yy");
//                    if(!flagSupplyExists)
//                        displaySupplyError.setText("The productID or supplierID or supplyDate you filled do not exist\nPlease give an already recorded supply");
//                    else
//                        displaySupplyError.setText("");
//                }
//            }
//        });
//        return view;
    }

    public void setErrorMessagesToNull() {
        if (orderID.getText().length() == 0) orderID.setError(null);
        if (productID.getText().length() == 0) productID.setError(null);
        if (customerName.getText().length() == 0) customerName.setError(null);
        if (orderDate.getText().length() == 0) orderDate.setError(null);
        if (quantity.getText().length() == 0) quantity.setError(null);
    }

}

