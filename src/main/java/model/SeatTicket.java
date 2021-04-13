package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatTicket extends Ticket {
    private Seat seat;

    public SeatTicket(Ticket ticket) {
        super(ticket);
    }
}
