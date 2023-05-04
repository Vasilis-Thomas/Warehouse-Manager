package database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "supplies",
        primaryKeys = {"productID", "supplierID", "supplyDate"},
        foreignKeys = {
        @ForeignKey(entity = Product.class,
                parentColumns = "productID",
                childColumns = "productID",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Supplier.class,
                parentColumns = "supplierID",
                childColumns = "supplierID",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)
        })
public class Supplies {

    @ColumnInfo(name = "productID") @NonNull
    private int productID;

    @ColumnInfo(name = "supplierID") @NonNull
    private int supplierID;

    @ColumnInfo(name = "supplyDate") @NonNull
    private String supply_date;

    @ColumnInfo(name = "NumberReceived")
    private int quantity;

    @ColumnInfo(name = "MSRP")
    private double msrp; // supplier recommended price

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    @NonNull
    public String getSupply_date() {
        return supply_date;
    }

    public void setSupply_date(@NonNull String supply_date) {
        this.supply_date = supply_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getMsrp() {
        return msrp;
    }

    public void setMsrp(double msrp) {
        this.msrp = msrp;
    }
}
