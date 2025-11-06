package CHATENVIVO.chat_reactivo.service;

import org.springframework.stereotype.Service;

import CHATENVIVO.chat_reactivo.model.User;
import CHATENVIVO.chat_reactivo.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    public Mono<Void> deleteById(Integer id) {
        return userRepository.deleteById(id);
    }
}