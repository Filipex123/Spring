package com.example.tercTreinamentoJava.repository;

import com.example.tercTreinamentoJava.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserName(String username);
}
