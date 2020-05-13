package com.chrisgya.webfluxjwtauthmongo.handler;

import com.chrisgya.webfluxjwtauthmongo.entity.User;
import com.chrisgya.webfluxjwtauthmongo.model.UserEvent;
import com.chrisgya.webfluxjwtauthmongo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserRepository userRepository;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class).flatMap(user -> userRepository.save(user));
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(userMono, User.class);
    }

    public Mono<ServerResponse> listUser(ServerRequest request) {
        Flux<User> user = userRepository.findAll();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(user, User.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        String userId = request.pathVariable("userId");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<User> userMono = userRepository.findById(userId);
        return userMono.flatMap(user -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(Mono.just(user), User.class))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String userId = request.pathVariable("userId");
        userRepository.deleteById(userId);
        return ServerResponse.ok().build();
    }

    public Mono<ServerResponse> streamEvents(ServerRequest serverRequest){
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Flux.interval(Duration.ofSeconds(1))
                .map(val -> new UserEvent("" + val, "Devglan User Event")), UserEvent.class);
    }
}
