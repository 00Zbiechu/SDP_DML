import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class DBControll {

     private PreparedStatement preparedStatement;
     private ResultSet resultSet;


    public void selectCustomerRowFromCustomerTableWhereSurnameAsParam(Connection connection, String surname){

        final String QUERY = "SELECT * FROM customer WHERE last_name=?";

        try{

            //SELECT 1
            preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1,surname);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                log.info("Dane uzytkownika: -------------------------------- ");
                log.info(resultSet.getString("first_name"));
                log.info(resultSet.getString("last_name"));
                log.info(resultSet.getString("street"));
                log.info(resultSet.getString("city"));
                log.info(resultSet.getString("phone"));
                log.info(resultSet.getString("email"));

            }

        }catch (SQLException e){

            e.printStackTrace();

        }


    }

    public void updateCustomerWhereSurnameAsParam(Connection connection, String oldSurname, String newSurname){

        final String UPDATE = "UPDATE customer set last_name=? WHERE last_name=?";

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


    public void insertDataIntoCustomerTable(Connection connection,String name,String surname,String street, String city, String phone, String email){

        final String INSERT = "INSERT INTO customer(first_name, last_name, street, city, phone, email) VALUES(?,?,?,?,?,?)";


        try{

            //INSERT
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,surname);
            preparedStatement.setString(3,street);
            preparedStatement.setString(4,city);
            preparedStatement.setString(5,phone);
            preparedStatement.setString(6,email);
            preparedStatement.executeUpdate();


        }catch (SQLException e){

            e.printStackTrace();

        }



    }

    public void deleteLoanRowFromCustomerTableWhereIdAsParam(Connection connection, int id){

        final String QUERY = "DELETE FROM loan WHERE id=?";

        try{

            //SELECT 1
            preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();


        }catch (SQLException e){

            e.printStackTrace();

        }


    }

    public void closeConnection(){

        try{preparedStatement.close();}catch(SQLException e){e.printStackTrace();}
        try{resultSet.close();}catch(SQLException e){e.printStackTrace();}

    }

}
