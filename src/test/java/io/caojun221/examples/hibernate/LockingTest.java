package io.caojun221.examples.hibernate;

import java.util.List;

import javax.persistence.LockModeType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LockingTest {

    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void setup() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySetting(AvailableSettings.HBM2DDL_AUTO, "create-drop")
                .applySetting(AvailableSettings.DRIVER, "org.h2.Driver")
                .applySetting(AvailableSettings.DIALECT, "org.hibernate.dialect.H2Dialect")
                .applySetting(AvailableSettings.URL, "jdbc:h2:mem:test")
                .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Item.class)
                .getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    @AfterClass
    public static void tearDown() {
        sessionFactory.close();
    }

    @Test
    public void test() {
        Session session1 = sessionFactory.openSession();
        session1.beginTransaction();
        session1.save(new Item("item_1"));
        session1.getTransaction().commit();
        session1.close();

        Session session2 = sessionFactory.openSession();
        session2.beginTransaction();
        List itemsSession2 = session2.createQuery("FROM items WHERE id = 1").setLockMode(LockModeType.OPTIMISTIC).list();
        Item itemSession2 = (Item) itemsSession2.get(0);


        Session session3 = sessionFactory.openSession();
        session3.beginTransaction();
        List itemsSession3 = session3.createQuery("FROM items WHERE id = 1").list();
        Item itemSession3 = (Item) itemsSession3.get(0);
        itemSession3.setName("item_3");
        session3.save(itemSession3);
        session3.getTransaction().commit();
        session3.close();

        itemSession2.setName("item_2");
        session2.save(itemSession2);
        session2.getTransaction().commit();
        session2.close();

        Session session4 = sessionFactory.openSession();
        session4.beginTransaction();
        List itemsSession4 = session4.createQuery("FROM items WHERE id = 1").list();
        Item itemSession4 = (Item) itemsSession4.get(0);
        System.out.println(itemSession4);
        session4.close();
    }
}
