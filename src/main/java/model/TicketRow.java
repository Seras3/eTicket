package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRow {
    private String name;
    private String description;
    private Float price;
    private boolean seat;
    private Integer count;
}
