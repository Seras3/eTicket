package mapper;

import dto.SeatDTO;
import dto.TicketDTO;
import dto.TicketRowDTO;
import javax.annotation.processing.Generated;
import model.Seat;
import model.SeatTicket;
import model.Ticket;
import model.TicketRow;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-14T17:05:24+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
public class TicketMapperImpl implements TicketMapper {

    @Override
    public Ticket ticketDtoToTicket(TicketDTO record) {
        if ( record == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setId( record.getId() );
        ticket.setPrice( record.getPrice() );
        ticket.setDescription( record.getDescription() );
        ticket.setName( record.getName() );

        return ticket;
    }

    @Override
    public TicketRow ticketRowDtoToTicketRow(TicketRowDTO record) {
        if ( record == null ) {
            return null;
        }

        TicketRow ticketRow = new TicketRow();

        ticketRow.setName( record.getName() );
        ticketRow.setDescription( record.getDescription() );
        ticketRow.setPrice( record.getPrice() );
        ticketRow.setSeat( record.isSeat() );
        ticketRow.setCount( record.getCount() );

        return ticketRow;
    }

    @Override
    public TicketDTO seatTicketToTicketDto(SeatTicket model) {
        if ( model == null ) {
            return null;
        }

        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setId( model.getId() );
        ticketDTO.setName( model.getName() );
        ticketDTO.setDescription( model.getDescription() );
        ticketDTO.setPrice( model.getPrice() );
        ticketDTO.setSeat( seatToSeatDTO( model.getSeat() ) );

        return ticketDTO;
    }

    @Override
    public TicketDTO ticketToTicketDto(Ticket model) {
        if ( model == null ) {
            return null;
        }

        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setId( model.getId() );
        ticketDTO.setName( model.getName() );
        ticketDTO.setDescription( model.getDescription() );
        ticketDTO.setPrice( model.getPrice() );

        return ticketDTO;
    }

    @Override
    public TicketRowDTO ticketRowToTicketRowDto(TicketRow model) {
        if ( model == null ) {
            return null;
        }

        TicketRowDTO ticketRowDTO = new TicketRowDTO();

        ticketRowDTO.setName( model.getName() );
        ticketRowDTO.setDescription( model.getDescription() );
        ticketRowDTO.setPrice( model.getPrice() );
        ticketRowDTO.setSeat( model.isSeat() );
        ticketRowDTO.setCount( model.getCount() );

        return ticketRowDTO;
    }

    protected SeatDTO seatToSeatDTO(Seat seat) {
        if ( seat == null ) {
            return null;
        }

        SeatDTO seatDTO = new SeatDTO();

        seatDTO.setId( seat.getId() );
        seatDTO.setRowOrd( seat.getRowOrd() );
        seatDTO.setPlace( seat.getPlace() );

        return seatDTO;
    }
}
