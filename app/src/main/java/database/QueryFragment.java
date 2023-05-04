//package database;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;
//
//import com.example.eshopapplication.R;
//
//import java.util.List;
//
//public class QueryFragment extends Fragment {
//
//    private TextView queryText;
//
//    public QueryFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_query, container, false);
//        queryText = view.findViewById(R.id.query_text);
//        List<User> users = AppDataBaseActivity.myAppDatabase.myDao().getUsers();
//        String result="";
//        for(User i: users){
//            int code = i.getId();
//            String name = i.getName();
//            String surname = i.getSurname();
//            result = result + "\n Id: " + code + "\n Name: " + name + "\n Surname: "+ surname + "\n\n";
//        }
//
//        queryText.setText(result);
//        return view;
//    }
//}