package mk.finki.ukim.mk.lab.service.implement;

import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidUserCredentialsException;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import mk.finki.ukim.mk.lab.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new RuntimeException("Username or password is empty");
        }
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(InvalidUserCredentialsException::new);
    }

    @Override
    public User register(String username, String password, String repeatPassword, String firstName, String lastName) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || repeatPassword == null || repeatPassword.isEmpty()) {
            throw new RuntimeException("Username or password is empty");
        }
        if (!password.equals(repeatPassword)) {
            throw new RuntimeException("Passwords do not match");
        }

        if (this.userRepository.findByUsername(username).isPresent() ||
                !this.userRepository.findByUsername(username).isEmpty()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User(username, password, firstName, lastName);
        return userRepository.save(user);
    }
}
