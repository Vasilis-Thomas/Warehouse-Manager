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

public class Supplies_Fragment extends Fragment {

    TextInputEditText productID, supplierID, supplyDate, quantity, msrp; // msrp = recommended supplier price

    Button insertButton, deleteButton, updateButton, queryButton;

    private final static String TAG = "database";


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

                if(productID.getText().length()==0) { productID.setError("You must fill this field");}
                if(supplierID.getText().length()==0){ supplierID.setError("You must fill this field");}
                if(supplyDate.getText().length()==0){ supplyDate.setError("You must fill this field");}
                if(quantity.getText().length()==0)  { quantity.setError("You must fill this field");}
                if(msrp.getText().length()==0)      { msrp.setError("You must fill this field");}



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

                if(productID.getText().length()==0 || supplierID.getText().length()==0 ||
                        supplyDate.getText().length()==0 || quantity.getText().length()==0 ||
                        msrp.getText().length()==0){
                    return;
                }

                try {
                    Supplies supply = new Supplies();
                    supply.setProductID(var_productID);
                    supply.setSupplierID(var_supplierID);
                    supply.setSupply_date(var_supplyDate);
                    supply.setQuantity(var_quantity);
                    supply.setMsrp(var_msrp);
                    MainActivity.myAppDatabase.myDao().addSupply(supply);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXEIS H PROSTHIKI TOY NEOY PROIONTOS
                    Toast.makeText(getActivity(), "Record added.", Toast.LENGTH_LONG).show(); // AYTO THA FYGEI

                    productID.setText("");
                    supplierID.setText("");
                    supplyDate.setText("");
                    quantity.setText("");
                    msrp.setText("");

                } catch (Exception e) {
                    String message = e.getMessage();
                    Log.i(TAG,e.getMessage());
//                    if(message.equals("FOREIGN KEY constraint failed (code 787 SQLITE_CONSTRAINT_FOREIGNKEY)")){
//                        Toast.makeText(getActivity(),"The productID or the supplier you submitted is not registered.", Toast.LENGTH_LONG).show();
//                    }
//                    else {

                        boolean flagProductID = false, flagSupplierID = false;

                        List<Product> aproduct= MainActivity.myAppDatabase.myDao().getProduct();
                        for(Product i: aproduct){
                            int var_productID_for_check = i.getPid();
                            if(var_productID_for_check == var_productID){
                                flagProductID = true;  // THA GINEI true APO TH STIGMH POY TO var_productID YPARXEI STHN VASH DHLADH STON PINAKA product
                                Log.i(TAG,"TO flagProductID egine true");
                                break;
                            }
                        }

                        List<Supplier> asupplier= MainActivity.myAppDatabase.myDao().getSupplier();
                        for(Supplier i: asupplier){
                            int var_supplierID_for_check = i.getSid();
                            if(var_supplierID_for_check == var_supplierID){
                                flagSupplierID = true;  // THA GINEI true APO TH STIGMH POY TO var_supplierID YPARXEI STHN VASH DHLADH STON PINAKA supplier
                                Log.i(TAG,"TO flagSupplierID egine true");
                                break;
                            }
                        }

                        //O PARAKATW KWDIKAS POY EINAI PLEON SXOLIO KATA PASA PITHANOTHTA THA KATARGITHEI AFOY TO ANTIKATESTHSE O KWDIKAS STA LINES 157-162

/*                        if(!(flagProductID && flagSupplierID)){  // ELEGXETAI EAN KANENA APO TA 2 FIELDS POY EXOYN SYMPLIRWTHEI DN ANTISTOIXEI SE KAPOIO EGGEGRAMENO productID kai supplierID
//                            Toast.makeText(getActivity(),"The productID and the supplier you submitted are not registered.", Toast.LENGTH_LONG).show();
                            supplierID.setError("The supplierID you filled is not registered");
                            productID.setError("The productID you filled is not registered");
                        }
                        else if (flagProductID && flagSupplierID==false) {  // EAN TO productID YPARXEI ALLA TO supplierID DEN YPARXEI STON ANTISTOIXO PINAKA
//                            Toast.makeText(getActivity(),"The supplierID you submitted is not registered.", Toast.LENGTH_LONG).show();
                            supplierID.setError("The supplierID you filled is not registered");
                        }
                        else if(flagProductID==false && flagSupplierID){  // EAN TO productID DEN YPARXEI ALLA TO supplierID  YPARXEI STON ANTISTOIXO PINAKA
//                            Toast.makeText(getActivity(),"The productID you submitted is not registered.", Toast.LENGTH_LONG).show();
                            productID.setError("The productID you filled is not registered");
                        }
 */
                        if(!flagProductID){
                            productID.setError("The productID you filled is not registered");
                        }
                        if(!flagSupplierID){
                            supplierID.setError("The supplierID you filled is not registered");
                        }
                    }
                }
        });
        return view;
    }
}