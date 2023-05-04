package database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

    @Entity(tableName = "product")
    public class Product {
        @PrimaryKey @ColumnInfo(name = "productID")
        private int pid;

        @ColumnInfo(name = "productName")
        private String name;

        @ColumnInfo(name = "category")
        private String category;

        @ColumnInfo(name = "price")
        private double price;

        @ColumnInfo(name = "stock")
        private int stock; // αποθεμα προϊοντος


        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }