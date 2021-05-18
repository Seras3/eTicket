package mapper;


import dto.CartRowDTO;
import model.CartRow;
import model.SeatTicket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper( CartMapper.class );

    default CartRow cartRowDtoToCartRow(CartRowDTO record) {
        return null;
        // TODO
    }

    default CartRowDTO cartRowToCartRowDto(CartRow model) {
        CartRowDTO dto = new CartRowDTO();
        model.getTickets().forEach((ticket) -> {
            if (ticket instanceof SeatTicket) {
                dto.getTickets().add(TicketMapper.INSTANCE.seatTicketToTicketDto((SeatTicket) ticket));
            } else {
                dto.getTickets().add(TicketMapper.INSTANCE.ticketToTicketDto(ticket));
            }
        });
        dto.setEvent(EventMapper.INSTANCE.eventToEventDto(model.getEvent()));
        return dto;
    }

}
