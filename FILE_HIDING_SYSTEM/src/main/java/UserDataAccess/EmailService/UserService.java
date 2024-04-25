package UserDataAccess.EmailService;

import UserDataAccess.User;
import org.example.Users.UserData;

import java.sql.SQLException;

public class UserService {
    public static Integer saveUser(UserData user){
        try{
            if(User.userExists(user.getEmail())){
                return 0;
            }else{
                return User.saveUser(user);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
