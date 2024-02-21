package org.example.app.repository;

import org.example.app.database.DBConn;
import org.example.app.entity.User;
import org.example.app.utils.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserReadRepository {

    private static final Logger LOGGER =
            Logger.getLogger(UserReadRepository.class.getName());

    public List<User> readUsers() {

        try (Statement stmt = DBConn.connect().createStatement()) {

            List<User> list = new ArrayList<>();

            String sql = "SELECT id, name, phone, email FROM " + Constants.TABLE_USERS;
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
            // Повертаємо колекцію данних
            return list;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            // Якщо помилка - повертаємо пусту колекцію
            return Collections.emptyList();
        }
    }
}
