package CHATENVIVO.chat_reactivo.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import CHATENVIVO.chat_reactivo.handler.ChatHandler;
import CHATENVIVO.chat_reactivo.handler.UserHandler;

@Configuration
public class ChatRouter {

    @Bean
    public RouterFunction<ServerResponse> route(ChatHandler chatHandler, UserHandler userHandler) {

        RouterFunction<ServerResponse> chatRoutes = RouterFunctions
                .route(GET("/messages"), chatHandler::getMessages)      
                .andRoute(POST("/messages"), chatHandler::saveMessage); 

        RouterFunction<ServerResponse> userRoutes = RouterFunctions
                .route(GET("/users"), userHandler::getAllUsers)         
                .andRoute(GET("/users/{id}"), userHandler::getUserById) 
                .andRoute(POST("/users"), userHandler::saveUser)       
                .andRoute(DELETE("/users/{id}"), userHandler::deleteUser); 

        return RouterFunctions
                .nest(path("/api"), chatRoutes.and(userRoutes));  
    }
}
