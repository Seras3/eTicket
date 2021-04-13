package dao;

import model.Account;
import config.DatabaseConfig;
import util.Converter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;


public class AccountDAO implements DAO<Account> {
    private DatabaseConfig db;

    public AccountDAO() {
        db = DatabaseConfig.getInstance();
    }

    private Account getAccount(ResultSet result) throws SQLException {
        Account account = new Account();

        account.setId(result.getInt("id"));
        account.setEmail(result.getString("email"));
        account.setPassword(result.getString("password"));
        account.setRoleId(result.getInt("role_id"));

        return account;
    }

    private void fillPreparedStatement(PreparedStatement ps, Account account) throws SQLException {
        ps.setString(1, account.getEmail());
        ps.setString(2, account.getPassword());
        ps.setInt(3, account.getRoleId());
    }

    public Account find(Map<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM account";
            ResultSet result = statement.executeQuery(Converter.addWhereFiltersToSql(sql, params));

            if(result.next()){
                return getAccount(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Account get(String id) {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM account WHERE id=" + id);

            if(result.next()) {
                return getAccount(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> getAll() {
        Connection connection = db.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM account");

            List<Account> accounts = new ArrayList<Account>();

            while(result.next()) {
                accounts.add(getAccount(result));
            }

            return accounts;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(Account account) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO account VALUES (NULL, ?, ?, ?)");
            fillPreparedStatement(ps, account);

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Account account) {
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE account SET email=?, password=?, role_id=? WHERE id=?");
            fillPreparedStatement(ps, account);
            ps.setInt(4, account.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Account account) {
        Connection connection = db.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM account WHERE id=?");
            ps.setLong(1, account.getId());

            if(ps.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

}
