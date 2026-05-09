package se.yrgo.dataaccess;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.*;
import se.yrgo.domain.Loan;

@Repository
public class LoanDaoImpl implements LoanDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Loan loan) {
        em.persist(loan);
    }

    @Override
    public void update(Loan loan) {
        em.merge(loan);
    }

    @Override
    public void delete(Loan loan) {
        em.remove(loan);
    }

    @Override
    public List<Loan> getByPlayer(int id) {
        return em.createQuery("from Loan as loan where loan.player = :player", Loan.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Loan> getAllLoans() {
        return em.createQuery("from Loan as loan", Loan.class)
                .getResultList();
    }
}