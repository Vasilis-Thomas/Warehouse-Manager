package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eshopapplication.ProductInfo;

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

    @Query("select product.productName, product.category, product.price, product.stock, supplier.supplierName as supplier \n" +
            "from product inner join supplies on product.productID=supplies.productID " +
                        "inner join supplier on supplies.supplierID=supplier.supplierID")
    public List<ProductInfo> getAllProductsCategoriesAllSuppliers();

    @Query("select product.productName, product.category, product.price, product.stock, supplier.supplierName as supplier \n" +
            "from product inner join supplies on product.productID=supplies.productID " +
            "inner join supplier on supplies.supplierID=supplier.supplierID "+
            "where supplier.supplierName= :inputSupplier")
    public List<ProductInfo> getAllProductsCategories_Supplier(String inputSupplier);

    @Query("select product.productName, product.category, product.price, product.stock, supplier.supplierName as supplier \n" +
            "from product inner join supplies on product.productID=supplies.productID " +
            "inner join supplier on supplies.supplierID=supplier.supplierID "+
            "where product.category = :inputCategory")
    public List<ProductInfo> get_ProductsCategoriesAllSupplier(String inputCategory);

    @Query("select product.productName, product.category, product.price, product.stock, supplier.supplierName as supplier \n" +
            "from product inner join supplies on product.productID=supplies.productID " +
            "inner join supplier on supplies.supplierID=supplier.supplierID "+
            "where product.category = :inputCategory and supplier.supplierName= :inputSupplier")
    public List<ProductInfo> get_ProductsCategories_Supplier(String inputSupplier, String inputCategory);

    @Query("select distinct category from product")
    public List<String> getCategoryNames();


    @Insert
    public  void addSupplier(Supplier supplier);

    @Delete
    public void deleteSupplier(Supplier supplier);

    @Update
    public void updateSupplier(Supplier supplier);

    @Query("select * from supplier")
    public List<Supplier> getSupplier();

    @Query("select supplierName from supplier")
    public List<String> getSupplierName();

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
