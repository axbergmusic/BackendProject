package se.yrgo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import se.yrgo.dataaccess.SkinDao;
import se.yrgo.domain.Skin;
import se.yrgo.domain.enums.*;

@Service
@Transactional
public class SkinManagementServiceImpl implements SkinManagementService {

    @Autowired
    private SkinDao dao;

    @Override
    public void create(Skin skin) {
        dao.create(skin);
    }

    @Override
    public void update(Skin skin) {
        dao.update(skin);
    }

    @Override
    public void delete(Skin skin) {
        dao.delete(skin);
    }

    @Override
    public Skin getById(int id) {
        return dao.getById(id);
    }

    @Override
    public List<Skin> getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<Skin> getByType(Type type) {
        return dao.getByType(type);
    }

    @Override
    public List<Skin> getByRarity(Rarity rarity) {
        return dao.getByRarity(rarity);
    }

    @Override
    public List<Skin> getByCondition(Condition condition) {
        return dao.getByCondition(condition);
    }

    @Override
    public List<Skin> getAllSkins() {
        return dao.getAllSkins();
    }
}