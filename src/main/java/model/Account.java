package model;

import lombok.Data;

@Data
public class Account {
    private Long id;
    private String email;
    private String password;
    private Integer role_id;

    public Account(Account ob) {
        this.id = ob.getId();
        this.email = ob.getEmail();
        this.password = ob.getPassword();
        this.role_id = ob.getRole_id();
    }

    public Account(String email, String password, Integer role_id) {
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    public Account(Long id, String email, String password, Integer role_id) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    public Account() { }
}
