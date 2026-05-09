package se.yrgo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import se.yrgo.dataaccess.PlayerDao;
import se.yrgo.domain.Player;

@Service
@Transactional
public class PlayerManagementServiceImpl implements PlayerManagementService {

    @Autowired
    private PlayerDao dao;

    @Override
    public void create(Player player) {
        dao.create(player);
    }

    @Override
    public void update(Player player) {
        dao.update(player);
    }

    @Override
    public void delete(Player player) {
        dao.delete(player);
    }

    @Override
    public Player getById(int id) {
        return dao.getById(id);
    }

    @Override
    public List<Player> getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public List<Player> getAllPlayers() {
        return dao.getAllPlayers();
    }
}