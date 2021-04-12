package service;


import dao.EventDAO;
import dto.EventDTO;
import dto.EventRowDTO;
import mapper.EventMapper;
import model.Event;

import java.util.ArrayList;
import java.util.List;

public class API {
    private Identity identity;
    private EventDAO event_dao;

    public API(Identity identity) {
        this.identity = identity;
        this.event_dao = new EventDAO();
    }

    public enum Result {
        OK,
        FAIL,
        NOT_FOUND
    }



    public List<EventRowDTO> getEvents() {
        List<EventRowDTO> events_dto_list = new ArrayList<EventRowDTO>();
        for(Event event : event_dao.getAll()) {
            events_dto_list.add(EventMapper.INSTANCE.toEventRowDTO(event));
        }
        return events_dto_list;
    }

    public EventDTO getEvent(Integer id) {
        return EventMapper.INSTANCE.toEventDTO(event_dao.get(id.toString()));
    }

    public Result postEvent(EventDTO event) {
        event_dao.add(EventMapper.INSTANCE.fromEventDTO(event));
        return Result.OK;
    }

    public Result putEvent(EventDTO event) {
        event_dao.update(EventMapper.INSTANCE.fromEventDTO(event));
        return Result.OK;
    }

    public Result deleteEvent(Integer id) {
        EventDTO event = new EventDTO();
        event.setId(id);
        event_dao.delete(EventMapper.INSTANCE.fromEventDTO(event));
        return Result.OK;
    }
}
