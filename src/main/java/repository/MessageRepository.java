package repository;

import model.Message;
import model.User;
import util.CustomDatabaseException;
import util.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository implements Repository<Message> {


    @Override
    public List<Message> getAll() throws CustomDatabaseException, SQLException {
        String sql = "SELECT * FROM messages";

        ResultSet rs = DatabaseManager.read(sql);


        List<Message> messages = new ArrayList<Message>();

        while (rs.next()) {
            messages.add(createMessage(rs));
        }

        return messages;

    }

    public List<Message> getAllByUser(User user) throws CustomDatabaseException, SQLException {
        String sql = "SELECT * FROM messages WHERE user_id = ?";

        ResultSet rs = DatabaseManager.read(sql, user.getUserId());


        List<Message> messages = new ArrayList<Message>();

        while (rs.next()) {
            messages.add(createMessage(rs));
        }

        return messages;

    }

    @Override
    public Message getById(int id) throws CustomDatabaseException, SQLException {
        String sql = "SELECT * FROM messages WHERE id = ?";

        ResultSet rs = DatabaseManager.read(sql, id);

        Message message = null;
        while (rs.next()) {
            message = createMessage(rs);
        }

        return message;
    }

    @Override
    public void save(Message object) throws CustomDatabaseException {

        String sql;
        if (object.getMessageId() != null && object.getMessageId() > 0) {
            sql = "UPDATE messages set message = ?, user_id = ? WHERE id = ?";
            DatabaseManager.createUpdateDelete(sql, object.getMessage(), object.getUserId(), object.getMessageId());
        } else {
            sql = "INSERT INTO messages (message, date, user_id) VALUES (?,CURRENT_TIMESTAMP, ?)";
            DatabaseManager.createUpdateDelete(sql, object.getMessage(), object.getUserId());
        }


    }

    @Override
    public void remove(Message message) throws CustomDatabaseException {
        String sql = "DELETE FROM messages WHERE id = ?";
        DatabaseManager.createUpdateDelete(sql, message.getMessageId());
    }

    public Message createMessage(ResultSet rs) throws SQLException {
        return new Message(rs.getInt("id"), rs.getString("message"),
                rs.getString("date"), rs.getInt("user_id"));
    }

    public Message searchByUserId(Integer userId) throws SQLException, CustomDatabaseException {
        String sql = "SELECT * FROM messages WHERE user_id = ?";

        ResultSet rs = DatabaseManager.read(sql, userId);

        Message message = null;
        while (rs.next()) {
            message = createMessage(rs);
        }

        return message;
    }
}
