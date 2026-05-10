package se.yrgo.services;

import java.util.List;

import se.yrgo.domain.*;
import se.yrgo.domain.enums.*;

public interface SkinManagementService {

    public void create(Skin skin);

    public void update(Skin skin) throws SkinNotFoundException;

    public void delete(Skin skin) throws SkinNotFoundException;

    public Skin getById(int id) throws SkinNotFoundException;

    public List<Skin> getByName(String name) throws SkinNotFoundException;

    public List<Skin> getByType(Type type) throws SkinNotFoundException;

    public List<Skin> getByRarity(Rarity rarity) throws SkinNotFoundException;

    public List<Skin> getByCondition(Condition condition) throws SkinNotFoundException;

    public List<Skin> getAllSkins();
}