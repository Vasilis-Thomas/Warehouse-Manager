package database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, Supplier.class, Supplies.class}, version = 1)
public abstract class MyAppDatabase extends RoomDatabase {
    public abstract MyDao myDao();
}
