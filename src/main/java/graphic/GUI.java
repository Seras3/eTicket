package graphic;

import dto.AccountAuthDTO;
import dto.EventDTO;
import dto.EventRowDTO;
import service.API;
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

    public static void invalidCommandParameter(String parameter) {
        System.out.println("Parameter " + parameter + " is invalid.");
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

    public static void apiResponse(API.Result api_result) {
        switch(api_result) {
            case OK:
                System.out.println("Command successfully executed.");
                break;
            case NOT_FOUND:
                System.out.println("Resource not found.");
                break;
            case FAIL:
                System.out.println("Command failed.");
                break;
        }
    }

    public static void welcome(String user) {
        System.out.println(String.format("Hello, %s! :D", user));
    }

    public static void eventsList(List<EventRowDTO> events) {
        for(EventRowDTO event : events) {
            System.out.println("~~~~~~~~~~~~~~~~~ ("+ event.getId() +") " + event.getName() + " ~~~~~~~~~~~~~~~~~");
            System.out.println(event.getDescription());
            System.out.println();
        }
    }

    public static void eventShow(EventDTO event) {
        System.out.println("~~~~~~~~ " + event.getName() + " ~~~~~~~~");
        System.out.println(event.getDescription());
        System.out.println();
    }

    public static boolean eventDelete(EventDTO event) {
        System.out.println("Are you sure you want to delete "+event.getName() + "? (Y/N)");
        return scan.nextLine().equals("Y");
    }

    public static void eventNotFound() {
        System.out.println("Event not found.");
    }

    public static EventDTO getEvent(EventDTO event) {
        System.out.print("Name: ");
        event.setName(scan.nextLine());
        System.out.print("Description: ");
        event.setDescription(scan.nextLine());
        System.out.print("Category id: ");
        event.setCategory_id(Integer.valueOf(scan.nextLine()));
        return event;
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
