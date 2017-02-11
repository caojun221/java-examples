package io.caojun221.examples.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.envers.Audited;

@Audited
@Entity(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    public Item() {}

    public Item(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
