package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventLocationDTO {
    private Integer id;
    private Integer eventId;
    private Integer locationId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
