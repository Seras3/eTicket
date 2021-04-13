package dao;

import model.Seat;
import config.DatabaseConfig;
import util.Converter;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class SeatDAO implements DAO<Seat> {
    private DatabaseConfig db;

    public SeatDAO() {
        db = DatabaseConfig.getInstance();
    }

    private Seat getSeat(ResultSet result) throws SQLException {
        Seat seat = new Seat();

        seat.setId(result.getInt("id"));
        seat.setRowOrd(result.getInt("row_ord"));
        seat.setPlace(result.getInt("place"));

        return seat;
    }

    private void fillPreparedStatement(PreparedStatement ps, Seat seat) throws SQLException {
        ps.setInt(1, seat.getRowOrd());
        ps.setInt(2, seat.getPlace());
    }

    public Seat findForTicket(String eventId, String ticketName, Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT s.id as id, row_ord, place " +
                    "FROM ticket t JOIN seat s ON (t.seat_id = s.id)";
            sql = Converter.addWhereFiltersToSql(sql, params);
            sql += " AND t.event_id = " + eventId + " AND t.name = '" + ticketName + "'";
            ResultSet result = statement.executeQuery(sql);

            if(result.next()){
                return getSeat(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public Seat find(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM seat";
            ResultSet result = statement.executeQuery(Converter.addWhereFiltersToSql(sql, params));

            if(result.next()){
                return getSeat(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Seat get(String id) {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM seat WHERE id=" + id);

            if(result.next()) {
                return getSeat(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Seat> getAll() {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM seat");

            List<Seat> seats = new ArrayList<Seat>();

            while(result.next()) {
                seats.add(getSeat(result));
            }

            return seats;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(Seat seat) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO seat VALUES (NULL, ?, ?)");
            fillPreparedStatement(ps, seat);

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Seat seat) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE seat SET row_ord=?, place=? WHERE id=?");
            fillPreparedStatement(ps, seat);
            ps.setInt(3, seat.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Seat seat) {
        Connection connection = db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM seat WHERE id=?");
            ps.setLong(1, seat.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

}
