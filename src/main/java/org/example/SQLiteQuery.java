package org.example;

import java.sql.*;
import java.sql.SQLException;
import java.io.IOException;
import java.util.ArrayList;

public class SQLiteQuery {
    private Connection connect(){
        Connection connection = null;
        String rootPath = System.getProperty("user.dir");
        String url = "jdbc:sqlite:" + rootPath + "/dbBelanja.db";
        try{
            connection = DriverManager.getConnection(url);
            System.out.println("connection ok");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }
    public void inputUsers(int id, String firstName, String lastName, String email, String phoneNumber, String type) {
        String sql = "INSERT INTO users(userId, first_name, last_name, email, phone_number, type) VALUES(?,?,?,?,?,?)";
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, firstName );
            pstmt.setString(3, lastName );
            pstmt.setString(4, email );
            pstmt.setString(5, phoneNumber );
            pstmt.setString(6, type );
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void inputProducts(int id, int sellerId, String title, String description, int price, int stock) {
        String sql = "INSERT INTO products(productId, sellerId, title, description, price, stock) VALUES(?,?,?,?,?,?)";
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, sellerId );
            pstmt.setString(3, title );
            pstmt.setString(4, description );
            pstmt.setInt(5, price );
            pstmt.setInt(6, stock );
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void inputAddresses(int userId, String type, String lineOne, String lineTwo, String city, String province, String postcode) {
        String sql = "INSERT INTO addresses(userId, type, line1, line2, city, province, postcode) VALUES(?,?,?,?,?,?,?)";
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId );
            pstmt.setString(2, type );
            pstmt.setString(3, lineOne );
            pstmt.setString(4, lineTwo );
            pstmt.setString(5, city );
            pstmt.setString(6, province );
            pstmt.setString(7, postcode );
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void inputOrders(int id, int buyerId, String note, int total, int discount, String is_paid) {
        String sql = "INSERT INTO orders(orderId, buyerId, note, total, discount, is_paid) VALUES(?,?,?,?,?,?)";
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id );
            pstmt.setInt(2, buyerId );
            pstmt.setString(3, note );
            pstmt.setInt(4, total );
            pstmt.setInt(5, discount );
            pstmt.setString(6, is_paid );
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void inputReviews(int id, int star, String description) {
        String sql = "INSERT INTO reviews(orderId, star, description) VALUES(?,?,?)";
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id );
            pstmt.setInt(2, star );
            pstmt.setString(3, description );
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void inputOrderDetails(int id, int productId, int quantity, int price) {
        String sql = "INSERT INTO order_details(orderId, productId, quantity, price) VALUES(?,?,?,?)";
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id );
            pstmt.setInt(2, productId );
            pstmt.setInt(3, quantity );
            pstmt.setInt(4, price );
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String appendString(ArrayList<String> json){
        StringBuffer group = new StringBuffer();
        for (String s : json){
            group.append(s);
            group.append("\n\n");
        }
        String result = group.toString();
        return result;
    }

    public String selectAll(String table){
        String sql = "SELECT * FROM " + table + ";";
        ArrayList<String> result = new ArrayList<String>();
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if(table.equals("users")){
                while(resultSet.next()) {
                    result.add(Structure.users(resultSet.getInt("userId"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("type")));
                }
            } else if (table.equals("products")){
                while(resultSet.next()) {
                    result.add(Structure.products(resultSet.getInt("productId"), resultSet.getInt("sellerId"), resultSet.getString("title"), resultSet.getString("description"), resultSet.getInt("price"), resultSet.getInt("stock")));
                }
            } else if (table.equals("addresses")) {
                while (resultSet.next()){
                    result.add(Structure.addresses(resultSet.getInt("userId"), resultSet.getString("type"), resultSet.getString("line1"), resultSet.getString("line1"), resultSet.getString("city"), resultSet.getString("province"), resultSet.getString("postcode")));
                }
            } else if (table.equals("orders")){
                while (resultSet.next()) {
                    result.add(Structure.orders(resultSet.getInt("orderId"), resultSet.getInt("buyerId"), resultSet.getString("note"), resultSet.getInt("total"), resultSet.getInt("discount"), resultSet.getString("is_paid")));
                }
            } else if (table.equals("order_details")){
                while (resultSet.next()) {
                    result.add(Structure.order_details(resultSet.getInt("orderId"), resultSet.getInt("productId"), resultSet.getInt("quantity"), resultSet.getInt("price")));
                }
            } else if (table.equals("reviews")){
                while (resultSet.next()) {
                    result.add(Structure.reviews(resultSet.getInt("orderId"), resultSet.getInt("star"), resultSet.getString("description")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String jsonSend = "{\n"+appendString(result)+"\n}";
        return jsonSend;
    }

    public String selectId(String table, String id){
        String sql = "SELECT * FROM "+table+" WHERE id = "+id+";";
        ArrayList<String> result = new ArrayList<String>();
        try{
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if(table.equals("users")){
                while(resultSet.next()) {
                    result.add(Structure.users(resultSet.getInt("userId"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("type")));
                }
            } else if (table.equals("products")){
                while(resultSet.next()) {
                    result.add(Structure.products(resultSet.getInt("productId"), resultSet.getInt("sellerId"), resultSet.getString("title"), resultSet.getString("description"), resultSet.getInt("price"), resultSet.getInt("stock")));
                }
            } else if (table.equals("orders")){
                while (resultSet.next()) {
                    result.add(Structure.orders(resultSet.getInt("orderId"), resultSet.getInt("buyerId"), resultSet.getString("note"), resultSet.getInt("total"), resultSet.getInt("discount"), resultSet.getString("is_paid")));
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        String jsonSend = "{\n"+appendString(result)+"\n}";
        return jsonSend;
    }

    public String selectOrdersId(String table, String id){
        String sql = "SELECT * FROM "+table+" WHERE orderId = "+id+";";
        ArrayList<String> result = new ArrayList<String>();
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (table.equals("orders")){
                while (resultSet.next()) {
                    result.add(Structure.orders(resultSet.getInt("orderId"), resultSet.getInt("buyerId"), resultSet.getString("note"), resultSet.getInt("total"), resultSet.getInt("discount"), resultSet.getString("is_paid")));
                }
            } else if (table.equals("reviews")){
                while (resultSet.next()) {
                    result.add(Structure.reviews(resultSet.getInt("orderId"), resultSet.getInt("star"), resultSet.getString("description")));
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        String jsonSend = "{\n"+appendString(result)+"\n}";
        return jsonSend;
    }

    public String selectIdUsers(String table, String id){
        String sql = "SELECT * FROM "+table+" WHERE userId = "+id+";";
        ArrayList<String> result = new ArrayList<String>();

        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (table.equals("addresses")) {
                while (resultSet.next()){
                    result.add(Structure.addresses(resultSet.getInt("userId"), resultSet.getString("type"), resultSet.getString("line1"), resultSet.getString("line1"), resultSet.getString("city"), resultSet.getString("province"), resultSet.getString("postcode")));
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        String jsonSend = "{\n"+appendString(result)+"\n}";
        return jsonSend;
    }

    public String selectTableUser(String id, String tableOne){
        String sql = "SELECT * FROM users WHERE id = "+id;
        String jsonSend = "";
        System.out.println(id);
        try{
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Users user = new Users();
            user.setUser(resultSet.getInt("userId"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("type"));

            if(tableOne.equals("addresses")){
                jsonSend = selectIdUsers(tableOne, String.valueOf(user.id()));
            }else if(tableOne.equals("products")){
                jsonSend = selectId(tableOne, String.valueOf(user.id()));
            }else if(tableOne.equals("orders")){
                jsonSend = selectId(tableOne, String.valueOf(user.id()));
            }else if(tableOne.equals("reviews")){
                String sQLFind = "SELECT * FROM orders WHERE buyerId = "+id;
                Connection connection1 = this.connect();
                Statement statement1 = connection1.createStatement();
                ResultSet resultSet1 = statement1.executeQuery(sQLFind);
                Orders order = new Orders();
                order.setOrders(resultSet.getInt("orderId"), resultSet.getInt("buyerId"), resultSet.getString("note"), resultSet.getInt("total"), resultSet.getInt("discount"), resultSet.getString("is_paid"));

                jsonSend = selectOrdersId(tableOne, String.valueOf(order.getId()));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return jsonSend;
    }
}
