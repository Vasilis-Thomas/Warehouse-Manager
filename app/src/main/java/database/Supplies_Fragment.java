package database;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.eshopapplication.MainActivity;
import com.example.eshopapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.List;

public class Supplies_Fragment extends Fragment {
//    TextInputLayout supplies_msrp_til;
//    TextInputEditText productID, supplierID, supplyDate, quantity, msrp; // msrp = recommended supplier price
TextInputEditText productID, supplierID, quantity, msrp; // msrp = recommended supplier price
    Button insertButton, deleteButton, updateButton;
    TextView displaySupplyError, questionText;
    EditText dateEdt;
    TextView supplyDate;
    private final static String TAG = "remote.database (Supplies_Fragment)";


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
        updateButton = view.findViewById(R.id.update_button);
        deleteButton = view.findViewById(R.id.delete_button);

        // on below line we are initializing our variables.
        dateEdt = view.findViewById(R.id.supplies_date_tiet);

        // on below line we are adding click listener
        // for our pick date button
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
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String yearString = Integer.toString(year); // WE ARE DOING THIS AND THE NEXT LINE
                                year = Integer.parseInt(yearString.substring(1)); // TO REMOVE THE FIRST 2 DIGITS FROM LIKE 2023 ->> 23
//                                dateEdt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                if(dayOfMonth < 10 && monthOfYear < 10)
                                    dateEdt.setText("0"+dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                                else if (dayOfMonth < 10)
                                    dateEdt.setText("0"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                else if (monthOfYear < 10)
                                    dateEdt.setText(""+dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                                else
                                    dateEdt.setText(""+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
//                    if(checkIfDateGivenIsValid())  // ΤΣΕΚΑΡΕΤΑΙ ΕΑΝ Η ΗΜΕΡΟΜΗΝΙΑ ΠΟΥ ΔΟΘΗΚΕ ΕΙΝΑΙ ΤΟΥ ΠΑΡΟΝΤΟΣ Ή ΜΕΛΛΟΝΤΙΚΗ
//                        throw new Exception("Exception thrown: CurrentDate value is less than dateGivenByUser");

//                    if(var_supplyDate.isEmpty() || !var_supplyDate.matches("\\d{2}-\\d{2}-\\d{2}") || (checkIfDateGivenIsValid() == false)
//                        || var_quantity == 0 || quantity.getText().toString().isEmpty())
//                        throw new Exception("Exception thrown");
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
//                    else if (!checkIfDateGivenIsValid()) {
//                        supplyDate.setError("The date filled is not valid\nPlease give present or future date");
//                        Log.d(TAG,"CurrentDate value is less than dateGivenByUser");
////                        throw new ParseException("Parse exception", ex);
//                    }
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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySupplyError.setText("");
                int var_pid = 0;
                try{
                    var_pid = Integer.parseInt(productID.getText().toString());
                }catch (NumberFormatException ex){
                    System.out.println("Could not parse"+ ex);
                }
                int var_sid = 0;
                try{
                    var_sid = Integer.parseInt(supplierID.getText().toString());
                }catch (NumberFormatException ex){
                    System.out.println("Could not parse"+ ex);
                }
                String var_supplyDate = supplyDate.getText().toString();
                boolean flagSupplyExists = false;
                List<Supplies> asupply = MainActivity.myAppDatabase.myDao().getSupplies();
                for(Supplies i: asupply){
                    int var_productID_for_check = i.getProductID();
                    int var_supplierID_for_check = i.getSupplierID();
                    String var_supplyDate_for_check = i.getSupply_date();
                    if(var_productID_for_check == var_pid && var_supplierID_for_check == var_sid && var_supplyDate_for_check.equals(var_supplyDate)){
                        flagSupplyExists = true;
                        break;
                    }
                }

                try{
                    if(productID.getText().length() == 0 || supplierID.getText().length() == 0
                            || supplyDate.getText().length() == 0 || !flagSupplyExists)
                        throw new Exception("Exception Error");
                    Supplies supply = new Supplies();
                    supply.setSupplierID(var_sid);
                    supply.setProductID(var_pid);
                    supply.setSupply_date(var_supplyDate);
                    MainActivity.myAppDatabase.myDao().deleteSupply(supply);

                    productID.setText("");
                    supplierID.setText("");
                    supplyDate.setText("");
                    quantity.setText("");
                    msrp.setText("");
                    setErrorMessagesToNull();
                }catch (Exception ex){
                    String message = ex.getMessage();
                    if(productID.length() == 0)
                        productID.setError("You must fill this field");
                    if(supplierID.length() == 0)
                        supplierID.setError("You must fill this field");
                    if(var_supplyDate.isEmpty())
                        supplyDate.setError("You must fill this field");
                    if(!flagSupplyExists)
                        displaySupplyError.setText("The productID or supplierID or supplyDate you filled do not exist\nPlease give an already recorded supply");
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

//    ΤΣΕΚΑΡΕΤΑΙ ΕΑΝ Η ΗΜΕΡΟΜΗΝΙΑ ΠΟΥ ΔΟΘΗΚΕ ΕΙΝΑΙ ΤΟΥ ΠΑΡΟΝΤΟΣ Ή ΜΕΛΛΟΝΤΙΚΗ
//    public boolean checkIfDateGivenIsValidd(){
//        boolean validDate = false;
//        Timestamp dateGivenByUser = Timestamp.valueOf(supplyDate.getText().toString());
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
////        String dateGivenByUser = supplyDate.getText().toString();
////            Date dateGivenByUserFormatted = formatter.parse(dateGivenByUser);
//            System.out.println("Supplies_Fragment Parse Exception");
////        Timestamp dateGivenByUserTimestamp = new java.sql.Timestamp(dateGivenByUserFormatted.getTime());
//        Date dateCurrent = new Date();
//        String dateCurrentt = formatter.format(dateCurrent);
////        String currentDate = (String) formatter.format(dateCurrent);
////        System.out.println(formatter.format("dateGivenByUser: "+ dateGivenByUser));
//        System.out.println(("dateCurrent: "+ dateCurrent));
////        System.out.println(("currentDate: "+ currentDate));
//        System.out.println(("dateGivenByUser: "+ dateGivenByUser));
//        //compares dateGivenByUser with dateCurrent
//        int checkDates = dateCurrent.compareTo(dateGivenByUser);
//        if(checkDates <= 0){
//            // EAN TO CURRENT DATE EINAI <= TOY GIVENDATEBYUSER TOTE SHMAINEI OTI DEN EXEI
//            // PERASEI AYTH H HMEROMHNIA OPOTE MPOREI NA SYMVEI Supply
//            validDate = true;
//            Log.d(TAG,"Ola kala me thn hmeromhnia supplyDate");
////            System.out.println("CurrentDate value is greater than dateGivenByUser, Ola Kala");
//        }
//        else{
////                        System.out.println("Exception Thrown: CurrentDate value is less than dateGivenByUser");
//            validDate = false;
////            throw new Exception("Exception Thrown: CurrentDate value is less than dateGivenByUser");
//        }
//        return validDate;
//    }


//    public boolean checkIfDateGivenIsValid(){
//        boolean validDate = false;
//
//        String dateGivenByUser = supplyDate.getText().toString();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
//        Date date = new Date();
//        Date parsedDateGivenByUser =  date;
//        try{
//            parsedDateGivenByUser = formatter.parse(dateGivenByUser);
//        }catch (ParseException e){
//            e.printStackTrace();
//        }
//        int checkDates = parsedDateGivenByUser.compareTo(date);
////        Timestamp ts1 = Timestamp.valueOf(supplyDate.getText().toString()+" 00:00:00");
////        Timestamp ts2 = Timestamp.valueOf("2018-09-01 09:01:16");
////        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
////        Date date = new Date();
//        System.out.println(formatter.format(date));
//        System.out.println(formatter.format(parsedDateGivenByUser));
////        System.out.println(formatter.format(ts1));
////        System.out.println(formatter.format(ts2));
////ts2 = formatter.format(ts2);
//        //compares ts1 with ts2
////        int checkDates = ts1.compareTo(date);
//        if(formatter.format(date).equals(formatter.format(parsedDateGivenByUser)) && checkDates>0){
//            System.out.println("Both values are equal");
//            // ISOS NA METAFERETAI TO QUANTITY
//            validDate = true;
//        }
//        else if(checkDates>0){
//            System.out.println("TimeSpan1 value is greater");
//            validDate = true;
//        }
//        else{
//            System.out.println("TimeSpan2 value is greater");
//            validDate = false;
//        }
////        if(formatter.format(date).equals(formatter.format(ts1))){
////            System.out.println("they are equal");
////        }
//
////        if(checkDates <= 0){
////            // EAN TO CURRENT DATE EINAI <= TOY GIVENDATEBYUSER TOTE SHMAINEI OTI DEN EXEI
////            // PERASEI AYTH H HMEROMHNIA OPOTE MPOREI NA SYMVEI Supply
////            validDate = true;
////            Log.d(TAG,"Ola kala me thn hmeromhnia supplyDate");
//////            System.out.println("CurrentDate value is greater than dateGivenByUser, Ola Kala");
////        }
////        else{
//////                        System.out.println("Exception Thrown: CurrentDate value is less than dateGivenByUser");
////            validDate = false;
//////            throw new Exception("Exception Thrown: CurrentDate value is less than dateGivenByUser");
////        }
//        return validDate;
//    }
}