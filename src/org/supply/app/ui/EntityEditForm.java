package org.supply.app.ui;

import org.supply.app.Application;
import org.supply.app.entity.Entity;
import org.supply.app.manager.EntityManager;
import org.supply.app.utils.BaseForm;
import org.supply.app.utils.DialogUtil;

import javax.swing.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class EntityEditForm extends BaseForm
{

    private JPanel mainPanel;
    private JTextField idField;
    private JTextField firstNameFiled;
    private JTextField lastNameField;
    private JTextField patronymicField;
    private JTextField birthdayField;
    private JTextField regDateField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField pathToImageField;
    private JComboBox genderBox;
    private JButton saveButton;
    private JButton backButton;
    private JButton deleteButton;

    private Entity entity;

    public EntityEditForm(Entity entity)
    {
        super(400,375);
        this.entity = entity;

        setContentPane(mainPanel);

        initBoxes();
        initFields();
        initButtons();

        setVisible(true);
    }

    private void initBoxes()
    {
        genderBox.addItem("Мужской");
        genderBox.addItem("Женский");
    }

    private void initFields()
    {
        idField.setEditable(false);
        idField.setText(String.valueOf(entity.getId()));
        firstNameFiled.setText(entity.getFirstname());
        lastNameField.setText(entity.getLastname());
        patronymicField.setText(entity.getPatronymic());
        birthdayField.setText(Application.DATE_FORMAT.format(entity.getBirthday()));
        regDateField.setText(Application.DATE_FORMAT.format(entity.getRegDate()));
        emailField.setText(entity.getEmail());
        phoneField.setText(entity.getPhone());
        genderBox.setSelectedIndex(entity.getGender() == 'ж' ? 0 : 1);
        pathToImageField.setText(entity.getPhotoPath());
    }

    private void initButtons()
    {
        backButton.addActionListener(e->
        {
            dispose();
            new EntityTableForm();
        });

        deleteButton.addActionListener(e->
        {
            if(DialogUtil.showConfirm(this, "Вы действительно хотите удалить данную запись?"))
            {
                try {
                    EntityManager.delete(entity);
                    DialogUtil.showInfo(this, "Данная запись удалена.");
                    dispose();
                    new EntityTableForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    DialogUtil.showError(this, "Ошибка удаления записи: " + ex.getMessage());
                }
            }
        });

        saveButton.addActionListener(e->
        {
            String firstname = firstNameFiled.getText();
            if(firstname.isEmpty() || firstname.length() > 50)
            {
                DialogUtil.showWarn(this, "Имя не введено или введено слишком большое имя");
            }
            Date birthday = null;
            try {
                birthday = Application.DATE_FORMAT.parse(birthdayField.getText());
            } catch (ParseException ex) {
                DialogUtil.showWarn(this, "Дата рождения не введена или введена неверно");
                ex.printStackTrace();
            }

            char gender = genderBox.getSelectedIndex() == 0 ? 'ж' : 'м';

            try {
                EntityManager.update(entity);
            } catch (SQLException ex) {
                ex.printStackTrace();
                DialogUtil.showError(this, "Ошибка сохранения данных: " + ex.getMessage());
                return;
            }

            DialogUtil.showInfo(this, "Клиент успешно отредактирован");
            dispose();
            new EntityTableForm();
        });
    }
}
