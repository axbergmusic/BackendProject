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
        if (loan.getPlayer() != null) {
            loan.setPlayer(em.merge(loan.getPlayer()));
        }
        if (loan.getSkin() != null) {
            loan.setSkin(em.merge(loan.getSkin()));
        }
        em.persist(loan);
    }
    @Override
    public void update(Loan loan) {
        em.merge(loan);
    }

    @Override
    public void delete(Loan loan) {
        em.remove(em.contains(loan) ? loan : em.merge(loan)); }

    @Override
    public List<Loan> getAllLoans() {
        return em.createQuery("from Loan as loan", Loan.class)
                .getResultList();
    }
}