package model;

import java.util.Objects;

public class Account implements Cloneable{
    private String id;
    private String email;
    private String password;
    private int role;

    public Account(Account ob) {
        this.id = ob.id();
        this.email = ob.email();
        this.password = ob.password();
        this.role = ob.role();
    }

    public Account(String id, String email, String password, int role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public int role() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Account) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.password, that.password) &&
                this.role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, role);
    }

    @Override
    public String toString() {
        return "Account[" +
                "id=" + id + ", " +
                "email=" + email + ", " +
                "password=" + password + ", " +
                "role=" + role + ']';
    }
}
