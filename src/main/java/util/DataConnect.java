package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ArrayBlockingQueue;

public class DataConnect {
    private final static String DBURL = "jdbc:mysql://127.0.0.1:3306/";
    private final static String DBUSER = "error-reporting-portal";
    private final static String DBPASS = "12345678";
    private final static String DBDRIVER = "com.mysql.cj.jdbc.Driver";

    private static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
    public static Connection getConnection() {
        try {
            queue.put(1);
        } catch (InterruptedException e) {
            System.out.println("ArrayBlockingQueue Error while putting; DataConnect.getConnection() --> " + e.getMessage());
        }
        try {
            Class.forName(DBDRIVER);
            return DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (Exception ex) {
            System.out.println("Opening DB connection Error; DataConnect.getConnection() --> " + ex.getMessage());
            try {
                queue.take();
            } catch (InterruptedException e) {
                System.out.println("ArrayBlockingQueue Error while taking; DataConnect.getConnection() --> " + e.getMessage());
            }
            return null;
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
            System.out.println("Connection close Error; DataConnect.close() --> " + ex.getMessage());
        }
        try {
            queue.take();
        } catch (InterruptedException e) {
            System.out.println("ArrayBlockingQueue Error; DataConnect.close() --> " + e.getMessage());
        }
    }
}
