package scene;


import graphic.GUI;
import dto.AccountAuthDTO;
import service.AuthService;
import context.Context;


public class LoginScene extends Scene{
    private AuthService auth_service;

    public LoginScene () {
        super("Login");
        auth_service = new AuthService();
    }

    public void run() {
        GUI.sceneTitle(this.title);

        AccountAuthDTO inputAccount = GUI.getAuth();
        AuthService.Result result = auth_service.login(inputAccount);
        GUI.authResponse(auth_service.login(inputAccount));

        if(result == AuthService.Result.LOGIN_SUCCESS) {
            GUI.welcome(Context.getIdentity().getEmail());
            new MenuScene().run();
        }
    }
}
