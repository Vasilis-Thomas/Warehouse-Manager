package database;

import android.os.Bundle;
import android.util.Log;
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

import java.util.List;

public class Supplies_Fragment extends Fragment {
//    TextInputLayout supplies_msrp_til;
    TextInputEditText productID, supplierID, supplyDate, quantity, msrp; // msrp = recommended supplier price
    Button insertButton, deleteButton, updateButton, queryButton;
    TextView displaySupplyError, questionText;
    private final static String TAG = "database (Supplies_Fragment)";


    public Supplies_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_supplies, container, false);

        displaySupplyError = view.findViewById(R.id.supply_error_textview);
        questionText = view.findViewById(R.id.question_text);

        productID = view.findViewById(R.id.supplies_productid_tiet);
        supplierID = view.findViewById(R.id.supplies_supplierid_tiet);
        supplyDate = view.findViewById(R.id.supplies_date_tiet);
        quantity = view.findViewById(R.id.supplies_quantity_tiet);
        msrp = view.findViewById(R.id.supplies_msrp_tiet);

        insertButton = view.findViewById(R.id.insert_button);
        queryButton = view.findViewById(R.id.query_button);
        updateButton = view.findViewById(R.id.update_button);
        deleteButton = view.findViewById(R.id.delete_button);


        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySupplyError.setText("");
                if(productID.getText().length()==0) { productID.setError("You must fill this field");  }
                if(supplierID.getText().length()==0){ supplierID.setError("You must fill this field"); }
                if(supplyDate.getText().length()==0){ supplyDate.setError("You must fill this field"); }
                if(quantity.getText().length()==0)  { quantity.setError("You must fill this field");   }
                // ΤΟ QUANTITY DEN APOTELEI KYRIO KLEIDI OMWS THA PREPEI NA MHN EINAI OYTE 0 OYTE NULL
