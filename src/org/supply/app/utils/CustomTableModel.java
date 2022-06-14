package org.supply.app.utils;

import lombok.AllArgsConstructor;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.List;

@AllArgsConstructor
public class CustomTableModel<T> extends AbstractTableModel {

    private Class<T> cls;
    private String[] columnNames;
    private List<T> rows;

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try{
            Field field = cls.getDeclaredFields()[columnIndex];
            field.setAccessible(true);
            return field.get(rows.get(rowIndex));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "Error!";
        }
    }

    @Override
    public String getColumnName(int column) {
        return column < columnNames.length ? columnNames[column] : "Столбец";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cls.getDeclaredFields()[columnIndex].getType();
    }

    public Class<T> getCls() {
        return cls;
    }

    public void setCls(Class<T> cls) {
        this.cls = cls;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
