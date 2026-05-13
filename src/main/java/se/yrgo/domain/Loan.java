package se.yrgo.domain;

import jakarta.persistence.*;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Skin skin;

    @ManyToOne
    private Player player;

    public Loan() {
    }

    public Loan(int id, Skin skin, Player player) {
        this.id = id;
        this.skin = skin;
        this.player = player;
    }

    public Loan(Skin skin, Player player) {
        this.skin = skin;
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Loan id " +id +" |" +" Skin " + skin  + player + " " + "";
    }
}