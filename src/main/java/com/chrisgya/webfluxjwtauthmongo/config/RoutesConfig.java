package com.chrisgya.webfluxjwtauthmongo.config;

import com.chrisgya.webfluxjwtauthmongo.handler.AuthHandler;
import com.chrisgya.webfluxjwtauthmongo.handler.UserHandler;
import com.chrisgya.webfluxjwtauthmongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RoutesConfig {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthHandler authHandler;

    @Bean
    public RouterFunction userRoute() {
        UserHandler userHandler = new UserHandler(userRepository);
        return RouterFunctions
                .route(POST("/users").and(accept(APPLICATION_JSON)), userHandler::createUser)
                .andRoute(GET("/users").and(accept(APPLICATION_JSON)), userHandler::listUser)
                .andRoute(GET("/users/{userId}").and(accept(APPLICATION_JSON)), userHandler::getUserById)
                .andRoute(PUT("/users").and(accept(APPLICATION_JSON)), userHandler::createUser)
                .andRoute(DELETE("/users/{userId}").and(accept(APPLICATION_JSON)), userHandler::deleteUser);
    }

    @Bean
    public RouterFunction authRoute() {
        return RouterFunctions
                .route(POST("/auth/login").and(accept(APPLICATION_JSON)), authHandler::login)
                .andRoute(POST("/auth/signup").and(accept(APPLICATION_JSON)), authHandler::signUp);
    }
}
