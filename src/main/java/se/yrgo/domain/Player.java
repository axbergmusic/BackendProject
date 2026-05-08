package se.yrgo.domain;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Loan> loans;

    public Player() {
    }

    public Player(int id, String name, List<Loan> loans) {
        this.id = id;
        this.name = name;
        this.loans = loans;
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

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    @Override
    public String toString() {
        return "Player [id=" + id + ", name=" + name + ", loans=" + loans + "]";
    }
}