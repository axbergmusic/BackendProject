package se.yrgo.services;

import java.util.List;

import se.yrgo.domain.*;

public interface PlayerManagementService {

    public void create(Player player);

    public void update(Player player) throws PlayerNotFoundException;

    public void delete(Player player) throws PlayerNotFoundException;

    public Player getById(int id) throws PlayerNotFoundException;

    public List<Player> getAllPlayers();
}