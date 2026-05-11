package se.yrgo.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.*;
import se.yrgo.domain.enums.*;
import se.yrgo.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        ApplicationContext tx = new ClassPathXmlApplicationContext("application.xml");

        PlayerManagementService playerService = tx.getBean(PlayerManagementService.class);
        SkinManagementService skinService = tx.getBean(SkinManagementService.class);
        LoanManagementService loanService = tx.getBean(LoanManagementService.class);

        boolean playerChosen = false;

        int playerChoice = 0;
        int menuChoice = 0;

        Player player = new Player();

        try (Scanner sc = new Scanner(System.in)) {
            while (!playerChosen) {
                System.out.println("""

                        Welcome
                        1: Choose a player
                        2: Create a new player
                        3: Quit

                        """);

                System.out.print(": ");
                menuChoice = sc.nextInt();
                sc.nextLine();

                switch (menuChoice) {
                    case 1 -> {
                        List<Player> players = playerService.getAllPlayers();

                        if (players.isEmpty()) {
                            System.out.println("No current players");
                            break;
                        }
                        for (Player p : players) {
                            System.out.println(p);
                        }

                        System.out.print("\nChoose player with id: ");
                        playerChoice = sc.nextInt();
                        sc.nextLine();

                        for (Player p : players) {
                            if (playerChoice == p.getId()) {
                                try {
                                    player = playerService.getById(playerChoice);
                                } catch (PlayerNotFoundException e) {
                                    System.out.println("Player not found: " + e.getMessage());
                                }
                                playerChosen = true;
                            }
                        }
                    }

                    case 2 -> {
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();

                        player.setName(name);

                        playerService.create(player);

                        playerChosen = true;
                    }

                    case 3 -> {
                        System.exit(0);
                    }

                    default -> {
                        System.out.println("Invalid choice");
                    }
                }
            }

            createData(skinService, loanService, player);

            menuChoice = 0;
            while (menuChoice != 9) {
                System.out.printf("""

                        Welcome %s
                        1: View all skins
                        2: View loaned skins
                        3: Loan skin
                        4: Return skin
                        5: Quit

                        """, player.getName());

                System.out.print(": ");
                menuChoice = sc.nextInt();
                System.out.println();

                switch (menuChoice) {
                    case 1 -> {
                        List<Skin> skins = skinService.getAllSkins().stream()
                                .filter(s -> !s.isLoaned())
                                .toList();
                        if (skins.isEmpty()) {
                            System.out.println("No skins existing");
                            break;
                        }
                        for (Skin s : skins) {
                            System.out.println(s);
                        }

                    }

                    case 2 -> {
                        List<Loan> loans = player.getLoans();
                        if (loans == null || loans.isEmpty()) {
                            System.out.println("This player has no loans");
                            break;
                        }
                        for (Loan l : loans) {
                            System.out.println(l);
                        }
                    }

                    case 3 -> {
                        loanSkin(skinService, loanService, playerService, player, sc);
                    }

                    case 4 -> {

                    }

                    case 5 -> {
                        menuChoice = 9;
                    }

                    default -> {

                    }
                }
            }
        }
    }

    public static void loanSkin(SkinManagementService skinService, LoanManagementService loanService,
            PlayerManagementService playerService, Player player, Scanner sc) {
        System.out.print("Enter skin ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            Skin skinToAdd = skinService.getById(id);

            Loan newLoan = new Loan();
            newLoan.setPlayer(player);
            newLoan.setSkin(skinToAdd);
            loanService.create(newLoan);

            List<Loan> playerLoans = player.getLoans();
            if (playerLoans == null || playerLoans.isEmpty()) {
                playerLoans = new ArrayList<>();
            }
            playerLoans.add(newLoan);
            player.setLoans(playerLoans);

            playerService.update(player);

            skinToAdd.setLoaned(true);
            skinService.update(skinToAdd);

            System.out.println("Skin loaned successfully!");

        } catch (SkinNotFoundException e) {
            System.out.println("Skin not found: " + e.getMessage());
        } catch (PlayerNotFoundException e) {
            System.out.println("Player not found: " + e.getMessage());
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
        skin4.setRarity(Rarity.RESTRICTED);
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
        skin7.setName("Butterfly Knife | Fade");
        skin7.setType(Type.MELEE);
        skin7.setRarity(Rarity.GOLD);
        skin7.setCondition(Condition.MINIMALWEAR);
        skin7.setPrice(2352.00);
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
        skin9.setRarity(Rarity.CLASSIFIED);
        skin9.setCondition(Condition.WELLWORN);
        skin9.setPrice(8.75);
        skinService.create(skin9);

        Skin skin10 = new Skin();
        skin10.setName("FAMAS | Commemoration");
        skin10.setType(Type.RIFLE);
        skin10.setRarity(Rarity.COVERT);
        skin10.setCondition(Condition.MINIMALWEAR);
        skin10.setPrice(21.35);
        skinService.create(skin10);
    }
}