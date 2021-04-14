package mapper;

import dto.SeatDTO;
import javax.annotation.processing.Generated;
import model.Seat;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-14T17:05:24+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
public class SeatMapperImpl implements SeatMapper {

    @Override
    public Seat seatDtoToSeat(SeatDTO record) {
        if ( record == null ) {
            return null;
        }

        Seat seat = new Seat();

        seat.setId( record.getId() );
        seat.setRowOrd( record.getRowOrd() );
        seat.setPlace( record.getPlace() );

        return seat;
    }

    @Override
    public SeatDTO seatToSeatDto(Seat model) {
        if ( model == null ) {
            return null;
        }

        SeatDTO seatDTO = new SeatDTO();

        seatDTO.setId( model.getId() );
        seatDTO.setRowOrd( model.getRowOrd() );
        seatDTO.setPlace( model.getPlace() );

        return seatDTO;
    }
}
