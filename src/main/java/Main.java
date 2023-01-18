import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;


import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

@Slf4j
public class Main {

    static final String DB_URL = "jdbc:postgresql://localhost:9000/bankdb";
    static final String USER = "postgres";
    static final String PASS = "postgres";

    static DBControll dbControll = new DBControll();

    private static boolean continueFlag = true;
    private static int option, id;
    private static String surname,surnameSecond;

    //Variables for Insert Query
    private static String name,street,city,phone,email;


    public static void main(String[] args){

        useLiquibase();


        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS)) {

            while(continueFlag){

                log.info("Wybierz operację do wykonania:");
                log.info("1. Pokaż wiersz z tabeli Customer dla podanego nazwiska użytkownika");
                log.info("2. Zmień nazwisko klienta");
                log.info("3. Dodaj użytkownika");
                log.info("4. Usuń pożyczkę");
                log.info("5. Wyjdź z aplikacji");
                Scanner scannerForOption = new Scanner(System.in);
                option = scannerForOption.nextInt();

                switch(option){
                    case 1:
                        log.info("Podaj nazwisko klienta do wypisania jego danych:");
                        Scanner scannerForSurnameSelect = new Scanner(System.in);
                        surname = scannerForSurnameSelect.nextLine();
                        dbControll.selectCustomerRowFromCustomerTableWhereSurnameAsParam(conn,surname);
                        break;
                    case 2:
                        log.info("Podaj nazwisko klienta, którego nazwsko chcesz zmienić:");
                        Scanner scannerForUpdateNew = new Scanner(System.in);
                        surname = scannerForUpdateNew.nextLine();

                        log.info("Podaj na jakie inne nazwisko ma zostać zmienione:");
                        Scanner scannerForUpdateOld = new Scanner(System.in);
                        surnameSecond = scannerForUpdateOld.nextLine();

                        dbControll.updateCustomerWhereSurnameAsParam(conn,surname,surnameSecond);
                        break;
                    case 3:
                        log.info("Podaj wartości do wprowadzenia:");

                        log.info("Podaj imie klienta:");
                        Scanner scannerForFirstName = new Scanner(System.in);
                        name = scannerForFirstName.nextLine();

                        log.info("Podaj nazwisko klienta:");
                        Scanner scannerForInsertSurname = new Scanner(System.in);
                        surname = scannerForInsertSurname.nextLine();

                        log.info("Podaj ulicę klienta:");
                        Scanner scannerForStreet = new Scanner(System.in);
                        street = scannerForStreet.nextLine();

                        log.info("Podaj miasto klienta:");
                        Scanner scannerForCity = new Scanner(System.in);
                        city = scannerForCity.nextLine();

                        log.info("Podaj nr telefonu klienta:");
                        Scanner scannerForPhone = new Scanner(System.in);
                        phone = scannerForPhone.nextLine();

                        log.info("Podaj email klienta:");
                        Scanner scannerForMail = new Scanner(System.in);
                        email = scannerForMail.nextLine();

                        dbControll.insertDataIntoCustomerTable(conn,name,surname,street,city,phone,email);
                        break;

                    case 4:
                        log.info("Podaj id pożyczki klienta do usunięcia:");
                        Scanner scannerForDelete = new Scanner(System.in);
                        id = scannerForDelete.nextInt();
                        dbControll.deleteLoanRowFromCustomerTableWhereIdAsParam(conn,id);
                        break;
                    case 5:
                        log.info("Zakończono działanie aplikacji");
                        continueFlag = false;
                        break;
                    default:
                        log.info("Wybrano złą opcję");
                        break;

                }

            }




        }catch(SQLException e) {
            e.printStackTrace();
        }finally {

        log.info("Zamykanie połączenia");
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