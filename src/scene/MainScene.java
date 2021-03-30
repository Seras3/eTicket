package scene;

import graphic.GUI;

public class MainScene extends Scene{

    public MainScene() {
        super("Main Scene");
    }

    public void run() {
        GUI.intro();
        new AuthScene().run();
        GUI.exit();
    }
}
