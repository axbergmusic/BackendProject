package se.yrgo.integrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import se.yrgo.domain.Skin;
import se.yrgo.domain.enums.*;
import se.yrgo.services.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/application.xml")
@Transactional
public class ManagingSkinsIntegrationTest {

    @Autowired
    private SkinManagementService skins;

    @Test
    public void testGettingById() {

        Skin testSkin = new Skin(
                "AK-47 | Redline",
                Type.RIFLE,
                Rarity.CLASSIFIED,
                Condition.FIELDTESTED,
                15.99);
        skins.create(testSkin);

        Skin foundSkin = null;

        try {
            foundSkin = skins.getById(testSkin.getId());
            assertEquals(testSkin, foundSkin);
        } catch (SkinNotFoundException e) {
            fail("No skin was found when one should have been!");
        }
    }
}