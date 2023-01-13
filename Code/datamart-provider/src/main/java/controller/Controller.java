package controller;

import datalake.DatalakeReader;
import datalake.DatalakeFileReader;
import datalake.POJO.Weather;
import datamart.Database;
import datamart.SQLiteDatabase;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Controller {

    private final Database database;
    private final DatalakeReader datalake;

    public Controller(File rootDatalake, File rootDatabase) throws SQLException {
        database = new SQLiteDatabase(rootDatabase);
        datalake = new DatalakeFileReader(rootDatalake);
    }

    public void run() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    execute();
                } catch (SQLException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 3600 * 1000);
    }

    private void execute() throws SQLException, InterruptedException {
        System.out.println("Datamart Started " + LocalDateTime.now());
        TimeUnit.SECONDS.sleep(9);
        List<Weather> maxList = datalake.readMax();
        List<Weather> minList = datalake.readMin();
        database.reload();
        database.storeMax(maxList);
        database.storeMin(minList);
        System.out.println("Datamart Endend " + LocalDateTime.now());
    }
}
