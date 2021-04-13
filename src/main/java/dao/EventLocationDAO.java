package dao;

import model.EventLocation;
import config.DatabaseConfig;
import util.Converter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;


public class EventLocationDAO implements DAO<EventLocation> {
    private DatabaseConfig db;

    public EventLocationDAO() {
        db = DatabaseConfig.getInstance();
    }

    private EventLocation getEventLocation(ResultSet result) throws SQLException {
        EventLocation event_location = new EventLocation();

        event_location.setId(result.getInt("id"));
        event_location.setEventId(result.getInt("event_id"));
        event_location.setLocationId(result.getInt("location_id"));
        event_location.setStartDate(result.getTimestamp("start_date").toLocalDateTime());
        event_location.setEndDate(result.getTimestamp("end_date").toLocalDateTime());

        return event_location;

    }

    private void fillPreparedStatement(PreparedStatement ps, EventLocation event_location) throws SQLException {
        ps.setInt(1, event_location.getEventId());
        ps.setInt(2, event_location.getLocationId());
        ps.setTimestamp(3, Timestamp.valueOf(event_location.getStartDate()));
        ps.setTimestamp(4, Timestamp.valueOf(event_location.getEndDate()));
    }

    public EventLocation find(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(Converter.MapToSqlFindString(params, "event_location"));

            if(result.next()) {
                return getEventLocation(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public EventLocation get(String id) {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM event_location WHERE id=" + id);

            if(result.next()) {
                return getEventLocation(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<EventLocation> getAll() {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM event_location");

            List<EventLocation> event_locations = new ArrayList<EventLocation>();

            while(result.next()) {
                event_locations.add(getEventLocation(result));
            }

            return event_locations;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(EventLocation event_location) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO event_location VALUES (NULL, ?, ?, ?, ?)");
            fillPreparedStatement(ps, event_location);

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(EventLocation event_location) {
        Connection connection = db.getConnection();
        try {
            String sql = "UPDATE event_location " +
                    "SET event_id=?, location_id=?, start_date=?, end_date=? " +
                    "WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            fillPreparedStatement(ps, event_location);
            ps.setInt(5, event_location.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(EventLocation event_location) {
        Connection connection = db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM event_location WHERE id=?");
            ps.setLong(1, event_location.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

}
