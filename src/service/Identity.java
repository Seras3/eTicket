package service;


public class Identity {
    private String id;
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

    public Identity(String id, String email, int role) {
        this.id = id;
        this.email = email;
        switch(role) {
            case 0:
                this.role = Role.USER;
                break;
            case 1:
                this.role = Role.ADMIN;
                break;

        }
    }


    public String getId() { return id; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    // public String getToken() { return token; }
}
