package org.supply.app.ui;

import org.supply.app.Application;
import org.supply.app.entity.Entity;
import org.supply.app.manager.EntityManager;
import org.supply.app.utils.BaseForm;
import org.supply.app.utils.CustomTableModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

public class EntityTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;
    private JButton sortByIdButton;
    private JButton sortByBirthdayButton;
    private JButton clearSortButton;
    private JComboBox genderFilterBox;
    private JComboBox nameFilterBox;
    private JLabel rowCountLabel;

    private CustomTableModel<Entity> model;

    private boolean idSort = true;
    private boolean dateSort = false;

    public EntityTableForm() {
        super(1200, 800);
        setContentPane(mainPanel);

        initTable();
        initBoxes();
        initButtons();

        setVisible(true);

    }

    private void initTable() {
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);


        try {
            model = new CustomTableModel<>(Entity.class, new String[]{"ID", "Имя", "Фамилия", "Отчество",
                    "Дата рождения", "Дата регистрации", "Email",
                    "Телефон", "Гендер", "Путь до картинки", "Картинка"},
                    EntityManager.selectAll());
            table.setModel(model);
            TableColumn column = table.getColumn("Путь до картинки");
            column.setPreferredWidth(0);
            column.setMinWidth(0);
            column.setMaxWidth(0);

            updateRowCountLabel(model.getRows().size(), model.getRows().size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && Application.ADMIN_MODE == true) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        dispose();
                        new EntityEditForm(model.getRows().get(row));
                    }
                }
            }
        });

    }

    private void initBoxes()
    {
        genderFilterBox.addItem("Все");
        genderFilterBox.addItem("Мужской");
        genderFilterBox.addItem("Женский");

        genderFilterBox.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    applyFilters();
                }
            }
        });

        nameFilterBox.addItem("Все");
        try {
            List<Entity> list = EntityManager.selectAll();
            Set<String> names = new HashSet<>();
            for(Entity c : list)
            {
                names.add(c.getFirstname());
            }
            for(String s : names)
            {
                nameFilterBox.addItem(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        nameFilterBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    applyFilters();
                }
            }
        });
    }

    private void applyFilters()
    {
        try {
            List<Entity> list = EntityManager.selectAll();
            int max = list.size();
            int index = genderFilterBox.getSelectedIndex();
            if(index == 1)
            {
                list.removeIf(c -> c.getGender() != 'м');
            } else if(index == 2){
                list.removeIf(c -> c.getGender() != 'ж');
            }

            if(nameFilterBox.getSelectedIndex() != 0)
            {
                list.removeIf(c -> !c.getFirstname().equals(nameFilterBox.getSelectedItem()));
            }

            model.setRows(list);
            model.fireTableDataChanged();

            idSort = true;
            dateSort = false;

            updateRowCountLabel(list.size(), max);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initButtons()
    {
        addButton.addActionListener(e->
        {
            if(Application.ADMIN_MODE == true) {
                dispose();
                new EntityCreateForm();
            }
        });

        clearSortButton.addActionListener(e->
        {
            genderFilterBox.setSelectedItem(0);
            nameFilterBox.setSelectedItem(0);
        });

        sortByIdButton.addActionListener(e->
        {
            Collections.sort(model.getRows(), new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    if(idSort)
                    {
                        return Integer.compare(o2.getId(), o1.getId());
                    }
                    else
                    {
                        return Integer.compare(o1.getId(), o2.getId());
                    }
                }});
            idSort = !idSort;
            dateSort = false;
            model.fireTableDataChanged();
        });

        sortByBirthdayButton.addActionListener(e -> {
            Collections.sort(model.getRows(), new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    if(dateSort)
                    {
                        return o2.getBirthday().compareTo(o1.getBirthday());
                    }
                    else
                    {
                        return o1.getBirthday().compareTo(o2.getBirthday());
                    }
                }
            });
            dateSort =! dateSort;
            idSort = false;
            model.fireTableDataChanged();
        });
    }

    private void updateRowCountLabel(int actual, int max)
    {
        rowCountLabel.setText("Отображено записей: " + actual + "/" + max);
    }
}
