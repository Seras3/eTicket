package mapper;

import dto.TicketDTO;
import dto.TicketRowDTO;
import model.SeatTicket;
import model.Ticket;
import model.TicketRow;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper( TicketMapper.class );

    Ticket ticketDtoToTicket(TicketDTO record);

    TicketRow ticketRowDtoToTicketRow(TicketRowDTO record);


    TicketDTO seatTicketToTicketDto(SeatTicket model);

    TicketDTO ticketToTicketDto(Ticket model);

    TicketRowDTO ticketRowToTicketRowDto(TicketRow model);
}
