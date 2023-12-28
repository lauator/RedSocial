package util;

import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/red_social";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    private DatabaseManager() {

    }

    public static synchronized Connection getConnection() throws CustomDatabaseException {
        if (connection == null) {
            try {

                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {

                throw new CustomDatabaseException("Failed to connect to database", e);
            }
        }
        return connection;
    }

    public static void disconnect() throws CustomDatabaseException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new CustomDatabaseException("Failed to disconnect", e);
        }
    }

    public static void createUpdateDelete(String sql, Object... parameters) throws CustomDatabaseException {
        try {

            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);


            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new CustomDatabaseException("Failed to operate", e);
        }
    }

    public static ResultSet read(String sql, Object... parameters) throws CustomDatabaseException {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);


            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            return statement.executeQuery();

        } catch (SQLException e) {
            throw new CustomDatabaseException("Failed to read", e);
        }


    }


}




