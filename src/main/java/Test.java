import dao.AccountDAO;
import dao.EventDAO;
import dto.EventDTO;
import mapper.AccountMapper;
import mapper.EventMapper;
import model.Account;
import model.Event;
import service.API;
import service.Identity;


public class Test {
    public static void main(String[] args) {
        API api = new API(new Identity(1, "cineva", 1));
        //System.out.println(api.getEvents());

    }
}
