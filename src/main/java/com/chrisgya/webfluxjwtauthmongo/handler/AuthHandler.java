package com.chrisgya.webfluxjwtauthmongo.handler;

import com.chrisgya.webfluxjwtauthmongo.entity.User;
import com.chrisgya.webfluxjwtauthmongo.model.ApiResponse;
import com.chrisgya.webfluxjwtauthmongo.model.LoginRequest;
import com.chrisgya.webfluxjwtauthmongo.model.LoginResponse;
import com.chrisgya.webfluxjwtauthmongo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class AuthHandler {
    @Autowired
    private TokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<LoginRequest> loginRequest = request.bodyToMono(LoginRequest.class);
        return loginRequest.flatMap(login -> userRepository.findByUsername(login.getUsername())
                .flatMap(user -> {
                    if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                        return ServerResponse.ok().contentType(APPLICATION_JSON)
                                .body(Mono.just(new LoginResponse(tokenProvider.generateToken(user))), LoginResponse.class);
                    } else {
                        return ServerResponse.badRequest()
                                .body(Mono.just(new ApiResponse(400, "Invalid credentials", null)), ApiResponse.class);
                    }
                }).switchIfEmpty(ServerResponse.badRequest()
                        .body(Mono.just(new ApiResponse(400, "User does not exist", null)), ApiResponse.class)
                )
        );
    }

    public Mono<ServerResponse> signUp(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        return userMono.map(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return user;
        }).flatMap(user -> userRepository.findByUsername(user.getUsername())
                .flatMap(dbUser -> ServerResponse.badRequest()
                        .body(Mono.just(new ApiResponse(400, "User already exist", null)), ApiResponse.class)
                ).switchIfEmpty(userRepository.save(user)
                        .flatMap(savedUser -> ServerResponse.ok().contentType(APPLICATION_JSON)
                                .body(Mono.just(savedUser), User.class)
                        )
                )
        );
    }
}
