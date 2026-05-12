package se.yrgo.integrationtests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import se.yrgo.domain.Player;
import se.yrgo.services.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/application.xml")
@Transactional
public class ManagingPlayersIntegrationTest {

    @Autowired
    private PlayerManagementService players;

    @Test
    public void testGettingById() {

        Player testPlayer = new Player("s1mple", null);
        players.create(testPlayer);

        Player foundPlayer = null;

        try {
            foundPlayer = players.getById(testPlayer.getId());
            assertEquals(testPlayer, foundPlayer);
        } catch (PlayerNotFoundException e) {
            fail("No player was found when one should have been!");
        }
    }

    @Test
    public void testAddingPlayer() {
        players.create(new Player("s1mple", null));

        int playersInDb = players.getAllPlayers().size();
        assertEquals(1, playersInDb);
    }
}