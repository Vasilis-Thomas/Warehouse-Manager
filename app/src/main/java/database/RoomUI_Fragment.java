package database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.eshopapplication.R;

public class RoomUI_Fragment extends Fragment implements View.OnClickListener {

    Button addButton, deleteButton, updateButton, queryButton;

    public RoomUI_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        addButton = view.findViewById(R.id.adduser_button);

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}