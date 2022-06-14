package org.supply.app.utils;

import javax.swing.*;
import java.awt.*;

public class DialogUtil {
    public static void showError(Component parentComponent, String text)
    {
        JOptionPane.showMessageDialog(parentComponent, text, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean showConfirm(Component parentComponent, String text)
    {
        return JOptionPane.showOptionDialog(parentComponent,
                text,
                "Подтверждение",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Подтверждение", "Отмена"}, "Подтверждение")
                == JOptionPane.YES_OPTION;
    }

    public static void showWarn(Component parentComponent, String text)
    {
        JOptionPane.showMessageDialog(parentComponent, text, "Внимание", JOptionPane.WARNING_MESSAGE);
    }

    public static void showInfo(Component parentComponent, String text)
    {
        JOptionPane.showMessageDialog(parentComponent, text, "Информация", JOptionPane.INFORMATION_MESSAGE);
    }
}
