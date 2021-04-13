package dao;

import model.Ticket;
import config.DatabaseConfig;
import util.Converter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class TicketDAO implements DAO<Ticket> {
    private DatabaseConfig db;

    public TicketDAO() {
        db = DatabaseConfig.getInstance();
    }

    private Ticket getTicket(ResultSet result) throws SQLException {
        Ticket ticket = new Ticket();

        ticket.setId(result.getInt("id"));
        ticket.setEventId(result.getInt("event_id"));
        ticket.setPrice(result.getFloat("price"));
        ticket.setSeatId(result.getInt("seat_id"));
        ticket.setDescription(result.getString("description"));
        ticket.setName(result.getString("name"));

        return ticket;

    }

    private void fillPreparedStatement(PreparedStatement ps, Ticket ticket) throws SQLException {
        ps.setInt(1, ticket.getEventId());
        ps.setFloat(2, ticket.getPrice());
        ps.setInt(3, ticket.getSeatId());
        ps.setString(4, ticket.getDescription());
        ps.setString(5, ticket.getName());
    }

    public Ticket find(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(Converter.MapToSqlFindString(params, "ticket"));

            if(result.next()) {
                return getTicket(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public List<Ticket> findAll(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(Converter.MapToSqlFindString(params, "ticket"));

            List<Ticket> accounts = new ArrayList<Ticket>();

            while(result.next()) {
                accounts.add(getTicket(result));
            }

            return accounts;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Ticket get(String id) {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM ticket WHERE id=" + id);

            if(result.next()) {
                return getTicket(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM ticket");

            List<Ticket> accounts = new ArrayList<Ticket>();

            while(result.next()) {
                accounts.add(getTicket(result));
            }

            return accounts;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(Ticket ticket) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ticket VALUES (NULL, ?, ?, ?, ?, ?)");
            fillPreparedStatement(ps, ticket);

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Ticket ticket) {
        Connection connection = db.getConnection();
        try {
            String sql = "UPDATE ticket SET event_id=?, price=?, seat_id=?, description=?, name=? WHERE id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            fillPreparedStatement(ps, ticket);
            ps.setInt(6, ticket.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Ticket ticket) {
        Connection connection = db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM ticket WHERE id=?");
            ps.setLong(1, ticket.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
