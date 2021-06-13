package com.test.skblabserver.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByEmailOrLoginOrPhoneNumber(String email, String login, String phoneNumber);
}
