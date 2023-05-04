package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDao {
    @Insert
    public  void addProduct(Product product);

    @Delete
    public void deleteProduct(Product product);

    @Update
    public void updateProduct(Product product);

    @Query("select * from product")
    public List<Product> getProduct();


    @Insert
    public  void addSupplier(Supplier supplier);

    @Delete
    public void deleteSupplier(Supplier supplier);

    @Update
    public void updateSupplier(Supplier supplier);

    @Query("select * from supplier")
    public List<Supplier> getSupplier();

//    @Query("SELECT productID FROM product WHERE productID= :" + var_productID )
//    public List<Product> getIDProduct(int var_productID);

    @Insert
    public void addSupply(Supplies supplies);

    @Delete
    public void deleteSupply(Supplies supplies);

    @Update
    public void updateSupply(Supplies supplies);

    @Query("select * from supplies")
    public List<Supplies> getSupplies();


}
