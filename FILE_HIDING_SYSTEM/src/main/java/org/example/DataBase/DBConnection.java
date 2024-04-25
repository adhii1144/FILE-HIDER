package org.example.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static final String url = "jdbc:mysql://localhost:3306/Project";
    private static final String username = "root";
    private static final String password = "filehider";
    public static Connection con=null;
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage());
        }
        //System.out.println("Connected to DataBase");
        return con;
    }
    public static void closeConnection(){
        if(con != null){
            try{
                con.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

//    public static void main(String[] args) {
//        DBConnection.getConnection();
//    }
}
