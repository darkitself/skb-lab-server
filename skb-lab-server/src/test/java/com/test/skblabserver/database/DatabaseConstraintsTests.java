package com.test.skblabserver.database;

import com.test.skblabserver.registration.UserData;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DatabaseConstraintsTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenThisLoginExist_thenThrowException() {
        UserData test = new UserData("test", "1123Qw#", "test@test.com", "89998888881");
        UserData test1 = new UserData("test", "1123Qw#", "1test@test.com", "89998888882");
        userRepository.save(new UserEntity(test));
        userRepository.save(new UserEntity(test1));
        assertThrows(Exception.class, () -> userRepository.count());
    }
    @Test
    public void whenThisEmailExist_thenThrowException() {
        UserData test = new UserData("test", "1123Qw#", "test@test.com", "89998888881");
        UserData test1 = new UserData("test1", "1123Qw#", "test@test.com", "89998888882");
        userRepository.save(new UserEntity(test));
        userRepository.save(new UserEntity(test1));
        assertThrows(Exception.class, () -> userRepository.count());
    }
    @Test
    public void whenThisPhoneNumberExist_thenThrowException() {
        UserData test = new UserData("test", "1123Qw#", "test@test.com", "89998888881");
        UserData test1 = new UserData("test1", "1123Qw#", "1test@test.com", "89998888881");
        userRepository.save(new UserEntity(test));
        userRepository.save(new UserEntity(test1));
        assertThrows(Exception.class, () -> userRepository.count());
    }

    @Test
    public void whenUniqueData(){
        UserData test = new UserData("test", "1123Qw#", "test@test.com", "89998888881");
        UserData test1 = new UserData("test1", "1123Qw#", "1test@test.com", "89998888882");
        UserData test2 = new UserData("test2", "1123Qw#", "2test@test.com", "89998888883");
        userRepository.save(new UserEntity(test));
        userRepository.save(new UserEntity(test1));
        userRepository.save(new UserEntity(test2));
        assertEquals(3, userRepository.count());
    }
}
