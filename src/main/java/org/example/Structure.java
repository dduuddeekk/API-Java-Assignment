package org.example;

public class Structure {
    public static String users(int id, String first_name, String last_name, String email, String phone_number, String type){
        String result = "\t\"id\" : "+id+"\n"+
                "\t\"first_name\" : "+first_name+"\n"+
                "\t\"last_name\" : "+last_name+"\n"+
                "\t\"email\" : "+email+"\n"+
                "\t\"phone_number\" : "+phone_number+"\n"+
                "\t\"type\" : "+type+"\n";
        return result;
    }
    public static String products(int id, int sellerId, String title, String description, int price, int stock){
        String result = "\t\"id\" : "+id+"\n"+
                "\t\"sellerId\" : "+sellerId+"\n"+
                "\t\"title\" : "+title+"\n"+
                "\t\"description\" : "+description+"\n"+
                "\t\"price\" : "+price+"\n"+
                "\t\"stock\" : "+stock+"\n";
        return result;
    }
    public static String addresses(int userId, String type, String lineOne, String lineTwo, String city, String province, String postcode){
        String result = "\t\"user\" : "+userId+"\n"+
                "\t\"type\" : "+type+"\n"+
                "\t\"line1\" : "+lineOne+"\n"+
                "\t\"line2\" : "+lineTwo+"\n"+
                "\t\"city\" : "+city+"\n"+
                "\t\"province\" : "+province+"\n"+
                "\t\"postcode\" : "+postcode+"\n";
        return result;
    }
    public static String orders(int id, int buyerId, String note, int total, int discount, String is_paid){
        String result = "\t\"id\" : "+id+"\n"+
                "\t\"buyer\" : "+buyerId+"\n"+
                "\t\"note\" : "+note+"\n"+
                "\t\"total\" : "+total+"\n"+
                "\t\"discount\" : "+discount+"\n"+
                "\t\"is_paid\" : "+is_paid+"\n";
        return result;
    }
    public static String orders_details(int id, int productId, int quantity, int price){
        String result = "\t\"id\" : "+id+"\n"+
                "\t\"product\" : "+productId+"\n"+
                "\t\"quantity\" : "+quantity+"\n"+
                "\t\"price\" : "+price+"\n";
        return result;
    }
    public static String reviews(int id, int star, String description){
        String result = "\t\"id\" : "+id+"\n"+
                "\t\"star\" : "+star+"\n"+
                "\t\"description\" : "+description+"\n";
        return result;
    }
}
