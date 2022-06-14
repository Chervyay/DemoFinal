package org.supply.app.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class BaseForm extends JFrame
{
    private static String baseApplicationName = "Название приложения";
    private static Image baseApplicationIcon = null;

    static {
        try {
            baseApplicationIcon = ImageIO.read(BaseForm.class.getClassLoader().getResource("Ссылка на иконку приложения из пакета ресурсов"));
        } catch (Exception e) {
            e.printStackTrace();
            DialogUtil.showError(null, "Не удалось загрузить иконку приложения");
        }
    }

    public BaseForm(int width, int height)
    {
        setTitle(baseApplicationName);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(width, height));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - height / 2);
    }
}
