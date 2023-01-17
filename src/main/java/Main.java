import java.sql.*;

public class Main {

    static final String DB_URL = "jdbc:postgresql://localhost:9000/hrdb";
    static final String USER = "postgres";
    static final String PASS = "postgres";
    static final String QUERY = "SELECT * FROM account WHERE last_name=?";
    static final String UPDATE = "UPDATE account set last_name='Nowak' WHERE last_name='Zbiewski'";
    static final String INSERT = "INSERT INTO account VALUES('3','Krystian','Szajewski','ksz@gmail.com','1234')";
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    public static void main(String[] args) throws SQLException {

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS)){


            //SELECT 1
            preparedStatement = conn.prepareStatement(QUERY);
            preparedStatement.setString(1,"Zbiewski");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                System.out.println(resultSet.getString("first_name"));
                System.out.println(resultSet.getString("last_name"));


            }


            //UPDATE
            preparedStatement = conn.prepareStatement(UPDATE);
            int executeQuery = preparedStatement.executeUpdate();
            System.out.println(executeQuery);





            //SELECT 2
            preparedStatement = conn.prepareStatement(QUERY);
            preparedStatement.setString(1,"Nowak");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                System.out.println(resultSet.getString("first_name"));
                System.out.println(resultSet.getString("last_name"));


            }

            //INSERT
            preparedStatement = conn.prepareStatement(INSERT);
            int executeQuerySecond = preparedStatement.executeUpdate();
            System.out.println(executeQuerySecond);



        }catch(SQLException e){
            e.printStackTrace();
        }finally {


            if(preparedStatement!=null){
                preparedStatement.close();
            }
            if(resultSet!=null){
                resultSet.close();
            }


        }

    }
}