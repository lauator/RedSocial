package service;

import model.User;
import repository.UserRepository;
import util.UserServiceException;
import util.CustomDatabaseException;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String username, String password, String email) throws UserServiceException, SQLException, CustomDatabaseException {
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

    }

    public void changeUsername(String email, String newUsername, String password) throws SQLException, CustomDatabaseException, UserServiceException {

        User user = searchUserByEmail(email);

        if (user == null) {
            throw new UserServiceException("User does not exist");
        }

        if (newUsername.length() > 20) {
            throw new UserServiceException("User must contain at most 20 characters");
        } else if (newUsername.length() < 4) {
            throw new UserServiceException("User must contain at least 4 characters");
        }

        if (!user.getPassword().equals(password)) {
            throw new UserServiceException("The password don´t match");
        }

        user.setUsername(newUsername);
        userRepository.save(user);
    }

    public void changePassword(String email, String password, String newPassword) throws SQLException, CustomDatabaseException, UserServiceException {

        User user = searchUserByEmail(email);

        if (user == null) {
            throw new UserServiceException("User does not exist");
        }

        if (newPassword.length() > 20) {
            throw new UserServiceException("Password must contain at most 20 characters");
        } else if (newPassword.length() < 4) {
            throw new UserServiceException("Password must contain at least 4 characters");
        }

        if (!user.getPassword().equals(password)) {
            throw new UserServiceException("The password don´t match");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public void deleteUser(String email) throws SQLException, CustomDatabaseException, UserServiceException {

        User user = searchUserByEmail(email);

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

    public List<User> listAll() throws SQLException, CustomDatabaseException {

        return userRepository.getAll();

    }


    private User searchUserByEmail(String email) throws SQLException, CustomDatabaseException {
        return userRepository.searchUserByEmail(email);
    }
}
