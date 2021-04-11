import dao.AccountDAO;
import mapper.AccountMapper;
import model.Account;

import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        AccountDAO account_dao = new AccountDAO();
        Account account = account_dao.find(new HashMap<String, Object>() {{
            put("id", 1);
        }});

        System.out.println(AccountMapper.INSTANCE.toRecord(account));
    }
}
