package org.example.app.controller;

import org.example.app.service.UserCreateService;
import org.example.app.utils.AppStarter;
import org.example.app.utils.Constants;
import org.example.app.view.UserCreateView;

public class UserCreateController {

    UserCreateView view;
    UserCreateService service;

    public UserCreateController(UserCreateService service, UserCreateView view) {
        this.service = service;
        this.view = view;
    }

    public void createUser() {
        // Получаем данные.
        // Передаем данные на обработку и получаем результат.
        String str = service.createUser(view.getData());
        // Проверяем результат.
        // Если БД отсутствует, выводим сообщение об этом
        // и закрываем приложение.
        // Иначе выводим сообщение и перезапускаем приложение.
        if (str.equals(Constants.DB_ABSENT_MSG)) {
            // Выводим уведомление.
            view.getOutput(str);
            // Закрываем приложение.
            System.exit(0);
        } else {
            // Выводим уведомление.
            view.getOutput(str);
            // Перезапускаем приложение.
            AppStarter.startApp();
        }
    }
}
