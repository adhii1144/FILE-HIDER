package Views;

import UserDataAccess.EmailService.GenerateOTP;
import UserDataAccess.EmailService.OTPService;
import UserDataAccess.EmailService.UserService;
import UserDataAccess.User;
import org.example.Users.UserData;

import java.sql.SQLException;
import java.util.Scanner;


public class Welcome {
    public static void welcomescreen(){
        Scanner sc = new Scanner(System.in);
        System.out.println("WLCOME TO THE APP");
        System.out.println("ENTER 1 TO LOGIN");
        System.out.println("ENTER 2 TO SIGNUP");
        System.out.println("ENTER 3 TO EXIT");
        System.out.println("Enter any Option : ");
        int choice = sc.nextInt();
        if(choice == 1){
            login();
        }else if(choice == 2){
            signup();
        }else{
            System.exit(0);
        }
    }

public static void login() {
    Scanner sc = new Scanner(System.in);
    try {
        System.out.println("Enter your email: ");
        String email = sc.nextLine();

        // Check if user exists or not
        if (User.userExists(email)) {
            String genOTP = GenerateOTP.getOTP();
            OTPService.sendOTP(email, genOTP);
            System.out.println("Enter the OTP sent to " + email + ": ");
            String otp = sc.nextLine();

            // Verify OTP
            if (otp.equals(genOTP)) {
                System.out.println("**Verified**");

                // Navigate to user's home menu
                UserView userView = new UserView(email);
                userView.home();
            } else {
                System.out.println("**Wrong OTP**");
            }
        } else {
            System.out.println("User does not exist. Please sign up first.");
        }
    } catch (Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
    }
}

    public static void signup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Name :");
        String name = sc.nextLine();
        System.out.println("Enter Your email : ");
        String email = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        OTPService.sendOTP(email,genOTP);
        System.out.println("Enter the OTP sent to "+email+":");
        String otp = sc.nextLine();
        if(otp.equals(genOTP)){
            UserData u = new UserData(name,email);
            int response = UserService.saveUser(u);
            switch (response){
                case 0 -> System.out.println("**User Registed Sucessfully**");
                case 1 -> System.out.println("User already exist");
            }
        }else{
            System.out.println("**Wrong OTP**");
        }
    }


}
