package dao;

import model.EventCategory;
import config.DatabaseConfig;
import util.Converter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class EventCategoryDAO implements DAO<EventCategory> {
    private DatabaseConfig db;

    public EventCategoryDAO() {
        db = DatabaseConfig.getInstance();
    }

    private EventCategory getCategory(ResultSet result) throws SQLException {
        EventCategory event_category = new EventCategory();

        event_category.setId(result.getInt("id"));
        event_category.setName(result.getString("name"));

        return event_category;
    }

    public EventCategory find(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(Converter.MapToSqlFindString(params, "event_category"));

            if(result.next()) {
                return getCategory(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public EventCategory get(String id) {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM event_category WHERE id=" + id);

            if(result.next()) {
                return getCategory(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<EventCategory> getAll() {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM event_category");

            List<EventCategory> accounts = new ArrayList<EventCategory>();

            while(result.next()) {
                accounts.add(getCategory(result));
            }

            return accounts;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(EventCategory event_category) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO event_category VALUES (NULL, ?)");
            ps.setString(1, event_category.getName());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(EventCategory event_category) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE event_category SET name=? WHERE id=?");
            ps.setString(1, event_category.getName());
            ps.setInt(2, event_category.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(EventCategory event_category) {
        Connection connection = db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM event_category WHERE id=?");
            ps.setLong(1, event_category.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

}
