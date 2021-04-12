package mapper;

import dto.EventDTO;
import dto.EventRowDTO;
import model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper( EventMapper.class );

    Event fromEventDTO(EventDTO record);
    Event fromEventRowDTO(EventRowDTO record);

    EventDTO toEventDTO(Event model);
    EventRowDTO toEventRowDTO(Event model);
}