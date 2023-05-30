package org.example;

import java.io.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.cdimascio.dotenv.Dotenv;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.SocketHandler;

public class Handler {
    public static class handler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException, FileNotFoundException {
            String allWeb = String.valueOf(exchange.getRequestURI());
            String web = exchange.getRequestURI().getPath();
            String[] allPath = Parsing.path(web);
            String path = allPath[1];
            String message = "";
            System.out.println(exchange.getRequestMethod());
            OutputStream mainOut = exchange.getResponseBody();
            Users user = new Users();
            Products product = new Products();
            Addresses address = new Addresses();
            Orders order = new Orders();
            OrderDetails orderDetail = new OrderDetails();
            Reviews review = new Reviews();
            Dotenv dotenv = Dotenv.configure().filename(".env").load();
            String requestApiKey = String.valueOf(exchange.getRequestHeaders().getFirst("x-api-key"));
            String apiKey = dotenv.get("API_KEY");
//            if(!APIAuto.apiAuthorization(exchange)){
//                message = "400 BAD REQUEST";
//                exchange.sendResponseHeaders(400, message.length());
//                mainOut.write(message.getBytes());
//                mainOut.flush();
//                mainOut.close();
//                System.exit(0);
//            }else {
            System.out.println(apiKey);
            System.out.println(requestApiKey);
            if(apiKey != null && requestApiKey != null) {
                if ("GET".equals(exchange.getRequestMethod())) {
                    OutputStream outputStream = exchange.getResponseBody();
                    SQLiteQuery tableValue = new SQLiteQuery();
                    if (Parsing.filterCheck(allWeb) == 1) {
                        String allQuery = exchange.getRequestURI().getQuery();
                        String[] query = Parsing.filter(allQuery);
                        if (query.length == 3) {
                            if (path.equals("users")) {
                                message = tableValue.allCondition(path, "userId", query[0], query[1], query[2]);
                            } else if (path.equals("produts")) {
                                message = tableValue.allCondition(path, "productId", query[0], query[1], query[2]);
                            } else if (path.equals("orders")) {
                                message = tableValue.allCondition(path, "orderId", query[0], query[1], query[2]);
                            } else if (path.equals("addresses")) {
                                message = tableValue.allCondition(path, "userId", query[0], query[1], query[2]);
                            } else if (path.equals("order_details")) {
                                message = tableValue.allCondition(path, "orderId", query[0], query[1], query[2]);
                            } else if (path.equals("reviews")) {
                                message = tableValue.allCondition(path, "orderId", query[0], query[1], query[2]);
                            } else {
                                message = "Table Not Found";
                                exchange.sendResponseHeaders(404, message.length());
                                outputStream.write(message.getBytes());
                                outputStream.flush();
                                outputStream.close();
                            }
                        } else if (query.length == 1) {
                            if (path.equals("users")) {
                                message = tableValue.oneCondition(path, "userId", query[0]);
                            } else if (path.equals("produts")) {
                                message = tableValue.oneCondition(path, "productId", query[0]);
                            } else if (path.equals("orders")) {
                                message = tableValue.oneCondition(path, "orderId", query[0]);
                            } else if (path.equals("addresses")) {
                                message = tableValue.oneCondition(path, "userId", query[0]);
                            } else if (path.equals("order_details")) {
                                message = tableValue.oneCondition(path, "orderId", query[0]);
                            } else if (path.equals("reviews")) {
                                message = tableValue.oneCondition(path, "orderId", query[0]);
                            } else {
                                message = "Table Not Found";
                                exchange.sendResponseHeaders(404, message.length());
                                outputStream.write(message.getBytes());
                                outputStream.flush();
                                outputStream.close();
                            }
                        } else {
                            message = "Path Not Found";
                            exchange.sendResponseHeaders(404, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                        exchange.sendResponseHeaders(200, message.length());
                        outputStream.write(message.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    } else {
                        if (path.equals("users")) {
                            if (allPath.length == 2) {
                                message = tableValue.selectAll(path);
                            } else if (allPath.length == 3) {
                                message = tableValue.selectId(path, allPath[2]);
                            } else if (allPath.length == 4) {
                                message = tableValue.selectTableUser(allPath[2], allPath[3]);
                            } else {
                                message = "Path Not Found";
                                exchange.sendResponseHeaders(404, message.length());
                                outputStream.write(message.getBytes());
                                outputStream.flush();
                                outputStream.close();
                            }
                        } else if (path.equals("products")) {
                            if (allPath.length == 2) {
                                message = tableValue.selectAll(path);
                            } else if (allPath.length == 3) {
                                message = tableValue.selectId(path, allPath[2]);
                            } else {
                                message = "Path Not Found";
                                exchange.sendResponseHeaders(404, message.length());
                                outputStream.write(message.getBytes());
                                outputStream.flush();
                                outputStream.close();
                            }
                        } else if (path.equals("orders") || path.equals("reviews") || path.equals("order_details")) {
                            if (allPath.length == 2) {
                                message = tableValue.selectAll(path);
                            } else if (allPath.length == 3) {
                                message = tableValue.selectOrdersId(path, allPath[2]);
                            } else {
                                message = "Path Not Found";
                                exchange.sendResponseHeaders(404, message.length());
                                outputStream.write(message.getBytes());
                                outputStream.flush();
                                outputStream.close();
                            }
                        } else if (path.equals("addresses")) {
                            if (allPath.length == 2) {
                                message = tableValue.selectAll(path);
                            } else if (allPath.length == 3) {
                                message = tableValue.selectIdUsers(path, allPath[2]);
                            } else {
                                message = "Path Not Found";
                                exchange.sendResponseHeaders(404, message.length());
                                outputStream.write(message.getBytes());
                                outputStream.flush();
                                outputStream.close();
                            }
                        } else {
                            message = "Table Not Found";
                            exchange.sendResponseHeaders(404, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                        exchange.sendResponseHeaders(200, message.length());
                        outputStream.write(message.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    }
                } else if ("POST".equals(exchange.getRequestMethod())) {
                    OutputStream outputStream = exchange.getResponseBody();
                    BufferedReader readers = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "utf-8"));
                    StringBuilder buffer = new StringBuilder();
                    String line;
                    while ((line = readers.readLine()) != null) {
                        buffer.append(line);
                    }
                    readers.close();
                    String json = buffer.toString();
                    if (path.equals("users")) {
                        if (user.parsingJson(json) != 1) {
                            user.insert();
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else if (path.equals("products")) {
                        if (product.parsingJson(json) != 1) {
                            product.insert();
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else if (path.equals("orders")) {
                        if (order.parsingJson(json) != 1) {
                            order.insert();
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else if (path.equals("order_details")) {
                        if (orderDetail.parsingJson(json) != 1) {
                            orderDetail.insert();
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else if (path.equals("addresses")) {
                        if (address.parsingJson(json) != 1) {
                            address.insert();
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else if (path.equals("reviews")) {
                        if (review.parsingJson(json) != 1) {
                            review.insert();
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else {
                        message = "400 BAD REQUEST";
                        exchange.sendResponseHeaders(400, message.length());
                        outputStream.write(message.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    }
                } else if ("PUT".equals(exchange.getRequestMethod())) {
                    OutputStream outputStream = exchange.getResponseBody();
                    BufferedReader readers = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "utf-8"));
                    StringBuilder buffer = new StringBuilder();
                    String line;
                    while ((line = readers.readLine()) != null) {
                        buffer.append(line);
                    }
                    readers.close();
                    String json = buffer.toString();
                    if (path.equals("users")) {
                        if (user.parsingJson(json) != 1) {
                            user.update(allPath[2]);
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else if (path.equals("products")) {
                        if (product.parsingJson(json) != 1) {
                            product.update(allPath[2]);
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else if (path.equals("orders")) {
                        if (order.parsingJson(json) != 1) {
                            order.update(allPath[2]);
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else if (path.equals("order_details")) {
                        if (orderDetail.parsingJson(json) != 1) {
                            orderDetail.update(allPath[2]);
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else if (path.equals("addresses")) {
                        if (address.parsingJson(json) != 1) {
                            address.update(allPath[2]);
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else if (path.equals("reviews")) {
                        if (review.parsingJson(json) != 1) {
                            review.update(allPath[2]);
                            message = "Success";
                            exchange.sendResponseHeaders(200, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } else {
                            message = "400 BAD REQUEST";
                            exchange.sendResponseHeaders(400, message.length());
                            outputStream.write(message.getBytes());
                            outputStream.flush();
                            outputStream.close();
                        }
                    } else {
                        message = "400 BAD REQUEST";
                        exchange.sendResponseHeaders(400, message.length());
                        outputStream.write(message.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    }
                } else if ("DELETE".equals(exchange.getRequestMethod())) {
                    OutputStream outputStream = exchange.getResponseBody();
                    SQLiteQuery tableValue = new SQLiteQuery();

                    if (path.equals("users") || path.equals("addresses")) {
                        tableValue.deleteColumn(path, "userId", allPath[2]);
                    } else if (path.equals("products")) {
                        tableValue.deleteColumn(path, "productId", allPath[2]);
                    } else if (path.equals("orders") || path.equals("order_details") || path.equals("reviews")) {
                        tableValue.deleteColumn(path, "orderId", allPath[2]);
                    } else {
                        message = "Path Not Found.";
                        exchange.sendResponseHeaders(404, message.length());
                        outputStream.write(message.getBytes());
                        outputStream.flush();
                        outputStream.close();
                    }
                    message = "Delete success.";
                    exchange.sendResponseHeaders(404, message.length());
                    outputStream.write(message.getBytes());
                    outputStream.flush();
                    outputStream.close();
                } else {
                    message = "Method is under development.";
                    exchange.sendResponseHeaders(404, message.length());
                    mainOut.write(message.getBytes());
                    mainOut.flush();
                    mainOut.close();
                }
            }else{
                message = "400 BAD REQUEST";
                exchange.sendResponseHeaders(400, message.length());
                mainOut.write(message.getBytes());
                mainOut.flush();
                mainOut.close();
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
    public void update(String newId){
        SQLiteQuery updateDatabase = new SQLiteQuery();
        updateDatabase.updateUser(id, firstName, lastName, email, phoneNumber, type, newId);
    }
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

    public void update(String newId){
        SQLiteQuery updateDatabase = new SQLiteQuery();
        updateDatabase.updateProducts(id, sellerId, title, description, price, stock, newId);
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

    public void update(String newId){
        SQLiteQuery updateDatabase = new SQLiteQuery();
        updateDatabase.updateAddresses(userId, type, lineOne, lineTwo, city, province, postcode, newId);
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

    public void update(String newId){
        SQLiteQuery updateDatabase = new SQLiteQuery();
        updateDatabase.updateOrders(id, buyerId, note, total, discount, is_paid, newId);
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

    public void update(String newId){
        SQLiteQuery updateDatabase = new SQLiteQuery();
        updateDatabase.updateOrderDetails(orderId, productId, quantity, price, newId);
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

    public void update(String newId){
        SQLiteQuery updateDatabase = new SQLiteQuery();
        updateDatabase.updateReviews(orderId, star, description, newId);
    }
}
