package se.yrgo.dataaccess;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.*;
import se.yrgo.domain.Player;

@Repository
public class PlayerDaoImpl implements PlayerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Player player) {
        em.persist(player);
    }

    @Override
    public void update(Player player) {
        em.merge(player);
    }

    @Override
    public void delete(Player player) {
        em.remove(em.contains(player) ? player : em.merge(player));
    }



    @Override
    public Player getById(int id) {
        return em.createQuery("from Player as player where player.id = :id", Player.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Player> getAllPlayers() {
        return em.createQuery("select player from Player as player", Player.class)
                .getResultList();
    }
}