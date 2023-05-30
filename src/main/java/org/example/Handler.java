package org.example;

import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
public class Handler {
    public static class handler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            OutputStream outputStream = exchange.getResponseBody();
            String message = "";

            if ("GET".equals(exchange.getRequestMethod())) {
                //DI SINI GET!
            } else if ("POST".equals(exchange.getRequestMethod())){
                //DI SINI POST!
            } else if ("PUT".equals(exchange.getRequestMethod())){
                //DI SINI PUT!
            } else if ("DELETE".equals(exchange.getRequestMethod())){
                //DI SINI DELETE!
            } else {
                message = "Method not found.";
                exchange.sendResponseHeaders(404, message.length());
                outputStream.write(message.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }
    }
}

class Users{
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String type;

    public Integer id(){
        return id;
    }
    public String firstName(){
        return firstName;
    }
    public String lastName(){
        return lastName;
    }
    public String email(){
        return email;
    }
    public String phoneNumber(){
        return phoneNumber;
    }
    public String type(){
        return type;
    }

    public void setUser(int id, String firstName, String lastName, String email, String phoneNumber, String type){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public int parsingJson(String jSon){
        try{
            JSONObject jsonObject = new JSONObject(jSon);
            id = jsonObject.getInt("userId");
            firstName = jsonObject.getString("first_name");
            lastName = jsonObject.getString("last_name");
            email = jsonObject.getString("email");
            phoneNumber = jsonObject.getString("phone_number");
            type = jsonObject.getString("type");
        }catch (Exception e){
            return 1;
        }
        return 0;
    }
    public void insert(){
        SQLiteQuery inputDatabase = new SQLiteQuery();
        inputDatabase.inputUsers(id, firstName, lastName, email, phoneNumber, type);
    }
//    public void update(String newId){
//        SQLiteQuery updateDatabase = new SQLiteQuery();
//        updateDatabase.updateUsers(id, firstName, lastName, email, phoneNumber, type);
//    }
}
class Products{
    private int id;
    private int sellerId;
    private String title;
    private String description;
    private int price;
    private int stock;

    public Integer getId(){
        return id;
    }
    public Integer getSellerId(){
        return sellerId;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public Integer getPrice(){
        return price;
    }
    public Integer getStock(){
        return stock;
    }

    public void setProducts(int id, int sellerId, String title, String description, int price, int stock){
        this.id = id;
        this.sellerId = sellerId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public int parsingJson(String jSon){
        try{
            JSONObject jsonObject = new JSONObject(jSon);
            id = jsonObject.getInt("userId");
            sellerId = jsonObject.getInt("sellerId");
            title = jsonObject.getString("title");
            description = jsonObject.getString("description");
            price = jsonObject.getInt("price");
            stock = jsonObject.getInt("stock");
        }catch (Exception e){
            return 1;
        }
        return 0;
    }
    public void insert(){
        SQLiteQuery inputDatabase = new SQLiteQuery();
        inputDatabase.inputProducts(id, sellerId, title, description, price, stock);
    }
}

class Addresses{
    private int userId;
    private String type;
    private String lineOne;
    private String lineTwo;
    private String city;
    private String province;
    private String postcode;

    public Integer getUserId(){
        return userId;
    }
    public String getType(){
        return type;
    }
    public String getLineOne(){
        return lineOne;
    }
    public String getLineTwo(){
        return lineTwo;
    }
    public String getCity(){
        return city;
    }
    public String getProvince(){
        return province;
    }
    public String getPostcode(){
        return postcode;
    }

    public void setAddresses(int userId, String type, String lineOne, String lineTwo, String city, String province, String postcode){
        this.userId = userId;
        this.type = type;
        this.lineOne = lineOne;
        this.lineTwo = lineTwo;
        this.city = city;
        this.province = province;
        this.postcode = postcode;
    }

    public int parsingJson(String jSon){
        try{
            JSONObject jsonObject = new JSONObject(jSon);
            userId = jsonObject.getInt("userId");
            type = jsonObject.getString("type");
            lineOne = jsonObject.getString("line1");
            lineTwo = jsonObject.getString("line2");
            city = jsonObject.getString("city");
            province = jsonObject.getString("province");
            postcode = jsonObject.getString("postcode");
        }catch (Exception e){
            return 1;
        }
        return 0;
    }
    public void insert(){
        SQLiteQuery inputDatabase = new SQLiteQuery();
        inputDatabase.inputAddresses(userId, type, lineOne, lineTwo, city, province, postcode);
    }
}
class Orders{
    private int id;
    private int buyerId;
    private String note;
    private int total;
    private int discount;
    private String is_paid;

    public Integer getId(){
        return id;
    }
    public Integer getBuyerId(){
        return buyerId;
    }
    public String getNote(){
        return note;
    }
    public Integer getTotal(){
        return total;
    }
    public Integer getDiscount(){
        return discount;
    }
    public String getIs_paid(){
        return is_paid;
    }

    public void setOrders(int id, int buyerId, String note, int total, int discount, String is_paid){
        this.id = id;
        this.buyerId = buyerId;
        this.note = note;
        this.total = total;
        this.discount = discount;
        this.is_paid = is_paid;
    }

    public int parsingJson(String jSon){
        try{
            JSONObject jsonObject = new JSONObject(jSon);
            id = jsonObject.getInt("orderId");
            buyerId = jsonObject.getInt("buyerId");
            note = jsonObject.getString("note");
            total = jsonObject.getInt("total");
            discount = jsonObject.getInt("discount");
            is_paid = jsonObject.getString("is_paid");
        }catch (Exception e){
            return 1;
        }
        return 0;
    }
    public void insert(){
        SQLiteQuery inputDatabase = new SQLiteQuery();
        inputDatabase.inputOrders(id, buyerId, note, total, discount, is_paid);
    }
}
class OrderDetails{
    private int orderId;
    private int productId;
    private int quantity;
    private int price;

    public Integer getOrderId(){
        return orderId;
    }
    public Integer getProductId(){
        return productId;
    }
    public Integer getQuantity(){
        return quantity;
    }
    public Integer getPrice(){
        return price;
    }

    public int parsingJson(String jSon){
        try{
            JSONObject jsonObject = new JSONObject(jSon);
            orderId = jsonObject.getInt("orderId");
            productId = jsonObject.getInt("productId");
            quantity = jsonObject.getInt("quantity");
            price = jsonObject.getInt("price");
        }catch (Exception e){
            return 1;
        }
        return 0;
    }
    public void insert(){
        SQLiteQuery inputDatabase = new SQLiteQuery();
        inputDatabase.inputOrderDetails(orderId, productId, quantity, price);
    }
}
class Reviews{
    private int orderId;
    private int star;
    private String description;

    public Integer getOrderId(){
        return orderId;
    }
    public Integer getStar(){
        return star;
    }
    public String getDescription(){
        return description;
    }

    public int parsingJson(String jSon){
        try{
            JSONObject jsonObject = new JSONObject(jSon);
            orderId = jsonObject.getInt("orderId");
            star = jsonObject.getInt("star");
            description = jsonObject.getString("description");
        }catch (Exception e){
            return 1;
        }
        return 0;
    }
    public void insert(){
        SQLiteQuery inputDatabase = new SQLiteQuery();
        inputDatabase.inputReviews(orderId, star, description);
    }
}
