package dao;

import config.DatabaseConfig;
import model.Log;
import util.Time;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDao implements DAO<Log>{
    private DatabaseConfig db;

    public LogDao() { db = DatabaseConfig.getInstance(); }

    private Log getLog(ResultSet result) throws SQLException {
        Log log = new Log();

        log.setId(result.getInt("id"));
        log.setAccountId(result.getInt("account_id"));
        log.setRoute(result.getString("route"));
        log.setCreatedAt(result.getTimestamp("created_at").toLocalDateTime());

        return log;
    }

    private void fillPreparedStatement(PreparedStatement ps, Log log) throws SQLException {
        ps.setInt(1, log.getAccountId());
        ps.setString(2, log.getRoute());
        ps.setTimestamp(3, Time.getCurrentTimeStamp());
    }

    @Override
    public Log get(String id) {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM log WHERE id=" + id);

            if(result.next()) {
                return getLog(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Log> getAll() {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM log");

            List<Log> logs = new ArrayList<Log>();

            while(result.next()) {
                logs.add(getLog(result));
            }

            return logs;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(Log log) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO log VALUES (NULL, ?, ?, ?)");
            fillPreparedStatement(ps, log);

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Log log) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE log SET account_id=?, route=?, created_at=? WHERE id=?");
            fillPreparedStatement(ps, log);
            ps.setInt(4, log.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Log log) {
        Connection connection = db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM log WHERE id=?");
            ps.setLong(1, log.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
