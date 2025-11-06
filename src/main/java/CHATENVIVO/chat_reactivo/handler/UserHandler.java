package CHATENVIVO.chat_reactivo.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import CHATENVIVO.chat_reactivo.model.User;
import CHATENVIVO.chat_reactivo.service.UserService; 
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return ServerResponse.ok().body(userService.findAll(), User.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable("id")); 
        
        return userService.findById(userId)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> saveUser(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(userService::save)
                .flatMap(savedUser -> ServerResponse.status(201).bodyValue(savedUser));
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        int userId = Integer.parseInt(request.pathVariable("id"));
        
        return userService.findById(userId)
                .flatMap(user -> 
                    userService.deleteById(userId)
                    .then(ServerResponse.noContent().build())
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}