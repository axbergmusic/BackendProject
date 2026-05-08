package se.yrgo.services;

import java.util.List;

import se.yrgo.domain.*;
import se.yrgo.domain.enums.*;

public interface SkinManagementImplementation {

    public void create(Skin skin);

    public void update(Skin skin);

    public void delete(Skin skin);

    public Skin getById(int id);

    public List<Skin> getByName(int id);

    public List<Skin> getByType(Type type);

    public List<Skin> getByRarity(Rarity rarity);

    public List<Skin> getByCondition(Condition condition);

    public List<Skin> getAllSkins();
}