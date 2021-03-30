package dao;

import model.Account;
import service.Database;

import java.util.ArrayList;
import java.util.List;


public class AccountDAO implements DAO<Account> {
    private Database db;
    private String store_file_name;
    private String[] header;

    public AccountDAO() {
        db = Database.getInstance();
        store_file_name = "account.csv";
        header = new String[] {"id", "email", "password", "role"};
    }

    @Override
    public Account get(String id) {
        List<Account> accounts = getAll();
        return accounts.stream()
                .filter(ob -> id.equals(ob.id()))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Account> getAll() {
        List<String[]> parsed_file = db.parse(store_file_name);
        String[] row;
        List<Account> accounts = new ArrayList();
        for(int i = 1; i < parsed_file.size(); i++) {
            row = parsed_file.get(i);
            accounts.add(new Account(row[0], row[1], row[2], Integer.parseInt(row[3])));
        }
        return accounts;
    }

    @Override
    public void add(Account account) {
        List<Account> accounts = getAll();
        accounts.add(account);
        db.saveCSV(store_file_name, header, convert(accounts));
    }

    @Override
    public void update(Account account) {
        List<Account> accounts = getAll();
        int index = accounts.indexOf(get(account.id()));
        if(index >= 0) {
            accounts.set(index, account);
            db.saveCSV(store_file_name, header, convert(accounts));
        }
    }

    @Override
    public void delete(Account account) {
        List<Account> accounts = getAll();
        accounts.removeIf(ob -> ob.equals(account));
        db.saveCSV(store_file_name, header, convert(accounts));
    }

    @Override
    public List<String[]> convert(List<Account> accounts) {
        List<String[]> converted_content = new ArrayList();
        String[] row = new String[4];
        for(Account account : accounts) {
            row[0] = account.id();
            row[1] = account.email();
            row[2] = account.password();
            row[3] = String.valueOf(account.role());
            converted_content.add(row.clone());
        }
        return converted_content;
    }

}
