package service;

public class Context {
    private static Identity user;

    public static void setIdentity(Identity user) {
        Context.user = new Identity(user);
    }

    public static Identity getIdentity() { return user; }

}
