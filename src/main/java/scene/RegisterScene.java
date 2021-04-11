package scene;

import graphic.GUI;
import dto.AccountAuthDTO;
import service.AuthService;


public class RegisterScene extends Scene {
    private AuthService auth_service;

    public RegisterScene () {
        super("Register");
        auth_service = new AuthService();
    }

    public void run() {
        GUI.sceneTitle(title);
        AccountAuthDTO input_account = GUI.getAuth();
        GUI.authResponse(auth_service.register(input_account));
    }
}
