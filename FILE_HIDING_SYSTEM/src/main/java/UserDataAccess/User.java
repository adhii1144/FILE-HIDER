package UserDataAccess;

import org.example.DataBase.DBConnection;
import org.example.Users.UserData;

import java.sql.*;

public class User {

 public static boolean userExists(String email){
    String query ="SELECT email FROM Users WHERE email = ?";
    try{
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, email); // Set the email parameter in the query
        ResultSet rs =  ps.executeQuery();
        if(rs.next()){
            // User with the provided email exists
            return true;
        }
    } catch(SQLException e){
        System.out.println(e.getMessage());
    }
    // No user with the provided email found
    return false;
}

    public static int saveUser(UserData user){
        String query = "INSERT INTO Users VALUES(default, ? ,?)";
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            int rowsaffected = ps.executeUpdate();
            return rowsaffected;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
