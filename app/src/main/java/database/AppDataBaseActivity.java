package database;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.example.eshopapplication.R;

public class AppDataBaseActivity extends AppCompatActivity {
    // Η αντιστοιχη MainActivity.class του παραδειγματος του Ευκλιδη
    public static FragmentManager fragmentManager;
    public static MyAppDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_database);
        fragmentManager = getSupportFragmentManager();

        myAppDatabase = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"userBD").allowMainThreadQueries().build();
        if(findViewById(R.id.fragment_container_database)!=null){
            if(savedInstanceState!=null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container_database, new RoomUI_Fragment()).commit();
        }
    }
}