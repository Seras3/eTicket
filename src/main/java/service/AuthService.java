package service;

import context.Context;
import context.Identity;
import dao.AccountDAO;
import dto.AccountAuthDTO;
import mapper.AccountMapper;
import model.Account;

import java.util.HashMap;

public class AuthService {
    private AccountDAO account_dao;

    public AuthService() {
        account_dao = new AccountDAO();
    }

    public enum Result {
        LOGIN_SUCCESS,
        REGISTER_SUCCESS,
        NOT_FOUND,
        USER_EXISTS
    }

    public Result login(AccountAuthDTO input) {
        Account account = account_dao.find(new HashMap<String, Object>(){{
         put("email", input.getEmail());
         put("password", input.getPassword());
        }});

        if(account == null)
            return Result.NOT_FOUND;

        Context.setIdentity(new Identity(account.getId(), account.getEmail(), account.getRoleId()));
        return Result.LOGIN_SUCCESS;
    }

    public Result register(AccountAuthDTO input) {
        Account account = account_dao.find(new HashMap<String, Object>(){{
            put("email", input.getEmail());
        }});

        if(account != null) {
            return Result.USER_EXISTS;
        }
        Account new_account = AccountMapper.INSTANCE.authDtoToAccount(input);
        new_account.setRoleId(2);   // USER
        account_dao.add(new_account);
        return Result.REGISTER_SUCCESS;
    }
}
