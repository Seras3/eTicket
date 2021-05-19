package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private Integer id;
    private Integer accountId;
    private String route;
    private LocalDateTime createdAt;

    public Log(Integer accountId, String route) {
        this.accountId = accountId;
        this.route = route;
    }
}
