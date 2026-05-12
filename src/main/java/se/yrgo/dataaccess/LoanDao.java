package se.yrgo.dataaccess;

import java.util.List;

import se.yrgo.domain.*;

public interface LoanDao {

    public void create(Loan loan);

    public void update(Loan loan);

    public void delete(Loan loan);

    public List<Loan> getAllLoans();
}