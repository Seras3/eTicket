package mapper;

import dto.AccountAuthDTO;
import javax.annotation.processing.Generated;
import model.Account;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-11T18:25:29+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account fromRecord(AccountAuthDTO record) {
        if ( record == null ) {
            return null;
        }

        Account account = new Account();

        account.setEmail( record.getEmail() );
        account.setPassword( record.getPassword() );

        return account;
    }

    @Override
    public AccountAuthDTO toRecord(Account model) {
        if ( model == null ) {
            return null;
        }

        AccountAuthDTO accountAuthDTO = new AccountAuthDTO();

        accountAuthDTO.setEmail( model.getEmail() );
        accountAuthDTO.setPassword( model.getPassword() );

        return accountAuthDTO;
    }
}
