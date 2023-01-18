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
import java.util.Scanner;

public class Main {

    static final String DB_URL = "jdbc:postgresql://localhost:9000/bankdb";
    static final String USER = "postgres";
    static final String PASS = "postgres";

    static DBControll dbControll = new DBControll();

    public static final Logger logger = LoggerFactory.getLogger(Main.class);





    private static boolean continueFlag = true;
    private static int option;

    private static String surname;
    private static String surnameSecond;


    public static void main(String[] args){

        useLiquibase();


        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS)) {

            while(continueFlag){

                System.out.println("Wybierz operację do wykonania:");
                System.out.println("1. Pokaż wiersz z tabeli Customer dla podanego nazwiska użytkownika");
                System.out.println("2. Zmień nazwisko klienta");
                System.out.println("3. Dodaj użytkownika");
                System.out.println("4. Wyjdź z aplikacji");
                Scanner scannerOption = new Scanner(System.in);
                option = scannerOption.nextInt();

                switch(option){
                    case 1:
                        System.out.println("Podaj nazwisko klienta do wypisania jego danych:");
                        Scanner scannerSurname = new Scanner(System.in);
                        surname = scannerSurname.nextLine();
                        dbControll.selectCustomerRowFromCustomerTableWhereSurnameAsParam(conn,surname);
                        break;
                    case 2:
                        System.out.println("Podaj nazwisko klienta, którego nazwsko chcesz zmienić:");
                        Scanner scannerSurnameForUpdate = new Scanner(System.in);
                        surname = scannerSurnameForUpdate.nextLine();

                        System.out.println("Podaj na jakie inne nazwisko ma zostać zmienione:");
                        Scanner scannerSurnameForUpdateTwo = new Scanner(System.in);
                        surnameSecond = scannerSurnameForUpdateTwo.nextLine();

                        dbControll.updateCustomerWhereSurnameAsParam(conn,surname,surnameSecond);
                        break;
                    case 3:

                    case 4:
                        System.out.println("Zakończono działanie aplikacji");
                        continueFlag = false;
                        break;
                    default:
                        System.out.println("Wybrano złą opcję");
                        break;

                }

            }




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