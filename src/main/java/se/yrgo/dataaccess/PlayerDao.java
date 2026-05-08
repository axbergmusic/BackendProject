package se.yrgo.dataaccess;

import java.util.List;

import se.yrgo.domain.*;

public interface PlayerDao {

    public void create(Skin skin);

    public void update(Skin skin);

    public void delete(Skin skin);

    public Player getById(int id);

    public List<Player> getByName(String name);

    public List<Player> getAllPlayers();
}