package main;

import model.Message;
import model.User;
import repository.MessageRepository;
import repository.UserRepository;
import util.CustomDatabaseException;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws CustomDatabaseException, SQLException {

        UserRepository userRepository = new UserRepository();

        MessageRepository messageRepository = new MessageRepository();

        /*Message message = new Message("Este es el mensaje de lauator numero 2", 1);
        messageRepository.save(message);

*/

        Message newMessage = messageRepository.getById(7);
       /* newMessage.setMessage("Este el penultimo mensaje de Carlos");
        messageRepository.save(newMessage);*/

        /*User user = userRepository.getById(1);

         */



        messageRepository.remove(newMessage);

        List<Message> messages = messageRepository.getAll();



        System.out.println("Todos los mensajes");
        for (Message message : messages) {
            System.out.println(message.toString());
        }



















    }
}
