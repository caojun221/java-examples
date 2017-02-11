package io.caojun221.examples.hibernate;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GetStarted {

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

    @SuppressWarnings("unchecked")
    @Test
    public void nativeHibernateAPI() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(new Item("a"));
        session.save(new Item("b"));
        session.getTransaction().commit();

        session.getTransaction().begin();
        List itemList = (List<Item>)session.createQuery("from items").list();
        itemList.forEach(System.out::println);
        session.getTransaction().commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void jpaAPI() {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new Item("a"));
        entityManager.persist(new Item("b"));
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        List<Item> itemList = entityManager.createQuery("from items", Item.class).getResultList();
        itemList.forEach(System.out::println);
        entityManager.getTransaction().commit();

        AuditReader reader = AuditReaderFactory.get(entityManager);
        Item firstRevision = reader.find(Item.class, 1, 1);
        System.out.println(firstRevision);
        entityManager.close();
    }
}
