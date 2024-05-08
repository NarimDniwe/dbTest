import db.DbController;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DbController controller = new DbController();
        ArrayList<String> user = controller.readUser(3);
        System.out.println(user);

    }
}
