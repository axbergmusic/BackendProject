package se.yrgo.domain;

import se.yrgo.domain.enums.*;

import jakarta.persistence.*;

@Entity
public class Skin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private boolean loaned = false;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    private double price;

    public Skin() {
    }

    public Skin(int id, String name, Type type, Rarity rarity, Condition condition, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.condition = condition;
        this.price = price;
    }

    public boolean isLoaned() {
        return loaned;
    }

    public void setLoaned(boolean loaned) {
        this.loaned = loaned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Skin [id=" + id + ", name=" + name + ", type=" + type + ", rarity=" + rarity + ", condition="
                + condition + ", price=" + price + "]";
    }
}