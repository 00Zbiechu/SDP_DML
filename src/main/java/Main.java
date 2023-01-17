import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class Main {

    static final String DB_URL = "jdbc:postgresql://localhost:9000/bankdb";
    static final String USER = "postgres";
    static final String PASS = "postgres";

    static DBControll dbControll = new DBControll();

    public static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args){

        useLiquibase();


        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS)) {

            System.out.println("Połączono");

            System.out.println("Wypisanie danych: -----------------------------------------------------");
            dbControll.selectCustomerRowFromCustomerTableWhereSurnameAsParam(conn,"Zbiewski");
            System.out.println("-----------------------------------------------------------------------");

            System.out.println("Aktualizacja danych: -----------------------------------------------------");
            dbControll.updateCustomerWhereSurnameAsParam(conn,"Zbiewski","Nowak");
            System.out.println("Wypisanie danych: -----------------------------------------------------");
            dbControll.selectCustomerRowFromCustomerTableWhereSurnameAsParam(conn,"Nowak");
            System.out.println("-----------------------------------------------------------------------");

            System.out.println("Wprowadzenie danych: -----------------------------------------------------");
            dbControll.insertDataIntoCustomerTable(conn,"Mateusz","Kowalski","Kwiatowa","Koszalin","123456789","00kowalski@gmail.com");
            System.out.println("Wypisanie danych: -----------------------------------------------------");
            dbControll.selectCustomerRowFromCustomerTableWhereSurnameAsParam(conn,"Kowalski");
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Aktualizacja danych: -----------------------------------------------------");
            dbControll.updateCustomerWhereSurnameAsParam(conn,"Nowak","Zbiewski");


        }catch(SQLException e) {
            e.printStackTrace();
        }finally {

        System.out.println("Zamykanie połączenia");
        dbControll.closeConnection();

    }
    }

    private static void useLiquibase() {

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS)) {

            try{

                // load properties from classpath Liquibase
                Properties properties = new Properties();
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));
                try (Liquibase liquibase = new liquibase.Liquibase("changelog/changelog.xml", new ClassLoaderResourceAccessor(), database)){
                    properties.forEach((key, value) -> liquibase.setChangeLogParameter(Objects.toString(key), value));
                    liquibase.update(new Contexts(), new LabelExpression());
                } catch (LiquibaseException e) {
                    e.printStackTrace();
                }

            }catch (LiquibaseException e){

                e.printStackTrace();

            }

        }catch(SQLException e) {
            e.printStackTrace();
        }



    }


}