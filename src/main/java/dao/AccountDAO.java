package dao;

import model.Account;
import service.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AccountDAO implements DAO<Account> {
    private Database db;

    public AccountDAO() {
        db = Database.getInstance();
    }

    private Account getAccount(ResultSet result) throws SQLException {
        if(result.next()) {
            Account account = new Account();

            account.setId(result.getLong("id"));
            account.setEmail(result.getString("email"));
            account.setPassword(result.getString("password"));
            account.setRole_id(result.getInt("role_id"));

            return account;
        }
        return null;
    }

    public Account find(HashMap<String, Object> params) {
        if(params.isEmpty())
            return null;

        Connection connection = db.getConnection();

        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM account WHERE ");
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

            Account account = getAccount(result);
            if (account != null) return account;


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

            Account account = getAccount(result);
            if (account != null) return account;

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
                Account account = new Account();

                account.setId(result.getLong("id"));
                account.setEmail(result.getString("email"));
                account.setPassword(result.getString("password"));
                account.setRole_id(result.getInt("role_id"));

                accounts.add(account);
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
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getPassword());
            ps.setInt(3, account.getRole_id());

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
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getPassword());
            ps.setInt(3, account.getRole_id());

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
