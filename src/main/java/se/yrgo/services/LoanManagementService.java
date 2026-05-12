package se.yrgo.services;

import java.util.List;

import se.yrgo.domain.*;

public interface LoanManagementService {

    public void create(Loan loan);

    public void update(Loan loan) throws LoanNotFoundException;

    public void delete(Loan loan) throws LoanNotFoundException;

    public List<Loan> getAllLoans();
}