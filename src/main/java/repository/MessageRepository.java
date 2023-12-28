package repository;

import model.Message;
import util.CustomDatabaseException;
import util.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository implements Repository<Message> {


    @Override
    public List<Message> getAll() throws CustomDatabaseException, SQLException {
        String sql = "SELECT * FROM messages m JOIN users u ON m.id = u.id_user";

        ResultSet rs = DatabaseManager.read(sql);

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

        return createMessage(rs);
    }

    @Override
    public void save(Message object) throws CustomDatabaseException {

        String sql;
        if (object.getMessageId() != null && object.getMessageId() > 0) {
            sql = "UPDATE messages set message = ?, date = ? WHERE id = ?";
            DatabaseManager.createUpdateDelete(sql, object.getMessage(), object.getDate(), object.getMessageId());
        } else {
            sql = "INSERT INTO messages (message, date) VALUES (?,CURRENT_TIMESTAMP)";
            DatabaseManager.createUpdateDelete(sql, object.getMessage());
        }


    }

    @Override
    public void remove(Message message) throws CustomDatabaseException {
        String sql = "DELETE FROM messages WHERE id = ? AND user_id = ?";
        DatabaseManager.createUpdateDelete(sql, message.getMessageId(), message.getUserId());
    }

    public Message createMessage(ResultSet rs) throws SQLException {
        return new Message(rs.getInt("id"), rs.getString("message"),
                rs.getString("date"), rs.getString("user_id"));
    }
}
