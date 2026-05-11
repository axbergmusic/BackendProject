package se.yrgo.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.*;
import se.yrgo.domain.enums.*;
import se.yrgo.services.*;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        ApplicationContext tx = new ClassPathXmlApplicationContext("application.xml");

        PlayerManagementService playerService = tx.getBean(PlayerManagementService.class);
        SkinManagementService skinService = tx.getBean(SkinManagementService.class);
        LoanManagementService loanService = tx.getBean(LoanManagementService.class);


        Player player = new Player();
        player.setName("s1mple");
        playerService.create(player);

        createData(skinService, loanService, player);

        System.out.printf("""
                Welcome %s
                """, playerService.getAllPlayerNames());

        try(Scanner sc = new Scanner(System.in)) {
            int choice = 0;
            while(choice != 9) {
                System.out.println("""
                1: View all skins
                2: View loaned skins
                3: Loan skin
                4: Return skin
                5: Quit
                """);

                System.out.print(": ");
                choice = sc.nextInt();

                switch(choice) {
                    case 1 -> {
                        skinService.getAllSkins();
                    }

                    case 2 -> {
                        loanService.getAllLoans();
                    }

                    case 3 -> {
                        addSkin(skinService, loanService, player);
                    }

                    case 4 -> {

                    }

                    case 5 -> {
                        choice = 9;
                    }

                    default -> {

                    }
                }
            }
        }







    }

    public static void addSkin(SkinManagementService skinService, LoanManagementService loanService, Player player) {
        try(Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter skin ID: ");
            int id = sc.nextInt();

            Skin newSkin = skinService.getById(id);

            Loan newLoan = new Loan();
            newLoan.setPlayer(player);
            newLoan.setSkin(newSkin);

            skinService.delete(newSkin);

        } catch (SkinNotFoundException e) {
            System.out.println("Skin not found: " + e.getMessage());
        }


    }

    public static void createData(SkinManagementService skinService, LoanManagementService loanService, Player player) {
        Skin skin1 = new Skin();
        skin1.setName("AK-47 | Redline");
        skin1.setType(Type.RIFLE);
        skin1.setRarity(Rarity.CLASSIFIED);
        skin1.setCondition(Condition.FIELDTESTED);
        skin1.setPrice(15.99);
        skinService.create(skin1);

        Skin skin2 = new Skin();
        skin2.setName("AWP | Dragon Lore");
        skin2.setType(Type.RIFLE);
        skin2.setRarity(Rarity.COVERT);
        skin2.setCondition(Condition.FACTORYNEW);
        skin2.setPrice(2499.99);
        skinService.create(skin2);

        Skin skin3 = new Skin();
        skin3.setName("M4A4 | Howl");
        skin3.setType(Type.RIFLE);
        skin3.setRarity(Rarity.CONTRABAND);
        skin3.setCondition(Condition.MINIMALWEAR);
        skin3.setPrice(1899.50);
        skinService.create(skin3);

        Skin skin4 = new Skin();
        skin4.setName("Desert Eagle | Blaze");
        skin4.setType(Type.PISTOL);
        skin4.setRarity(Rarity.COVERT);
        skin4.setCondition(Condition.FACTORYNEW);
        skin4.setPrice(450.75);
        skinService.create(skin4);

        Skin skin5 = new Skin();
        skin5.setName("USP-S | Kill Confirmed");
        skin5.setType(Type.PISTOL);
        skin5.setRarity(Rarity.COVERT);
        skin5.setCondition(Condition.FIELDTESTED);
        skin5.setPrice(89.99);
        skinService.create(skin5);

        Skin skin6 = new Skin();
        skin6.setName("Glock-18 | Fade");
        skin6.setType(Type.PISTOL);
        skin6.setRarity(Rarity.RESTRICTED);
        skin6.setCondition(Condition.FACTORYNEW);
        skin6.setPrice(1200.00);
        skinService.create(skin6);

        Skin skin7 = new Skin();
        skin7.setName("Desert Eagle | Blaze");
        skin7.setType(Type.PISTOL);
        skin7.setRarity(Rarity.COVERT);
        skin7.setCondition(Condition.MINIMALWEAR);
        skin7.setPrice(21.00);
        skinService.create(skin7);

        Skin skin8 = new Skin();
        skin8.setName("P90 | Asiimov");
        skin8.setType(Type.SMG);
        skin8.setRarity(Rarity.COVERT);
        skin8.setCondition(Condition.BATTLESCARRED);
        skin8.setPrice(32.40);
        skinService.create(skin8);

        Skin skin9 = new Skin();
        skin9.setName("MP9 | Hydra");
        skin9.setType(Type.SMG);
        skin9.setRarity(Rarity.RESTRICTED);
        skin9.setCondition(Condition.WELLWORN);
        skin9.setPrice(8.75);
        skinService.create(skin9);

        Skin skin10 = new Skin();
        skin10.setName("FAMAS | Commemoration");
        skin10.setType(Type.RIFLE);
        skin10.setRarity(Rarity.CLASSIFIED);
        skin10.setCondition(Condition.MINIMALWEAR);
        skin10.setPrice(21.35);
        skinService.create(skin10);

        Loan loan = new Loan();
        loan.setPlayer(player);
        loan.setSkin(skin1);
        loanService.create(loan);
    }
}
