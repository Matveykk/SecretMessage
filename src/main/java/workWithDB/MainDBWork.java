package workWithDB;

import main.SecretMessage;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainDBWork {

    static Connection connection = ConnectionManager.open();

    static Statement statement;

    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MainDBWork() throws SQLException {
    }

    public static String saveToDB(String message) throws SQLException {

        ArrayList<String> messageAndHash = SecretMessage.SendMessage(message);

        String sql = "insert into secret_table(message, hash_code) " +
                     "values ('" + messageAndHash.get(0) + "', '" + messageAndHash.get(1) + "')";

        statement.execute(sql);

        return messageAndHash.get(1);
    }

    public static String takeFromDB(String code) throws SQLException {

        String sql = "select * from secret_table where hash_code = '" + code + "'";


        ResultSet result = statement.executeQuery(sql);

        result.next();

        String res = result.getString("message");

        sql = "delete from secret_table where hash_code = '" + code + "'";

        statement.execute(sql);

        return res;
    }

    public static void saveUserData(Long id, String username) throws SQLException {

        String userId = id + "";

        String sql = "insert into userdata(username, userid)" +
                     "values ('" + username + "', '" + userId + "')";


        statement.execute(sql);

    }

    public static boolean hasUserId(Long id) {
        String userId = id + "";

        String sql = "select * from userdata where UserID = '" + userId + "'";

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.next();

            String hasStr = resultSet.getString("userid");

            if (hasStr != null) {
                return true;
            }

        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}
