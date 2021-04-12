package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private Integer id;
    private Integer event_id;
    private Float price;
    private Integer seat_id;
    private String description;
}
