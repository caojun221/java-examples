package io.caojun221.examples.mybatis;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GetStartedTest {

    private static SqlSessionFactory factory;
    private static final JdbcConnectionPool DATA_SOURCE = JdbcConnectionPool.create("jdbc:h2:mem:test", "sa",
                                                                                    "sa");

    @BeforeClass
    public static void setup() throws SQLException {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Connection connection = DATA_SOURCE.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE item (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255), PRIMARY KEY (id))");
        connection.commit();
        connection.close();

        Environment environment = new Environment("development", transactionFactory, DATA_SOURCE);
        Configuration configuration = new Configuration(environment);
        configuration.getTypeAliasRegistry().registerAlias(Item.class);
        configuration.addMapper(ItemMapper.class);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        factory = builder.build(configuration);

    }

    @AfterClass
    public static void tearDown() {
        DATA_SOURCE.dispose();
    }

    @Test
    public void test() throws SQLException {
        try (SqlSession session = factory.openSession()) {
            ItemMapper itemMapper = session.getMapper(ItemMapper.class);
            Item item = new Item();
            item.setName("item_1");
            itemMapper.insertItem(item);
            System.out.println(item);
            Item selected = itemMapper.selectItem(1);
            System.out.println(selected);
        }
    }
}
