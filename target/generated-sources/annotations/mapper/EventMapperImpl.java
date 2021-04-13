package mapper;

import dto.EventDTO;
import dto.EventRowDTO;
import javax.annotation.processing.Generated;
import model.Event;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-13T23:03:17+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
public class EventMapperImpl implements EventMapper {

    @Override
    public Event eventDtoToEvent(EventDTO record) {
        if ( record == null ) {
            return null;
        }

        Event event = new Event();

        event.setId( record.getId() );
        event.setName( record.getName() );
        event.setDescription( record.getDescription() );
        event.setCategoryId( record.getCategoryId() );

        return event;
    }

    @Override
    public Event eventRowDtoToEvent(EventRowDTO record) {
        if ( record == null ) {
            return null;
        }

        Event event = new Event();

        event.setId( record.getId() );
        event.setName( record.getName() );
        event.setDescription( record.getDescription() );

        return event;
    }

    @Override
    public EventDTO eventToEventDto(Event model) {
        if ( model == null ) {
            return null;
        }

        EventDTO eventDTO = new EventDTO();

        eventDTO.setId( model.getId() );
        eventDTO.setName( model.getName() );
        eventDTO.setDescription( model.getDescription() );
        eventDTO.setCategoryId( model.getCategoryId() );

        return eventDTO;
    }

    @Override
    public EventRowDTO eventToEventRowDto(Event model) {
        if ( model == null ) {
            return null;
        }

        EventRowDTO eventRowDTO = new EventRowDTO();

        eventRowDTO.setId( model.getId() );
        eventRowDTO.setName( model.getName() );
        eventRowDTO.setDescription( model.getDescription() );

        return eventRowDTO;
    }
}
