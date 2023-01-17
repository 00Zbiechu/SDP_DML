import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBControll {

     private PreparedStatement preparedStatement;
     private ResultSet resultSet;


    public void selectAccountRowFromAccountTableWhereSurnameAsParam(Connection connection, String surname){

        final String QUERY = "SELECT * FROM account WHERE last_name=?";

        try{

            //SELECT 1
            preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1,surname);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                Main.logger.info("Dane uzytkownika: ");
                Main.logger.info("Imię:"+" "+resultSet.getString("first_name"));
                Main.logger.info("Nazwisko:"+" "+resultSet.getString("last_name"));
                Main.logger.info("Email:"+" "+resultSet.getString("email"));
                Main.logger.info("Hasło:"+" "+resultSet.getString("password"));

            }

        }catch (SQLException e){

            e.printStackTrace();

        }


    }

    public void updateAccountWhereSurnameAsParam(Connection connection,String oldSurname, String newSurname){

        final String UPDATE = "UPDATE account set last_name=? WHERE last_name=?";

        try{

            //UPDATE
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1,newSurname);
            preparedStatement.setString(2,oldSurname);
            preparedStatement.executeUpdate();


        }catch (SQLException e){

            e.printStackTrace();

        }

    }


    public void insertDataIntoAccountTable(Connection connection,String name,String surname,String email, String password){

        final String INSERT = "INSERT INTO account(first_name, last_name, email, password) VALUES(?,?,?,?)";


        try{

            //INSERT
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,surname);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,password);
            preparedStatement.executeUpdate();


        }catch (SQLException e){

            e.printStackTrace();

        }



    }

    public void closeConnection(){

        try{preparedStatement.close();}catch(SQLException e){e.printStackTrace();}
        try{resultSet.close();}catch(SQLException e){e.printStackTrace();}

    }

}
