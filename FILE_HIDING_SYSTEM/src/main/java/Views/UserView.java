package Views;

import UserDataAccess.DataDao;
import org.example.Users.UserContent;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    UserView(String email){
        this.email = email;
    }
    public void home(){
        while (true) {
            System.out.println("Welcome "+this.email);
            System.out.println("Enter 1 to show the hidden files");
            System.out.println("Enter 2 to hide a file");
            System.out.println("Enter 3 to unhide the file");
            System.out.println("Enter 4 to Exit");
            Scanner sc = new Scanner(System.in);
            int ch = sc.nextInt();
            switch(ch){
                case 1 -> {
                    try{
                        List<UserContent> files = DataDao.getAllFiles(this.email);
                        System.out.println("ID  -   File Name ");
                        for(UserContent x : files){
                            System.out.println(x.getId() + " -   "+ x.getFilename());
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path : ");
                    String path = sc.nextLine();
                    File f = new File(path);
                    UserContent file = new UserContent(0,f.getName(),path,this.email);
                    try{
                        DataDao.hidefile(file);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    System.out.println("File Hidden Sucessfully!!");

                }
                case 3 -> {
                    try{
                        List<UserContent> files = DataDao.getAllFiles(this.email);
                        System.out.println("ID  -   File Name ");
                        for(UserContent x : files){
                            System.out.println(x.getId() + " - "+ x.getFilename() );
                        }
                        System.out.println("Enter the id of file to unhide :");
                        int id = sc.nextInt();
                        boolean isValidID = false;
                        for(UserContent x : files){
                                if(x.getId() == id){
                                    isValidID = true;
                                    break;
                                }
                        }
                        if(isValidID){
                            DataDao.unhide(id);
                        }else{
                            System.out.println("Worng ID Try again");
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    System.exit(0);
                }
            }
        }
    }
}
