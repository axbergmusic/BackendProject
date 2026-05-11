package se.yrgo.dataaccess;

import java.util.List;

import se.yrgo.domain.*;

public interface PlayerDao {

    public void create(Player player);

    public void update(Player player);

    public void delete(Player player);

    public Player getById(int id);

    public List<Player> getByName(String name);

    public List<Player> getAllPlayerNames();

    public List<Player> getAllPlayers();
}