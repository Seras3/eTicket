package dao;

import model.Order;
import config.DatabaseConfig;
import util.Converter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class OrderDAO implements DAO<Order> {
    private DatabaseConfig db;

    public OrderDAO() {
        db = DatabaseConfig.getInstance();
    }

    private Order getOrder(ResultSet result) throws SQLException {
        Order order = new Order();

        order.setId(result.getInt("id"));
        order.setAccountId(result.getInt("account_id"));
        order.setTicketId(result.getInt("ticket_id"));
        order.setBuyDate(result.getTimestamp("buy_date").toLocalDateTime());

        return order;
    }

    private void fillPreparedStatement(PreparedStatement ps, Order order) throws SQLException {
        ps.setInt(1, order.getAccountId());
        ps.setInt(2, order.getTicketId());
        ps.setTimestamp(3, Timestamp.valueOf(order.getBuyDate()));
    }

    public Order find(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM order";
            ResultSet result = statement.executeQuery(Converter.addWhereFiltersToSql(sql, params));

            if(result.next()){
                return getOrder(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public List<Order> findAll(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM order";
            ResultSet result = statement.executeQuery(Converter.addWhereFiltersToSql(sql, params));

            List<Order> orders = new ArrayList<Order>();

            while(result.next()){
                orders.add(getOrder(result));
            }

            return orders;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Order get(String id) {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM order WHERE id=" + id);

            if(result.next()) {
                return getOrder(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM order");

            List<Order> orders = new ArrayList<Order>();

            while(result.next()) {
                orders.add(getOrder(result));
            }

            return orders;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(Order order) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO order VALUES (NULL, ?, ?, ?)");
            fillPreparedStatement(ps, order);

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public boolean delete(Order order) {
        Connection connection = db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM order WHERE id=?");
            ps.setInt(1, order.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
