import java.sql.*;

public class Main {

    static final String DB_URL = "jdbc:postgresql://localhost:9000/hrdb";
    static final String USER = "postgres";
    static final String PASS = "postgres";

    static DBControll dbControll = new DBControll();


    public static void main(String[] args){

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS)){


            //SELECT + UPDATE
            dbControll.selectAccountRowFromAccountTableWhereSurnameAsParam(conn,"124");
            dbControll.updateAccountWhereSurnameAsParam(conn,"124","Nowak");
            dbControll.selectAccountRowFromAccountTableWhereSurnameAsParam(conn,"Nowak");

            //INSERT + SELECT
            dbControll.insertDataIntoAccountTable(conn,"Mateusz","Zbiewski","00zbiewski@gmail.com","1234");
            dbControll.selectAccountRowFromAccountTableWhereSurnameAsParam(conn,"Zbiewski");



        }catch(SQLException e){
            e.printStackTrace();
        }finally {

            dbControll.closeConnection();

        }

    }


}