package org.supply.app;

import org.supply.app.ui.EntityTableForm;
import org.supply.app.utils.FontUtil;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Application
{
    public static boolean ADMIN_MODE = false;

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        FontUtil.changeAllFonts(new FontUIResource("Comic Sans MS", Font.TRUETYPE_FONT, 12));

        ADMIN_MODE = "0000".equalsIgnoreCase(JOptionPane.showInputDialog(null, "Введите пароль для входа в режим администратора",
                "Режим администратора",JOptionPane.QUESTION_MESSAGE));

        new EntityTableForm();

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://[ip адрес:порт/Название таблицы]", "Имя пользователя","Пароль");
    }
}
