package com.test.skblabserver.database;

import com.test.skblabserver.registration.UserData;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void saveUser(UserData userData){
        repository.save(new UserEntity(userData));
    }

    /**
     * Проверка переданных пользователем данных на уникальность
     *
     * @param userData данные, переданные пользователем
     */
    public boolean isUserDataUnique(UserData userData){
        return repository.findFirstByEmailOrLoginOrPhoneNumber(userData.getEmail(), userData.getLogin(), userData.getPhoneNumber()).isEmpty();
    }
}
