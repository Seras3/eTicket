package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private Integer id;
    private Integer eventId;
    private Float price;
    private Integer seatId;
    private String description;
    private String name;

    public Ticket(Ticket ob) {
        this.id = ob.getId();
        this.eventId = ob.getEventId();
        this.price = ob.getPrice();
        this.seatId = ob.getSeatId();
        this.description = ob.getDescription();
        this.name = ob.getName();
    }
}