//                if(msrp.getText().length()==0)      { msrp.setError("It is optional to fill this field");}


                int var_productID = 0;
                try {
                    var_productID = Integer.parseInt(productID.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                int var_supplierID = 0;
                try {
                    var_supplierID = Integer.parseInt(supplierID.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                String var_supplyDate = supplyDate.getText().toString();
                int var_quantity = 0;
                try {
                    var_quantity = Integer.parseInt(quantity.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                double var_msrp = 0.0;
                try {
                    var_msrp = Integer.parseInt(msrp.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }

//                if(productID.getText().length()==0 || supplierID.getText().length()==0 ||
//                        supplyDate.getText().length()==0 || quantity.getText().length()==0 ||
//                        msrp.getText().length()==0){
//                    return;
//                }

                try {
                    if(var_supplyDate.isEmpty() || !var_supplyDate.matches("\\d{2}-\\d{2}-\\d{2}")
                        || var_quantity == 0 || quantity.getText().toString().isEmpty())
                        throw new Exception("Exception thrown");

                    Supplies supply = new Supplies();
                    supply.setProductID(var_productID);
                    supply.setSupplierID(var_supplierID);
                    supply.setSupply_date(var_supplyDate);
                    supply.setQuantity(var_quantity);
                    supply.setMsrp(var_msrp);
                    MainActivity.myAppDatabase.myDao().addSupply(supply);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXHS H PROSTHIKI TOY NEOY PROIONTOS
                    Toast.makeText(getActivity(), "Record added.", Toast.LENGTH_LONG).show(); // AYTO THA FYGEI

                    productID.setText("");
                    supplierID.setText("");
                    supplyDate.setText("");
                    quantity.setText("");
                    msrp.setText("");
                    setErrorMessagesToNull();
                } catch (Exception e) {
                    String message = e.getMessage();
                    Log.i(TAG,e.getMessage());
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI APOTYXHS H PROSTHIKI TOY NEOY PROIONTOS
//                    if(message.equals("FOREIGN KEY constraint failed (code 787 SQLITE_CONSTRAINT_FOREIGNKEY)")){
//                        Toast.makeText(getActivity(),"The productID or the supplier or the supplyDate you submitted is not registered.", Toast.LENGTH_LONG).show();
//                    }

                    boolean flagProductID = false, flagSupplierID = false;

                    List<Product> aproduct= MainActivity.myAppDatabase.myDao().getProduct();
                    for(Product i: aproduct){
                        int var_productID_for_check = i.getPid();
                        if(var_productID_for_check == var_productID){
                            flagProductID = true;  // THA GINEI true APO TH STIGMH POY TO var_productID YPARXEI STHN VASH DHLADH STON PINAKA supplies
                            Log.i(TAG,"TO flagProductID egine true");
                            break;
                        }
                    }

                    List<Supplier> asupplier= MainActivity.myAppDatabase.myDao().getSupplier();
                    for(Supplier i: asupplier){
                        int var_supplierID_for_check = i.getSid();
                        if(var_supplierID_for_check == var_supplierID){
                            flagSupplierID = true;  // THA GINEI true APO TH STIGMH POY TO var_supplierID YPARXEI STHN VASH DHLADH STON PINAKA supplies
                            Log.i(TAG,"TO flagSupplierID egine true");
                            break;
                        }
                    }

                    boolean flagSupplyExists = false;
                    List<Supplies> asupply = MainActivity.myAppDatabase.myDao().getSupplies();
                    for(Supplies i: asupply){
                        int var_productID_for_check = i.getProductID();
                        int var_supplierID_for_check = i.getSupplierID();
                        String var_supplyDate_for_check = i.getSupply_date();
                        if((var_productID_for_check == var_productID) && (var_supplierID_for_check == var_supplierID) &&
                                (var_supplyDate_for_check.equals(var_supplyDate)) ){
                            flagSupplyExists = true;  // THA GINEI true APO TH STIGMH POY TO DOSMENO supply YPARXEI HDH STHN VASH DHLADH STON PINAKA supplies
                            Log.i(TAG,"TO flagSupplyExists egine true");
                            break;
                        }
                    }

                    if(!flagProductID)
                        productID.setError("The productID you filled is not registered");
                    if(!flagSupplierID)
                        supplierID.setError("The supplierID you filled is not registered");
                    if(quantity.getText().toString().isEmpty())
                        quantity.setError("You must fill this field");
                    if(var_supplyDate.isEmpty())
                        supplyDate.setError("You must fill this field");
                    else if (!var_supplyDate.matches("\\d{2}-\\d{2}-\\d{2}"))
                        supplyDate.setError("Required format is dd-mm-yy");
                    if(flagSupplyExists)
                        displaySupplyError.setText("The supply you want to make has already been recorded.");
                    else
                        displaySupplyError.setText("");
                    if(quantity.getText().length()==0)
                        quantity.setError("You must fill this field");
                }
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySupplyError.setText("");

                int var_productID = 0;
                try {
                    var_productID = Integer.parseInt(productID.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                int var_supplierID = 0;
                try {
                    var_supplierID = Integer.parseInt(supplierID.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                String var_supplyDate = supplyDate.getText().toString();
                int var_quantity = 0;
                try {
                    var_quantity = Integer.parseInt(quantity.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                double var_msrp = 0.0;
                try {
                    var_msrp = Integer.parseInt(msrp.getText().toString());
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }

                boolean flagSupplyExists = false;
                int currentQuantity = 0;
                double currentMsrp = 0.0;
                List<Supplies> asupply = MainActivity.myAppDatabase.myDao().getSupplies();
                for(Supplies i: asupply){
                    int var_productID_for_check = i.getProductID();
                    int var_supplierID_for_check = i.getSupplierID();
                    String var_supplyDate_for_check = i.getSupply_date();
                    if((var_productID_for_check == var_productID) && (var_supplierID_for_check == var_supplierID) &&
                            (var_supplyDate_for_check.equals(var_supplyDate)) ){
                        currentQuantity = i.getQuantity();
                        currentMsrp = i.getMsrp();
                        flagSupplyExists = true;
                        break;
                    }
                }

                try{
                    if(productID.getText().length() == 0 || supplierID.getText().length() == 0
                            || supplyDate.getText().length() == 0 || !var_supplyDate.matches("\\d{2}-\\d{2}-\\d{2}") || !flagSupplyExists){
                        throw new Exception("Exception Error");
                    }
                    Supplies supply = new Supplies();
                    supply.setProductID(var_productID);
                    supply.setSupplierID(var_supplierID);
                    supply.setSupply_date(var_supplyDate);
                    if(var_quantity == 0) supply.setQuantity(currentQuantity); else supply.setQuantity(var_quantity);
                    if(var_msrp == 0.0) supply.setMsrp(currentMsrp); else supply.setMsrp(var_msrp);
//                    supply.setQuantity(var_quantity);
//                    supply.setMsrp(var_msrp);
                    MainActivity.myAppDatabase.myDao().updateSupply(supply);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXHS H PROSTHIKI TOY NEOY PROIONTOS
                    Toast.makeText(getActivity(), "Record added.", Toast.LENGTH_LONG).show(); // AYTO THA FYGEI
                    productID.setText("");
                    supplierID.setText("");
                    supplyDate.setText("");
                    quantity.setText("");
                    msrp.setText("");
                    setErrorMessagesToNull();
                }catch (Exception e) {
                    String message = e.getMessage();
                    Log.i(TAG,e.getMessage());
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(),"The productID or the supplier you submitted is not registered.", Toast.LENGTH_LONG).show();
//                    }
                    if(productID.length() == 0)
                        productID.setError("You must fill this field");
                    if(supplierID.length() == 0)
                        supplierID.setError("You must fill this field");
                    if(var_supplyDate.isEmpty())
                        supplyDate.setError("You must fill this field");
                    else if (!var_supplyDate.matches("\\d{2}-\\d{2}-\\d{2}"))
                        supplyDate.setError("Required format is dd-mm-yy");
                    if(!flagSupplyExists)
                        displaySupplyError.setText("The productID or supplierID or supplyDate you filled do not exist\nPlease give an already recorded supply");
                    else
                        displaySupplyError.setText("");
                }
            }
        });
        return view;
    }

    public void setErrorMessagesToNull() {
        if (productID.getText().length() == 0)    productID.setError(null);
        if (supplierID.getText().length() == 0)   supplierID.setError(null);
        if (supplyDate.getText().length() == 0)   supplyDate.setError(null);
        if (quantity.getText().length() == 0)     quantity.setError(null);
        if (msrp.getText().length() == 0)         msrp.setError(null);
    }

}