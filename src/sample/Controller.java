package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private Button start;
    @FXML private Button odp1;
    @FXML private Button odp2;
    @FXML private Button odp3;
    @FXML private Button odp4;
    @FXML private Labeled pytanie;
    @FXML private Labeled points;
    @FXML private Labeled info;

    int prawidlowaodp=0;
    int punkty=0;
    boolean tablica[]= new boolean[4];

    public void startquiz() throws SQLException {
        start.setVisible(false);
        odp1.setVisible(true);
        odp2.setVisible(true);
        odp3.setVisible(true);
        odp4.setVisible(true);
        pytanie.setVisible(true);
        points.setVisible(true);

        for(int a=0; a<4; a++)
            tablica[a]=false;


        //todo:umyc dupe (zrobic losowosc pytan)
        wczytajpytanie(wczytaj());



    }

    public void startodp1(){
    weryfikujodp(1, odp1);
    }

    public void startodp2(){
    weryfikujodp(2, odp2);
    }

    public void startodp3(){
    weryfikujodp(3, odp3);
    }

    public void startodp4(){
    weryfikujodp(4, odp4);
    }


    public void weryfikujodp(int a, Button przycisk){

        if (tablica[a-1]==false){
            if (prawidlowaodp == a) {
                punkty++;
                points.setText("Punkty: " + punkty);
                info.setText("Wlasciwa odpowiedz");
                przycisk.setStyle("-fx-background-color:#2fff00;");
            }
            else {
                przycisk.setStyle("-fx-background-color:#ff0000;");
                info.setText("Bledna odpowiedz");
                punkty--;
                points.setText("Punkty:"+ punkty);
            }
            start.setVisible(true);
            start.setText("Nastepne pytanie");
            tablica[a-1]=true;
        }

    }

    public void wczytajpytanie(int maxid) throws SQLException {
        dbConnection link = new dbConnection();
        Connection linkDB = link.getConnection();

        int losoweid=(int)((Math.random()*maxid)+1);
        String q = "SELECT odp1,odp2,odp3,odp4,wlasciwa,tresc FROM pytania WHERE ID="+losoweid;
        Statement statement = linkDB.createStatement();
        ResultSet results =  statement.executeQuery(q);

        results.next();

        pytanie.setText(results.getString(6));
        odp1.setText(results.getString(1));
        odp2.setText(results.getString(2));
        odp3.setText(results.getString(3));
        odp4.setText(results.getString(4));

        odp1.setStyle("-fx-background-color:#fff;");
        odp2.setStyle("-fx-background-color:#fff;");
        odp3.setStyle("-fx-background-color:#fff;");
        odp4.setStyle("-fx-background-color:#fff;");

        prawidlowaodp=Integer.parseInt(results.getString(5));
        System.out.println(prawidlowaodp);//
        System.out.println(losoweid);//
        info.setText("");
    }

    public int wczytaj() throws SQLException {
        dbConnection link = new dbConnection();
        Connection linkDB = link.getConnection();

        String q = "SELECT ID FROM pytania ORDER BY ID DESC LIMIT 1";
        Statement statement = linkDB.createStatement();
        ResultSet results =  statement.executeQuery(q);

        results.next();
        int maxid=Integer.parseInt(results.getString(1));

        System.out.println(maxid);
        return maxid;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        odp1.setVisible(false);
        odp2.setVisible(false);
        odp3.setVisible(false);
        odp4.setVisible(false);
        pytanie.setVisible(false);
        points.setVisible(false);
    }
}
