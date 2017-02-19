package io.caojun221.examples.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class ConnectionTest {
    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:mem:test";
//    private static final String DRIVER = "com.mysql.jdbc.Driver";
//    private static final String URL = "jdbc:mysql://localhost:20306/test";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, "root", "");
    }

    @Test
    public void testTransaction() throws Exception {
        Connection conn = createConnection();
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE IF EXISTS tb1");
        statement.execute(
                "CREATE TABLE IF NOT EXISTS tb1 (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255), PRIMARY KEY(id))");

        Connection conn1 = createConnection();
        conn1.setAutoCommit(false);
        Statement insert = conn1.createStatement();
        insert.execute("INSERT INTO tb1 (name) VALUES ('item_1')");

        Connection conn2 = createConnection();
        Statement select = conn2.createStatement();
        ResultSet resultSet = select.executeQuery("SELECT * FROM tb1 WHERE name='item_1'");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(2));
        }

        select = conn1.createStatement();
        resultSet = select.executeQuery("SELECT * FROM tb1 WHERE name='item_1'");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(2));
        }

        conn1.commit();
        select = conn2.createStatement();
        resultSet = select.executeQuery("SELECT * FROM tb1 WHERE name='item_1'");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(2));
        }

        conn.close();
    }
}
