package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCompactDTO {
    private EventDTO event;
    private LocationDTO location;
    private EventCategoryDTO category;
    private EventLocationDTO eventLocation;
}
