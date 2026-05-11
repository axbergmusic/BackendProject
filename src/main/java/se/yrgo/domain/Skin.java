package se.yrgo.domain;

import se.yrgo.domain.enums.*;

import jakarta.persistence.*;

@Entity
public class Skin {

    public static final String RESET = "\u001B[0m";
    public static final String WHITE = "\u001B[37m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[38;5;57m";
    public static final String PINK = "\u001B[95m";
    public static final String RED = "\u001B[31m";
    public static final String GOLD = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";

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

    private String rarityColor() {

        if (rarity == null) {
            return RESET;
        }

        switch (rarity) {

            case CONSUMER:
                return WHITE;

            case INDUSTRIAL:
                return CYAN;

            case MILSPEC:
                return BLUE;

            case RESTRICTED:
                return PURPLE;

            case CLASSIFIED:
                return PINK;

            case COVERT:
                return RED;

            case CONTRABAND:
                return GOLD;

            case GOLD:
                return GOLD;

            default:
                return RESET;
        }

    }

    @Override
    public String toString() {
        return "id = " + id + RESET +
                " [ " + rarityColor() + name + RESET +
                " | " + type + " | " +
                rarityColor() + rarity + RESET +
                " | " + WHITE + condition + RESET + " | " +
                GREEN + "$" + price + RESET + " ]";
    }
}