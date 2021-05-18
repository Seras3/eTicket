package dto;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CartRowDTO {
    private List<TicketDTO> tickets;
    private EventDTO event;

    public CartRowDTO() {
        tickets = new ArrayList<TicketDTO>();
    }
}
