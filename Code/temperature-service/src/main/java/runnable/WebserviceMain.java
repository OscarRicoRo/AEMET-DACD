package runnable;

import controller.Controller;

import java.io.File;
import java.sql.SQLException;

public class WebserviceMain {

    private final static File rootDatamart = new File("datamart/");

    public static void main(String[] args) throws SQLException, InterruptedException {
        new Controller(rootDatamart).run();
    }
}
