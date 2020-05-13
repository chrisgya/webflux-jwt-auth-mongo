package com.chrisgya.webfluxjwtauthmongo.repository;

import com.chrisgya.webfluxjwtauthmongo.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);
}
