package service;


import model.Message;
import model.User;
import repository.MessageRepository;
import util.CustomDatabaseException;
import util.DatabaseManager;
import util.MessageServiceException;
import util.UserServiceException;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    private MessageRepository messageRepository;

    public MessageService() {
        messageRepository = new MessageRepository();
    }

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void createMessage(String message, Integer userId) throws MessageServiceException, CustomDatabaseException {
        if (message.length() > 200) {
            throw new MessageServiceException("Message must contain at most 200 characters");
        }


        Message message1 = new Message(message, userId);
        messageRepository.save(message1);

    }

    public void changeMessage(String message, Integer userId) throws SQLException, CustomDatabaseException, MessageServiceException {

        Message message1 = searchMessageByUser(userId);

        if (message.length() > 200) {
            throw new MessageServiceException("Message must contain at most 200 characters");
        }


        message1.setMessage(message);
        messageRepository.save(message1);
    }

    private Message searchMessageByUser(Integer id) throws SQLException, CustomDatabaseException {
        return messageRepository.searchByUserId(id);
    }


    public void deleteMessage(Integer userId) throws SQLException, CustomDatabaseException, MessageServiceException {

        Message message = searchMessageByUser(userId);

        if (message == null) {
            throw new MessageServiceException("Message does not exist");
        }


        messageRepository.remove(message);


    }


    public List<Message> listAll() throws SQLException, CustomDatabaseException {

        return messageRepository.getAll();

    }

    public Message searchById(Integer id) throws MessageServiceException, SQLException, CustomDatabaseException {
        if(id == null){
            throw new MessageServiceException("You must indicate the id");
        }
        return messageRepository.getById(id);
    }

    public List<Message> getAllByUser(User user) throws CustomDatabaseException, SQLException {

        List<Message> messages = messageRepository.getAllByUser(user);



        return messages;

    }


}
