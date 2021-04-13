package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRowDTO {
    private String name;
    private String description;
    private Float price;
    private boolean seat;
    private Integer count;
}
