package se.yrgo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import se.yrgo.dataaccess.LoanDao;
import se.yrgo.domain.Loan;

@Service
@Transactional
public class LoanManagementServiceImpl implements LoanManagementService {

    @Autowired
    private LoanDao dao;

    @Override
    public void create(Loan loan) {
        dao.create(loan);
    }

    @Override
    public void update(Loan loan) {
        dao.update(loan);
    }

    @Override
    public void delete(Loan loan) {
        dao.delete(loan);
    }

    @Override
    public List<Loan> getByPlayer(int id) {
        return dao.getByPlayer(id);
    }

    @Override
    public List<Loan> getAllLoans() {
        return dao.getAllLoans();
    }
}