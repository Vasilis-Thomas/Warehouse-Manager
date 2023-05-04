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

public class Supplier_Fragment extends Fragment {

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
                    Supplier supplier = new Supplier();
                    supplier.setSid(var_id);
                    supplier.setName(var_name);
                    supplier.setEmail(var_email);
                    supplier.setPhone(var_phone);
                    supplier.setAddress(var_address);
                    MainActivity.myAppDatabase.myDao().addSupplier(supplier);
                    //EDW THA VALOYME NA ERHETAI NOTIFICATION OTI EINAI EPITYXEIS H PROSTHIKI TOY NEOY PROIONTOS
                    Toast.makeText(getActivity(), "Record added.", Toast.LENGTH_LONG).show(); // AYTO THA FYGEI
                } catch (Exception e) {
                    String message = e.getMessage();
                    //ANTISTOIXA MPROYME NA VALOYME TO NOTIFICATION GIA UNSUCCESFUL YLOPOIHSH
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show(); // AYTO THA FYGEI
                }

                supplierID.setText("");
                supplierName.setText("");
                email.setText("");
                phone.setText("");
                address.setText("");
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