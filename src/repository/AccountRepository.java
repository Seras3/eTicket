package repository;

import dao.AccountDAO;
import dto.AccountAuthDTO;
import model.Account;

import java.util.List;

public class AccountRepository implements Repository<Account> {
    private AccountDAO account_dao;
    long next_id;

    public AccountRepository() {
        account_dao = new AccountDAO();
        next_id = 0;
        for(Account ob : account_dao.getAll()) {
            next_id = Long.max(Long.parseLong(ob.id()), next_id);
        }
        next_id += 1;
    }

    public Account find(AccountAuthDTO input) {
        List<Account> accounts = account_dao.getAll();
        for(Account account : accounts) {
            if(account.email().equals(input.email()) &&
               account.password().equals(input.password())) {
                return new Account(account);
            }
        }
        return null;
    }

    public Account register(AccountAuthDTO input) {
        Account account = new Account(String.valueOf(next_id), input.email(), input.password(), 0);
        next_id += 1;
        add(account);
        return account;
    }

    @Override
    public Account get(String id) {
        return account_dao.get(id);
    }

    @Override
    public void add(Account obj) {
        account_dao.add(obj);
    }

    @Override
    public void update(Account obj) {
        account_dao.update(obj);
    }

    @Override
    public void delete(Account obj) {
        account_dao.delete(obj);
    }
}
