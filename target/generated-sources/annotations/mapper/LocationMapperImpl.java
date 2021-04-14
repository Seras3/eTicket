package mapper;

import dto.LocationDTO;
import javax.annotation.processing.Generated;
import model.Location;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-14T17:05:24+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
public class LocationMapperImpl implements LocationMapper {

    @Override
    public Location locationDtoToLocaion(LocationDTO record) {
        if ( record == null ) {
            return null;
        }

        Location location = new Location();

        location.setId( record.getId() );
        location.setCountry( record.getCountry() );
        location.setCity( record.getCity() );
        location.setDetails( record.getDetails() );

        return location;
    }

    @Override
    public LocationDTO locationToLocationDto(Location model) {
        if ( model == null ) {
            return null;
        }

        LocationDTO locationDTO = new LocationDTO();

        locationDTO.setId( model.getId() );
        locationDTO.setCountry( model.getCountry() );
        locationDTO.setCity( model.getCity() );
        locationDTO.setDetails( model.getDetails() );

        return locationDTO;
    }
}
