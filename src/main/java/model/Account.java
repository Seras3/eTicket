package model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Integer id;
    private String email;
    private String password;
    private Integer roleId;

    public Account(Account ob) {
        this.id = ob.getId();
        this.email = ob.getEmail();
        this.password = ob.getPassword();
        this.roleId = ob.getRoleId();
    }

    public Account(String email, String password, Integer roleId) {
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }
}
