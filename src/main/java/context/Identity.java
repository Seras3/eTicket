package context;


public class Identity {
    private Integer id;
    private String email;
    private Role role;
    // private String token;

    public enum Role {
        ADMIN,
        USER
    }

    public Identity(Identity identity) {
        this.id = identity.id;
        this.email = identity.email;
        this.role = identity.role;
        // this.token = user.token;
    }

    public Identity(Integer id, String email, int role) {
        this.id = id;
        this.email = email;
        switch (role) {
            case 1 -> this.role = Role.ADMIN;
            case 2 -> this.role = Role.USER;
        }
    }


    public Integer getId() { return id; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    // public String getToken() { return token; }
}
