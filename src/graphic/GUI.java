package graphic;

import dto.AccountAuthDTO;
import service.AuthService;
import util.Command;

import java.util.*;

public class GUI {
    private static Scanner scan = new Scanner(System.in);
    private static int command_align = 20;

    public static void sceneTitle(String title) {
        System.out.println("~~~~~ " + title + " ~~~~~");
    }

    public static void intro() {
        System.out.println("Welcome to eTicket !\n");
        System.out.println("We're glad to have you here.");
    }

    public static void authentication() {
        System.out.println("Get comfortable and create an account or just log in.");
    }

    public static void enterCommand() {
        System.out.println("Enter command:");
    }

    public static void help(HashMap<String, Command> commands) {
        List<Command> display_list = new ArrayList(commands.values());
        Collections.sort(display_list);
        display_list.forEach(obj -> System.out.println(GUI.commandToString(obj)));
    }

    public static String commandToString(Command command) {
        int align_offset = GUI.command_align - command.getName().length();
        return command.getName() + " ".repeat(align_offset) + " - " + command.getDescription();
    }

    public static void exit() {
        System.out.println("See you soon! :D");
    }

    public static void invalidCommand() {
        System.out.println("Command is invalid, please try again.");
    }

    public static void exception(Exception e) {
        System.out.println(e.getMessage());
    }

    public static void authResponse(AuthService.Result auth_result) {
        switch(auth_result) {
            case REGISTER_SUCCESS:
                System.out.println("Your account has been created.");
                break;
            case LOGIN_SUCCESS:
                System.out.println("Successfully login.");
                break;
            case NOT_FOUND:
                System.out.println("E-mail or password incorrect.");
                break;
            case USER_EXISTS:
                System.out.println("You already have an account linked with this e-mail.");
                break;
        }
    }

    public static void welcome(String user) {
        System.out.println(String.format("Hello, %s! :D", user));
    }

    public static String getCommand() {
        enterCommand();
        return scan.nextLine();
    }

    public static AccountAuthDTO getAuth() {
        String email, password;

        System.out.print("e-mail: ");
        email = scan.nextLine();

        System.out.print("password: ");
        password = scan.nextLine();

        return new AccountAuthDTO(email, password);
    }
}
