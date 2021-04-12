package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventLocation {
    private Integer id;
    private Integer event_id;
    private Integer location_id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
}
