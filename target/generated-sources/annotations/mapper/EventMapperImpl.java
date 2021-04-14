package mapper;

import dto.EventCategoryDTO;
import dto.EventCompactDTO;
import dto.EventDTO;
import dto.EventLocationDTO;
import dto.EventRowDTO;
import dto.LocationDTO;
import javax.annotation.processing.Generated;
import model.Event;
import model.EventCategory;
import model.EventCompact;
import model.EventLocation;
import model.EventRow;
import model.Location;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-14T17:05:24+0300",
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
    public EventRow eventRowDtoToEventRow(EventRowDTO record) {
        if ( record == null ) {
            return null;
        }

        EventRow eventRow = new EventRow();

        eventRow.setId( record.getId() );
        eventRow.setName( record.getName() );
        eventRow.setDescription( record.getDescription() );
        eventRow.setCategory( record.getCategory() );
        eventRow.setCountry( record.getCountry() );
        eventRow.setCity( record.getCity() );
        eventRow.setStartDate( record.getStartDate() );
        eventRow.setEndDate( record.getEndDate() );

        return eventRow;
    }

    @Override
    public EventCompact eventCompactDtoToEventCompact(EventCompactDTO record) {
        if ( record == null ) {
            return null;
        }

        EventCompact eventCompact = new EventCompact();

        eventCompact.setEvent( eventDtoToEvent( record.getEvent() ) );
        eventCompact.setLocation( locationDTOToLocation( record.getLocation() ) );
        eventCompact.setCategory( eventCategoryDTOToEventCategory( record.getCategory() ) );
        eventCompact.setEventLocation( eventLocationDtoToEventLocation( record.getEventLocation() ) );

        return eventCompact;
    }

    @Override
    public EventLocation eventLocationDtoToEventLocation(EventLocationDTO record) {
        if ( record == null ) {
            return null;
        }

        EventLocation eventLocation = new EventLocation();

        eventLocation.setId( record.getId() );
        eventLocation.setEventId( record.getEventId() );
        eventLocation.setLocationId( record.getLocationId() );
        eventLocation.setStartDate( record.getStartDate() );
        eventLocation.setEndDate( record.getEndDate() );

        return eventLocation;
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
    public EventRowDTO eventRowToEventRowDto(EventRow model) {
        if ( model == null ) {
            return null;
        }

        EventRowDTO eventRowDTO = new EventRowDTO();

        eventRowDTO.setId( model.getId() );
        eventRowDTO.setName( model.getName() );
        eventRowDTO.setDescription( model.getDescription() );
        eventRowDTO.setCategory( model.getCategory() );
        eventRowDTO.setCountry( model.getCountry() );
        eventRowDTO.setCity( model.getCity() );
        eventRowDTO.setStartDate( model.getStartDate() );
        eventRowDTO.setEndDate( model.getEndDate() );

        return eventRowDTO;
    }

    @Override
    public EventCompactDTO eventCompactToEventCompactDto(EventCompact model) {
        if ( model == null ) {
            return null;
        }

        EventCompactDTO eventCompactDTO = new EventCompactDTO();

        eventCompactDTO.setEvent( eventToEventDto( model.getEvent() ) );
        eventCompactDTO.setLocation( locationToLocationDTO( model.getLocation() ) );
        eventCompactDTO.setCategory( eventCategoryToEventCategoryDTO( model.getCategory() ) );
        eventCompactDTO.setEventLocation( eventLocationToEventLocationDto( model.getEventLocation() ) );

        return eventCompactDTO;
    }

    @Override
    public EventLocationDTO eventLocationToEventLocationDto(EventLocation model) {
        if ( model == null ) {
            return null;
        }

        EventLocationDTO eventLocationDTO = new EventLocationDTO();

        eventLocationDTO.setId( model.getId() );
        eventLocationDTO.setEventId( model.getEventId() );
        eventLocationDTO.setLocationId( model.getLocationId() );
        eventLocationDTO.setStartDate( model.getStartDate() );
        eventLocationDTO.setEndDate( model.getEndDate() );

        return eventLocationDTO;
    }

    protected Location locationDTOToLocation(LocationDTO locationDTO) {
        if ( locationDTO == null ) {
            return null;
        }

        Location location = new Location();

        location.setId( locationDTO.getId() );
        location.setCountry( locationDTO.getCountry() );
        location.setCity( locationDTO.getCity() );
        location.setDetails( locationDTO.getDetails() );

        return location;
    }

    protected EventCategory eventCategoryDTOToEventCategory(EventCategoryDTO eventCategoryDTO) {
        if ( eventCategoryDTO == null ) {
            return null;
        }

        EventCategory eventCategory = new EventCategory();

        eventCategory.setId( eventCategoryDTO.getId() );
        eventCategory.setName( eventCategoryDTO.getName() );

        return eventCategory;
    }

    protected LocationDTO locationToLocationDTO(Location location) {
        if ( location == null ) {
            return null;
        }

        LocationDTO locationDTO = new LocationDTO();

        locationDTO.setId( location.getId() );
        locationDTO.setCountry( location.getCountry() );
        locationDTO.setCity( location.getCity() );
        locationDTO.setDetails( location.getDetails() );

        return locationDTO;
    }

    protected EventCategoryDTO eventCategoryToEventCategoryDTO(EventCategory eventCategory) {
        if ( eventCategory == null ) {
            return null;
        }

        EventCategoryDTO eventCategoryDTO = new EventCategoryDTO();

        eventCategoryDTO.setId( eventCategory.getId() );
        eventCategoryDTO.setName( eventCategory.getName() );

        return eventCategoryDTO;
    }
}
