package database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//TO CLASS AYTO EINAI DEIGMA ... THA DIAGRAFEI
@Entity(tableName = "users")
public class User {

    @PrimaryKey
    private int id;

    @ColumnInfo (name = "users_name")
    private String name;

    @ColumnInfo (name = "users_surname")
    private String surname;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }
}
