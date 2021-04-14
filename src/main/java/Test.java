
import graphic.GUI;
import service.API;
import context.Identity;


public class Test {
    public static void main(String[] args) {
        API api = new API(new Identity(1, "cineva", 1));

        System.out.println(GUI.getEventLocation());

    }
}
