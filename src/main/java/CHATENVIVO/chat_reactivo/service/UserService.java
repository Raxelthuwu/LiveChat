package CHATENVIVO.chat_reactivo.service;

import org.springframework.stereotype.Service;

import CHATENVIVO.chat_reactivo.model.User;
import CHATENVIVO.chat_reactivo.repository.UserRepository;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> findUserById(Integer userId) {
        return userRepository.findByIdUser(userId);
    }
}
