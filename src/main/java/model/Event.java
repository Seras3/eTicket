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
    private Integer categoryId;


    public Event(String name, String description, Integer categoryId) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
    }
}