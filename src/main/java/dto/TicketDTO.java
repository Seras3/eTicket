package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Integer id;
    private String name;
    private String description;
    private Float price;
    private SeatDTO seat;
}
