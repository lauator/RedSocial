package repository;


import model.User;
import util.CustomDatabaseException;
import util.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User> {
    @Override
    public List<User> getAll() throws CustomDatabaseException, SQLException {
        String sql = "SELECT * FROM users";

        ResultSet rs = DatabaseManager.read(sql);

        List<User> messages = new ArrayList<User>();

        while (rs.next()) {
            messages.add(createUser(rs));
        }

        return messages;

    }

    @Override
    public User getById(int id) throws CustomDatabaseException, SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        ResultSet rs = DatabaseManager.read(sql, id);

        User user = null;
        while(rs.next()){
            user = createUser(rs);
        }

        return user;

    }

    @Override
    public void save(User object) throws CustomDatabaseException {

        String sql;
        if (object.getUserId() != null && object.getUserId() > 0) {
            sql = "UPDATE users set username = ?, password = ?, email = ? WHERE id = ?";
            DatabaseManager.createUpdateDelete(sql, object.getUsername(), object.getPassword(), object.getEmail(), object.getUserId());
        } else {
            sql = "INSERT INTO users (username, password, email) VALUES (?,?,?)";
            DatabaseManager.createUpdateDelete(sql, object.getUsername(), object.getPassword(), object.getEmail());
        }


    }

    @Override
    public void remove(User user) throws CustomDatabaseException {
        String sql = "DELETE FROM users WHERE id = ?";
        DatabaseManager.createUpdateDelete(sql, user.getUserId());
    }

    public User createUser(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("username"),
                rs.getString("password"), rs.getString("email"));
    }

    public User searchUserByEmail(String email) throws SQLException, CustomDatabaseException {
        String sql = "SELECT * FROM users WHERE email = ?";

        ResultSet rs = DatabaseManager.read(sql, email);

        User user = null;
        while(rs.next()){
            user = createUser(rs);
        }

        return user;
    }
}
