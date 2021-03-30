package service;

import dto.AccountAuthDTO;
import model.Account;
import repository.AccountRepository;

public class AuthService {
    private AccountRepository account_repository;

    public AuthService() {
        account_repository = new AccountRepository();
    }

    public enum Result {
        LOGIN_SUCCESS,
        REGISTER_SUCCESS,
        NOT_FOUND,
        USER_EXISTS
    }

    public Result login(AccountAuthDTO input) {
        Account account = account_repository.find(input);
        if(account == null)
            return Result.NOT_FOUND;

        Context.setIdentity(new Identity(account.id(), account.email(), account.role()));
        return Result.LOGIN_SUCCESS;
    }

    public Result register(AccountAuthDTO input) {
        Account account = account_repository.find(input);
        if(account != null) {
            return Result.USER_EXISTS;
        }
        account_repository.register(input);
        return Result.REGISTER_SUCCESS;
    }
}
