package se.yrgo.services;

import java.util.List;

import se.yrgo.domain.*;

public interface LoanManagementService {

    public void create(Loan loan);

    public void update(Loan loan);

    public void delete(Loan loan);

    public List<Loan> getByPlayer(int id);

    public List<Loan> getAllLoans();
}