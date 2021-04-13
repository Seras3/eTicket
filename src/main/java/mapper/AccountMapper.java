package mapper;


import dto.AccountAuthDTO;
import model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper( AccountMapper.class );

    Account authDtoToAccount(AccountAuthDTO record);

    AccountAuthDTO accountToAuthDto(Account model);
}
