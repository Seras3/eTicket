package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class EventCompact{
    private Event event;
    private Location location;
    private EventCategory category;
    private EventLocation eventLocation;
}
