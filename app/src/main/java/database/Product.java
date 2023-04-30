package database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

    @Entity(tableName = "product")
    public class Product {

        @PrimaryKey @ColumnInfo(name = "ProductID")
        private int id;

        @ColumnInfo(name = "ProductName")
        private String name;

        @ColumnInfo(name = "Category")
        private String category;

        @ColumnInfo(name = "Price")
        private double price;

        @ColumnInfo(name = "Stock")
        private int stock; // αποθεμα προϊοντος



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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