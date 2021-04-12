package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Integer id;
    private String name;
    private String description;
    private Integer category_id;


    public Event(String name, String description, Integer category_id) {
        this.name = name;
        this.description = description;
        this.category_id = category_id;
    }
}