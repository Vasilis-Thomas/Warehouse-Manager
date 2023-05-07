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

public class Supplier_Fragment extends Fragment {
    private final static String TAG = "database (Supplier_Fragment)";
    TextInputEditText supplierID, supplierName, email, phone, address;
    Button insertButton, deleteButton, updateButton, queryButton;

    public Supplier_Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_supplier, container, false);

        supplierID = (TextInputEditText) view.findViewById(R.id.supplier_id_tiet);
        supplierName = view.findViewById(R.id.supplier_name_tiet);
        email = view.findViewById(R.id.supplier_email_tiet);
        phone = view.findViewById(R.id.supplier_phone_tiet);
        address = view.findViewById(R.id.supplier_address_tiet);

        insertButton = view.findViewById(R.id.insert_button);
        queryButton = view.findViewById(R.id.query_button);
        updateButton = view.findViewById(R.id.update_button);
        deleteButton = view.findViewById(R.id.delete_button);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(supplierID.getText().length()==0)  { supplierID.setError("You must fill this field");   }
                if(supplierName.getText().length()==0){ supplierName.setError("You must fill this field"); }
                if(email.getText().length()==0)       { email.setError("You must fill this field");        }
                if(phone.getText().length()==0)       { phone.setError("You must fill this field");        }
                if(address.getText().length()==0)     { address.setError("You must fill this field");      }

                int var_id = 0;
                try {
//                    var_id = Integer.parseInt(id.getEditText().getText().toString()); // EAN TO id HTAN TYPOU TextInputLayout
                    var_id = Integer.parseInt(supplierID.getText().toString());  // EDW TO supplierID EINAI TYPOU TextInputEditText
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                String var_name = supplierName.getText().toString();
                String var_email = email.getText().toString();
                String var_phone = phone.getText().toString();
                String var_address = address.getText().toString();

                try {
                    // EAN THELOYME OTAN OPOIODIPOTE APO TA PEDIA DN EXEI TIMH TOTE NA MHN GINETAI INSERT STO TABLE TO SYGKEKRIMENO supplier
                    if(supplierID.getText().length()==0 || email.getText().length()==0 ||
                       phone.getText().length()==0 || address.getText().length()==0 )
                           throw new Exception("Excpetion thrown");

                    Supplier supplier = new Supplier();
                    supplier.setSid(var_id);
                    supplier.setName(var_name);
                    supplier.setEmail(var_email);
                    supplier.setPhone(var_phone);
                    supplier.setAddress(var_address);
                    MainActivity.myAppDatabase.myDao().addSupplier(supplier);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXEIS H PROSTHIKI TOY NEOY PROIONTOS
                    Toast.makeText(getActivity(), "Record added.", Toast.LENGTH_LONG).show(); // AYTO THA FYGEI
                    supplierID.setText("");
                    supplierName.setText("");
                    email.setText("");
                    phone.setText("");
                    address.setText("");
                    setErrorMessagesToNull();
                } catch (Exception e) {
                    String message = e.getMessage();
                    //ANTISTOIXA MPROYME NA VALOYME TO NOTIFICATION GIA UNSUCCESFUL YLOPOIHSH
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show(); // AYTO THA FYGEI

                    boolean flagSupplierID = false;

                    List<Supplier> asupplier= MainActivity.myAppDatabase.myDao().getSupplier();
                    for(Supplier i: asupplier){
                        int var_supplierID_for_check = i.getSid();
                        if(var_supplierID_for_check == var_id){
                            flagSupplierID = true;  // THA GINEI true APO TH STIGMH POY TO var_supplierID YPARXEI STHN VASH DHLADH STON PINAKA product
                            Log.i(TAG,"TO flagSupplierID egine true");
                            break;
                        }
                    }
                    if(supplierID.getText().length()==0){
                        supplierID.setError("You must fill this field");
                    }
                    else if(flagSupplierID){  // EAN YPARXEI HDH STH VASH TO SYGKEKRIMENO productID
                        supplierID.setError("The supplierID you filled is already recorded\nPlease give another supplierID");
                    }
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int var_id = 0;
                try {
                    var_id = Integer.parseInt(supplierID.getText().toString());  // EDW TO supplierID EINAI TYPOU TextInputEditText
                } catch (NumberFormatException exception) {
                    System.out.println("Could not parse" + exception);
                }
                String var_name = supplierName.getText().toString();
                String var_email = email.getText().toString();
                String var_phone = phone.getText().toString();
                String var_address = address.getText().toString();

                String currentName="Unknown", currentEmail="Unknown", currentPhone="Unknown", currentAddress="Unknown";
                boolean flagSupplierID = false;

                List<Supplier> asupplier= MainActivity.myAppDatabase.myDao().getSupplier();
                for(Supplier i: asupplier){
                    int var_supplierID_for_check = i.getSid();
                    if(var_supplierID_for_check == var_id) {
                        currentName = i.getName();
                        currentEmail = i.getEmail();
                        currentPhone = i.getPhone();
                        currentAddress = i.getAddress();
                        flagSupplierID = true;  // THA GINEI true APO TH STIGMH POY TO var_supplierID YPARXEI STHN VASH DHLADH STON PINAKA product
                        Log.i(TAG, "TO flagSupplierID egine true");
                        break;
                    }
                }

                try {
                    if(supplierID.getText().toString().isEmpty() || !flagSupplierID)
                        throw new Exception("Exception thrown");

                    Supplier supplier = new Supplier();
                    supplier.setSid(var_id);
                    if(var_name.isEmpty()) supplier.setName(currentName); else supplier.setName(var_name);
                    if(var_email.isEmpty()) supplier.setEmail(currentEmail); else supplier.setEmail(var_email);
                    if(var_phone.isEmpty()) supplier.setPhone(currentPhone); else supplier.setPhone(var_phone);
                    if(var_address.isEmpty()) supplier.setAddress(currentAddress); else supplier.setAddress(var_address);

                    MainActivity.myAppDatabase.myDao().updateSupplier(supplier);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXHS H PROSTHIKI TOY NEOY PROIONTOS
                    Toast.makeText(getActivity(), "Record added.", Toast.LENGTH_LONG).show(); // AYTO THA FYGEI
                    supplierID.setText("");
                    supplierName.setText("");
                    email.setText("");
                    phone.setText("");
                    address.setText("");
                    setErrorMessagesToNull();
                } catch (Exception e) {
                    String message = e.getMessage();
                    //ANTISTOIXA MPROYME NA VALOYME TO NOTIFICATION GIA UNSUCCESFUL YLOPOIHSH
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show(); // AYTO THA FYGEI

                    if (supplierID.getText().length() == 0) {
                        supplierID.setError("You must fill this field");
                    } else if (!flagSupplierID) {  // EAN DEN YPARXEI STH VASH TO SYGKEKRIMENO productID
                        supplierID.setError("The supplierID you filled does not exist\nPlease give an already recorded supplierID");
                    }
                }
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

    public void setErrorMessagesToNull(){
        if(supplierID.getText().length()==0)  { supplierID.setError(null);}
        if(supplierName.getText().length()==0){ supplierName.setError(null);}
        if(email.getText().length()==0)       { email.setError(null);}
        if(phone.getText().length()==0)       { phone.setError(null);}
        if(address.getText().length()==0)     { address.setError(null);}
    }

}