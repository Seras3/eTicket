package mapper;

import dto.EventCompactDTO;
import dto.EventDTO;
import dto.EventLocationDTO;
import dto.EventRowDTO;
import model.Event;
import model.EventCompact;
import model.EventLocation;
import model.EventRow;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper( EventMapper.class );

    Event eventDtoToEvent(EventDTO record);
    EventRow eventRowDtoToEventRow(EventRowDTO record);
    EventCompact eventCompactDtoToEventCompact(EventCompactDTO record);
    EventLocation eventLocationDtoToEventLocation(EventLocationDTO record);


    EventDTO eventToEventDto(Event model);
    EventRowDTO eventRowToEventRowDto(EventRow model);
    EventCompactDTO eventCompactToEventCompactDto(EventCompact model);
    EventLocationDTO eventLocationToEventLocationDto(EventLocation model);
}