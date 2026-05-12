package se.yrgo.dataaccess;

import java.util.List;

import se.yrgo.domain.*;
import se.yrgo.domain.enums.*;

public interface SkinDao {

    public void create(Skin skin);

    public void update(Skin skin);

    public void delete(Skin skin);

    public Skin getById(int id);

    public List<Skin> getAllSkins();
}