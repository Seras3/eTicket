package dao;

import model.Cart;
import config.DatabaseConfig;
import util.Converter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class CartDAO implements DAO<Cart> {
    private DatabaseConfig db;

    public CartDAO() {
        db = DatabaseConfig.getInstance();
    }

    private Cart getCart(ResultSet result) throws SQLException {
        Cart cart = new Cart();

        cart.setAccountId(result.getInt("account_id"));
        cart.setTicketId(result.getInt("ticket_id"));

        return cart;
    }

    private void fillPreparedStatement(PreparedStatement ps, Cart cart) throws SQLException {
        ps.setInt(1, cart.getAccountId());
        ps.setInt(2, cart.getTicketId());
    }

    public Cart find(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM cart";
            ResultSet result = statement.executeQuery(Converter.addWhereFiltersToSql(sql, params));

            if(result.next()){
                return getCart(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public List<Cart> findAll(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM cart";
            ResultSet result = statement.executeQuery(Converter.addWhereFiltersToSql(sql, params));

            List<Cart> carts = new ArrayList<Cart>();

            while(result.next()){
                carts.add(getCart(result));
            }

            return carts;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Cart get(String id) {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM cart WHERE account_id=" + id);

            if(result.next()) {
                return getCart(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cart> getAll() {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM cart");

            List<Cart> carts = new ArrayList<Cart>();

            while(result.next()) {
                carts.add(getCart(result));
            }

            return carts;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(Cart cart) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO cart VALUES (?, ?)");
            fillPreparedStatement(ps, cart);

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Cart cart) {
        return false;
    }

    @Override
    public boolean delete(Cart cart) {
        Connection connection = db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM cart WHERE account_id=? AND ticket_id=?");
            fillPreparedStatement(ps, cart);

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
