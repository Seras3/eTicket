package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer categoryId;

    public EventDTO(EventDTO event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.categoryId = event.getCategoryId();
    }
}
