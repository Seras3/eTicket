package dao;

import model.Location;
import config.DatabaseConfig;
import util.Converter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class LocationDAO implements DAO<Location> {
    private DatabaseConfig db;

    public LocationDAO() {
        db = DatabaseConfig.getInstance();
    }

    private Location getLocation(ResultSet result) throws SQLException {
        if(result.next()) {
            Location location = new Location();

            location.setId(result.getInt("id"));
            location.setCountry(result.getString("country"));
            location.setCity(result.getString("city"));
            location.setDetails(result.getString("details"));

            return location;
        }
        return null;
    }

    private void fillPreparedStatement(PreparedStatement ps, Location location) throws SQLException {
        ps.setString(1, location.getCountry());
        ps.setString(2, location.getCity());
        ps.setString(3, location.getDetails());
    }

    public Location find(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM location";
            ResultSet result = statement.executeQuery(Converter.addWhereFiltersToSql(sql, params));

            if(result.next()) {
                return getLocation(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Location get(String id) {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM location WHERE id=" + id);

            Location location = getLocation(result);
            if (location != null) return location;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Location> getAll() {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM location");

            List<Location> accounts = new ArrayList<Location>();

            while(result.next()) {
                Location location = new Location();

                location.setId(result.getInt("id"));
                location.setCountry(result.getString("country"));
                location.setCity(result.getString("city"));
                location.setDetails(result.getString("details"));

                accounts.add(location);
            }
            return accounts;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(Location location) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO location VALUES (NULL, ?, ?, ?)");
            fillPreparedStatement(ps, location);

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Location location) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE location SET country=?, city=?, details=? WHERE id=?");
            fillPreparedStatement(ps, location);
            ps.setInt(4, location.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Location location) {
        Connection connection = db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM location WHERE id=?");
            ps.setLong(1, location.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
