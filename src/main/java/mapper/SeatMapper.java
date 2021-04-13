package mapper;

import dto.SeatDTO;
import model.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SeatMapper {

    SeatMapper INSTANCE = Mappers.getMapper( SeatMapper.class );

    Seat seatDtoToSeat(SeatDTO record);

    SeatDTO seatToSeatDto(Seat model);


}
