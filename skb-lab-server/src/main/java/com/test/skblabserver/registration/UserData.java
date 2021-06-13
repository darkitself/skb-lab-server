package com.test.skblabserver.registration;

import com.test.skblabserver.validators.Password;
import com.test.skblabserver.validators.Phone;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserData implements Serializable {
    @Size(min = 3, max = 33, message = "Размер логина должен быть в диапазоне от 3 до 33")
    private String login;
    @Password(message = "Пароль должен содержать минимум один из символов: 0-9, A-Z, a-z, специальные символы. Быть не короче 6 символов")
    private String password;
    @NotBlank(message = "Поле Email обязательно к заполнению")
    @Email(message = "Email должно иметь формат адреса электронной почты")
    private String email;
    @Phone(message = "Введите действующий номер телефона")
    private String phoneNumber;

    public UserData withEncryptedPassword(String encryptedPassword) {
        return new UserData(login, encryptedPassword, email, password);
    }

    public UserData(String login, String password, String email, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
