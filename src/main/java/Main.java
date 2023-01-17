import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Main {

    static final String DB_URL = "jdbc:postgresql://localhost:9000/hrdb";
    static final String USER = "postgres";
    static final String PASS = "postgres";

    static DBControll dbControll = new DBControll();

    public static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args){

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS)){

            logger.info("Wypisanie danych: -----------------------------------------------------");
            //SELECT + UPDATE
            dbControll.selectAccountRowFromAccountTableWhereSurnameAsParam(conn,"124");
            logger.info("Aktualizacja danych: -----------------------------------------------------");
            dbControll.updateAccountWhereSurnameAsParam(conn,"124","Nowak");
            logger.info("Wypisanie danych: -----------------------------------------------------");
            dbControll.selectAccountRowFromAccountTableWhereSurnameAsParam(conn,"Nowak");

            logger.info("Wprowadzenie danych: -----------------------------------------------------");
            //INSERT + SELECT
            dbControll.insertDataIntoAccountTable(conn,"Mateusz","Zbiewski","00zbiewski@gmail.com","1234");
            logger.info("Wypisanie danych: -----------------------------------------------------");
            dbControll.selectAccountRowFromAccountTableWhereSurnameAsParam(conn,"Zbiewski");



        }catch(SQLException e){
            e.printStackTrace();
        }finally {

            dbControll.closeConnection();

        }

    }


}