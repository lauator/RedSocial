package service;

import model.User;
import repository.UserRepository;
import util.UserServiceException;
import util.CustomDatabaseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer createUser(String username, String password, String email) throws UserServiceException, SQLException, CustomDatabaseException {
        if (username.length() > 20) {
            throw new UserServiceException("User must contain at most 20 characters");
        } else if (username.length() < 4) {
            throw new UserServiceException("User must contain at least 4 characters");
        }

        if (password.length() > 20) {
            throw new UserServiceException("Password must contain at most 20 characters");
        } else if (password.length() < 8) {
            throw new UserServiceException("Password must contain at least 8 characters");
        }

        if (email.length() > 30) {
            throw new UserServiceException("Email must contain at most 20 characters");
        } else if (email.length() < 4) {
            throw new UserServiceException("Email must contain at least 4 characters");
        }

        if (!email.contains("@")) {
            throw new UserServiceException("Email must contain @");
        }

        if (searchUserByEmail(email) != null) {
            throw new UserServiceException("User already exists");
        }

        User user = new User(username, password, email);
        userRepository.save(user);

        return userRepository.searchUserByEmail(user.getEmail()).getUserId();

    }

    public void updateUser(Integer id, String username, String password, String email) throws UserServiceException, SQLException, CustomDatabaseException {
        if (username.length() > 20) {
            throw new UserServiceException("User must contain at most 20 characters");
        } else if (username.length() < 4) {
            throw new UserServiceException("User must contain at least 4 characters");
        }

        if (password.length() > 20) {
            throw new UserServiceException("Password must contain at most 20 characters");
        } else if (password.length() < 8) {
            throw new UserServiceException("Password must contain at least 8 characters");
        }

        if (email.length() > 30) {
            throw new UserServiceException("Email must contain at most 20 characters");
        } else if (email.length() < 4) {
            throw new UserServiceException("Email must contain at least 4 characters");
        }

        if (!email.contains("@")) {
            throw new UserServiceException("Email must contain @");
        }


        User user = searchById(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        userRepository.save(user);



    }


    public void deleteUser(Integer id) throws SQLException, CustomDatabaseException, UserServiceException {

        User user = searchById(id);

        if (user == null) {
            throw new UserServiceException("User does not exist");
        }


        userRepository.remove(user);


    }

    public User searchById(Integer id) throws SQLException, CustomDatabaseException, UserServiceException {
        if(id == null){
            throw new UserServiceException("You must indicate the id");
        }
        return userRepository.getById(id);
    }

    public List<User> listAll() throws SQLException, CustomDatabaseException, UserServiceException {
        List<User> users = userRepository.getAll();

        if(users == null){
            throw new UserServiceException("Failed to list all users");
        }



        return userRepository.getAll();

    }


    private User searchUserByEmail(String email) throws SQLException, CustomDatabaseException {
        return userRepository.searchUserByEmail(email);
    }
}
