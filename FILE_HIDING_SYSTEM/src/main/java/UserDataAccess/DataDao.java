package UserDataAccess;

import org.example.DataBase.DBConnection;
import org.example.Users.UserContent;
import org.example.Users.UserData;

import javax.xml.crypto.Data;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataDao {
    public static List<UserContent> getAllFiles(String email) {
        List<UserContent> files = null;
        try {
            String query = "SELECT * FROM Data WHERE email=?";
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            files = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String path = rs.getString("path");
                files.add(new UserContent(id, name, path));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return files;
    }
    public static int hidefile(UserContent file) {
        int ans = 0;
        try {
            String query = "INSERT INTO Data(name,path,email,data) VALUES (?,?,?,?)";
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, file.getFilename());
            ps.setString(2, file.getPath());
            ps.setString(3, file.getEmail());
            File f = new File(file.getPath());
            FileReader fr = new FileReader(f);
            ps.setCharacterStream(4, fr, f.length());
            ans = ps.executeUpdate();
            fr.close();
            f.delete();
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return ans;
    }
    public static void unhide(int id ){
        try{
            String query = "SELECT path,data FROM Data WHERE id = ?";
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String path = rs.getString("path");
            Clob c = rs.getClob("data");
            Reader r = c.getCharacterStream();
            FileWriter fw = new FileWriter(path);
            int i;
            while((i = r.read())!= -1){
                fw.write((char) i);
            }
            fw.close();
            ps = con.prepareStatement("delete from Data where id = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("Successfully Unhidden");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


}
