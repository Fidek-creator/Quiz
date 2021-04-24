 package sample;

import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

 public class dbConnection {
    public Connection dbConn;

    public Connection getConnection(){
        String nazwa = "quiz";
        String konto = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost/"+nazwa;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConn = DriverManager.getConnection(url,konto,pass);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return dbConn;
    }


}