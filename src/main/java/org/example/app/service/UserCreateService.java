package org.example.app.service;

import org.example.app.database.DBCheck;
import org.example.app.entity.User;
import org.example.app.exceptions.CreateException;
import org.example.app.exceptions.DBException;
import org.example.app.repository.UserCreateRepository;
import org.example.app.utils.Constants;
import org.example.app.utils.EmailValidator;
import org.example.app.utils.PhoneValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserCreateService {

    UserCreateRepository repository;
    private static final Logger LOGGER =
            Logger.getLogger(UserCreateService.class.getName());

    public UserCreateService(UserCreateRepository repository) {
        this.repository = repository;
    }

    public String createUser(String[] data) {
        // Проверяем на наличие файла БД.
        // ДА - работаем с данными. НЕТ - уведомление об отсутствии БД.
        if (DBCheck.isDBExists()) {
            try {
                throw new DBException(Constants.DB_ABSENT_MSG);
            } catch (DBException e) {
                LOGGER.log(Level.SEVERE, Constants.LOG_DB_ABSENT_MSG);
                return e.getMessage();
            }
        }

        Map<String, String> errors = validateData(data);

        if (!errors.isEmpty()) {
            try {
                throw new CreateException("Check inputs", errors);
            } catch (CreateException ce) {
                LOGGER.log(Level.WARNING, Constants.LOG_DATA_INPTS_WRONG_MSG);
                return ce.getErrors(errors);
            }
        }

        return repository.createUser(mapData(data));
    }

    private Map<String, String> validateData(String[] data) {
        // Map для сбора ошибок
        Map<String, String> errors = new HashMap<>();

        if (data[0].trim().isEmpty())
            errors.put("name", Constants.INPUT_REQ_MSG);

        if (PhoneValidator.isPhoneValid(data[1].trim()))
            errors.put("phone", Constants.WRONG_PHONE_MSG);

        if(EmailValidator.isEmailValid(data[2].trim()))
            errors.put("email", Constants.WRONG_EMAIL_MSG);

        return errors;
    }

    // Перетворюємо масив даних в об'єкт.
    private User mapData(String[] data) {
        // створюємо об'єкт
        User user = new User();
        // Устанавлюємо значення властивостей об'єкта.
        user.setName(data[0].trim());
        user.setPhone(data[1].trim());
        user.setEmail(data[2].trim());
        // Повертаємо об'єкт
        return user;
    }
}
