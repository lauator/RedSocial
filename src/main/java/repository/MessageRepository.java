package repository;

import model.Message;

import java.sql.SQLException;
import java.util.List;

public class MessageRepository implements Repository<Message> {


    @Override
    public List<Message> getAll() throws SQLException {
        return null;
    }

    @Override
    public Message getById(int id) throws SQLException {
        return null;
    }

    @Override
    public void save(Message object) throws SQLException {

    }

    @Override
    public void remove(Integer id) throws SQLException {

    }
}
