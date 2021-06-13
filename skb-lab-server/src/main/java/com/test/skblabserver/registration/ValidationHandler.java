package com.test.skblabserver.registration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.stream.Collectors;
/**
 * Обработка исключений, возникающих на этапе валидации данных
 * Возвращает пользователю форму регистрации с сообщениями об ошибках
 */
@ControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(BindException.class)
    public String handleRestValidationException(BindException ex, Model model) {
        model.addAttribute("message", ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        return "registration";
    }
}
