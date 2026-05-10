package se.yrgo.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.*;
import se.yrgo.domain.enums.*;
import se.yrgo.services.*;

public class Client {
    public static void main(String[] args) {

        ApplicationContext tx = new ClassPathXmlApplicationContext("application.xml");

        // Get services from Spring
        PlayerManagementService playerService = tx.getBean(PlayerManagementService.class);
        SkinManagementService skinService = tx.getBean(SkinManagementService.class);
        LoanManagementService loanService = tx.getBean(LoanManagementService.class);

        Player player = new Player();
        player.setName("s1mple");
        playerService.create(player);
        System.out.println("Created: " + player);

        Skin skin = new Skin();
        skin.setName("AK-47 | Redline");
        skin.setType(Type.RIFLE);
        skin.setRarity(Rarity.CLASSIFIED);
        skin.setCondition(Condition.FIELDTESTED);
        skin.setPrice(15.99);
        skinService.create(skin);
        System.out.println("Created: " + skin);

        Loan loan = new Loan();
        loan.setPlayer(player);
        loan.setSkin(skin);
        loanService.create(loan);
        System.out.println("Created: " + loan);

        System.out.println("\nAll Players:");
        playerService.getAllPlayers().forEach(System.out::println);

        System.out.println("\nAll Skins:");
        skinService.getAllSkins().forEach(System.out::println);

        System.out.println("\nAll Loans");
        loanService.getAllLoans().forEach(System.out::println);

        System.out.println("\nLoans by player id");
        loanService.getByPlayer(player.getId()).forEach(System.out::println);
    }
}