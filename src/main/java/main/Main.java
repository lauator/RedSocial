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

        User user = userRepository.getById(3);
        user.setEmail("carlos@gmail.com");
        userRepository.save(user);
        System.out.println("Usuario actualizado");

        System.out.println("Listado de usuarios: ");

        List<User> users = userRepository.getAll();

        for (User object : users) {
            System.out.println("Usuario: " + object.toString());
        }











    }
}
