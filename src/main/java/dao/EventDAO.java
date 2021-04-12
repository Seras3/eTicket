package dao;

import model.Event;
import service.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDAO implements DAO<Event> {
    private Database db;

    public EventDAO() {
        db = Database.getInstance();
    }

    private Event getEvent(ResultSet result) throws SQLException {
        if(result.next()) {
            Event event = new Event();

            event.setId(result.getInt("id"));
            event.setName(result.getString("name"));
            event.setDescription(result.getString("description"));
            event.setCategory_id(result.getInt("category_id"));

            return event;
        }
        return null;
    }

    public Event find(HashMap<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM event WHERE ");
            for(Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                if(value instanceof String) {
                    sql.append(entry.getKey()).append("='").append(value).append("' AND ");
                } else
                if(value instanceof Number) {
                    sql.append(entry.getKey()).append("=").append(value).append(" AND ");
                }
            }
            sql.delete(sql.length() - 4, sql.length());



            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql.toString());

            Event event = getEvent(result);
            if (event != null) return event;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Event get(String id) {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM event WHERE id=" + id);

            Event event = getEvent(result);
            if (event != null) return event;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Event> getAll() {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM event");

            List<Event> events = new ArrayList<Event>();

            while(result.next()) {
                Event event = new Event();

                event.setId(result.getInt("id"));
                event.setName(result.getString("name"));
                event.setDescription(result.getString("description"));
                event.setCategory_id(result.getInt("category_id"));

                events.add(event);
            }
            return events;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(Event event) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO event VALUES (NULL, ?, ?, ?)");
            ps.setString(1, event.getName());
            ps.setString(2, event.getDescription());
            ps.setInt(3, event.getCategory_id());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Event event) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE event SET name=?, description=?, category_id=? WHERE id=?");
            ps.setString(1, event.getName());
            ps.setString(2, event.getDescription());
            ps.setInt(3, event.getCategory_id());
            ps.setInt(4, event.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Event event) {
        Connection connection = db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM event WHERE id=?");
            ps.setLong(1, event.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
