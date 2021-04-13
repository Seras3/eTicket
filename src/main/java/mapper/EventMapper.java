package mapper;

import dto.EventDTO;
import dto.EventRowDTO;
import model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper( EventMapper.class );

    Event eventDtoToEvent(EventDTO record);
    Event eventRowDtoToEvent(EventRowDTO record);

    EventDTO eventToEventDto(Event model);
    EventRowDTO eventToEventRowDto(Event model);
}