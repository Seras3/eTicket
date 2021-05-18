
import graphic.GUI;
import service.API;
import context.Identity;


public class Test {
    public static void main(String[] args) {
        API api = new API(new Identity(2, "cineva", 2));

        GUI.showCart(api.getCart());

    }
}
