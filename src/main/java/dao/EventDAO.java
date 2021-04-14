package dao;

import model.Event;
import config.DatabaseConfig;
import model.EventRow;
import util.Converter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;


public class EventDAO implements DAO<Event> {
    private DatabaseConfig db;

    public EventDAO() {
        db = DatabaseConfig.getInstance();
    }

    private Event getEvent(ResultSet result) throws SQLException {
        Event event = new Event();

        event.setId(result.getInt("id"));
        event.setName(result.getString("name"));
        event.setDescription(result.getString("description"));
        event.setCategoryId(result.getInt("category_id"));

        return event;
    }

    private EventRow getEventRow(ResultSet result) throws SQLException {
        EventRow event = new EventRow();

        event.setId(result.getInt("id"));
        event.setName(result.getString("name"));
        event.setDescription(result.getString("description"));
        event.setCategory(result.getString("category"));
        event.setCountry(result.getString("country"));
        event.setCity(result.getString("city"));
        event.setStartDate(result.getTimestamp("start_date").toLocalDateTime());
        event.setEndDate(result.getTimestamp("end_date").toLocalDateTime());

        return event;
    }

    private void fillPreparedStatement(PreparedStatement ps, Event event) throws SQLException {
        ps.setString(1, event.getName());
        ps.setString(2, event.getDescription());
        ps.setInt(3, event.getCategoryId());
    }

    public Event find(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM event";
            ResultSet result = statement.executeQuery(Converter.addWhereFiltersToSql(sql, params));

            if(result.next()) {
                return getEvent(result);
            }

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

            if(result.next()) {
                return getEvent(result);
            }

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
                events.add(getEvent(result));
            }

            return events;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public List<EventRow> getAllEventRow() {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT el.id as id, e.name as name, description, " +
                        "       ec.name as category, country, city ,start_date, end_date " +
                        "FROM event as e, event_location as el, event_category as ec, location as l " +
                        "WHERE e.id = el.event_id AND " +
                        "      el.location_id = l.id AND " +
                        "      e.category_id = ec.id";

            ResultSet result = statement.executeQuery(sql);

            List<EventRow> events = new ArrayList<EventRow>();

            while(result.next()) {
                events.add(getEventRow(result));
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
            fillPreparedStatement(ps, event);

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
            fillPreparedStatement(ps, event);
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
