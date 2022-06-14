package org.supply.app.manager;

import org.supply.app.Application;
import org.supply.app.entity.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager
{
    public static void insert(Entity entity) throws SQLException
    {
        try(Connection c = Application.getConnection())
        {
            String sql = "insert into entity(FirstName, LastName, Patronymic, Birthday, RegistrationDate, Email, Phone, GenderCode, PhotoPath) " +
                    "values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getFirstname());
            ps.setString(2, entity.getLastname());
            ps.setString(3, entity.getPatronymic());
            ps.setTimestamp(4, new Timestamp((entity.getBirthday().getTime())));
            ps.setTimestamp(5, new Timestamp(entity.getRegDate().getTime()) );
            ps.setString(6, entity.getEmail());
            ps.setString(7, entity.getPhone());
            ps.setString(8, String.valueOf(entity.getGender()));
            ps.setString(9, entity.getPhotoPath());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next())
            {
                entity.setId(keys.getInt(1));
                return;
            }
            throw new SQLException("Entity not added");
        }
    }

    public static Entity selectById (int id) throws SQLException
    {
        try(Connection c = Application.getConnection()){

            String sql = "Select * from Entity where id=?";
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next())
            {
                return new Entity(resultSet.getInt("ID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Patronymic"),
                        resultSet.getTimestamp("Birthday"),
                        resultSet.getTimestamp("RegistrationDate"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Gender").charAt(0),
                        resultSet.getString("PhotoPath"));
            }
        }
        return null;
    }

    public static List<Entity> selectAll() throws SQLException {
        try(Connection c = Application.getConnection())
        {
            String sql = "Select * from Entity";
            Statement s = c.createStatement();
            ResultSet resultSet =  s.executeQuery(sql);
            List<Entity> list = new ArrayList<>();
            while(resultSet.next())
            {
                list.add(new Entity(resultSet.getInt("ID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Patronymic"),
                        resultSet.getTimestamp("Birthday"),
                        resultSet.getTimestamp("RegistrationDate"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Gender").charAt(0),
                        resultSet.getString("PhotoPath")
                ));
            }
            return list;
        }
    }

    public static void update(Entity entity) throws SQLException
    {
        try(Connection c = Application.getConnection())
        {
            String sql = "Update Entity set FirstName=?, LastName=?, Patronymic=?, Birthday=?, " +
                    "RegistrationDate=?, Email=?, Phone=?, GenderCode=?, PhotoPath=? where id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, entity.getFirstname());
            ps.setString(2, entity.getLastname());
            ps.setString(3, entity.getPatronymic());
            ps.setTimestamp(4, new Timestamp((entity.getBirthday().getTime())));
            ps.setTimestamp(5, new Timestamp(entity.getRegDate().getTime()) );
            ps.setString(6, entity.getEmail());
            ps.setString(7, entity.getPhone());
            ps.setString(8, String.valueOf(entity.getGender()));
            ps.setString(9, entity.getPhotoPath());

            ps.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        try (Connection c = Application.getConnection())
        {
            String sql = "Delete from Entity where id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static void delete(Entity entity) throws SQLException {
        delete(entity.getId());
    }
}

