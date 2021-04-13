package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class EventCompact{
    private Event event;
    private Location location;
    private List<Ticket> tickets;
    private EventCategory category;
    private EventLocation eventLocation;
}
