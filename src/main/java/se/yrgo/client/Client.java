package se.yrgo.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import se.yrgo.domain.Skin;
import se.yrgo.domain.enums.Condition;
import se.yrgo.domain.enums.Rarity;
import se.yrgo.domain.enums.Type;
import se.yrgo.services.SkinManagementService;

public class Client {
    public static void main(String[] args) {

        try (ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml")) {

            SkinManagementService skinService = container.getBean(SkinManagementService.class);

            skinService.create(
                    new Skin(0, "AWP | Dragon Lore", Type.RIFLE, Rarity.COVERT, Condition.FACTORYNEW, 11616.28));
        }
    }
}