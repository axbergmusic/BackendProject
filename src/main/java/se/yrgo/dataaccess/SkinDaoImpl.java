package se.yrgo.dataaccess;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.*;
import se.yrgo.domain.Skin;
import se.yrgo.domain.enums.*;

@Repository
public class SkinDaoImpl implements SkinDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Skin skin) {
        em.persist(skin);
    }

    @Override
    public void update(Skin skin) {
        em.merge(skin);
    }

    @Override
    public void delete(Skin skin) {
        em.remove(em.contains(skin) ? skin : em.merge(skin));
    }


    @Override
    public Skin getById(int id) {
        return em.createQuery("from Skin as skin where skin.id = :id", Skin.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Skin> getByName(String name) {
        return em.createQuery("from Skin as skin where skin.name = :name", Skin.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Skin> getByType(Type type) {
        return em.createQuery("from Skin as skin where skin.type = :type", Skin.class)
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public List<Skin> getByRarity(Rarity rarity) {
        return em.createQuery("from Skin as skin where skin.rarity = :rarity", Skin.class)
                .setParameter("rarity", rarity)
                .getResultList();
    }

    @Override
    public List<Skin> getByCondition(Condition condition) {
        return em.createQuery("from Skin as skin where skin.condition = :condition", Skin.class)
                .setParameter("condition", condition)
                .getResultList();
    }

    @Override
    public List<Skin> getAllSkins() {
        return em.createQuery("from Skin as skin", Skin.class)
                .getResultList();
    }
}