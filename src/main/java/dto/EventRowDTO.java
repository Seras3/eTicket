package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRowDTO {
    private Integer id;
    private String name;
    private String description;
    private String category;
    private String country;
    private String city;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
