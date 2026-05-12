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

        int menuChoice = 0;

        Player currentPlayer = new Player();

        try (Scanner sc = new Scanner(System.in)) {

            currentPlayer = choosePlayer(sc, playerService, currentPlayer);

            createData(skinService, loanService, currentPlayer);

            while (true) {
                System.out.printf("""

                        Welcome %s
                        1: View all skins
                        2: View loaned skins
                        3: Loan skin
                        4: Return skin
                        5: Change player
                        6: Quit

                        """, currentPlayer.getName());

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
                        List<Loan> loans = currentPlayer.getLoans();
                        if (loans == null || loans.isEmpty()) {
                            System.out.println("This player has no loans");
                            break;
                        }
                        for (Loan l : loans) {
                            System.out.println(l);
                        }
                    }

                    case 3 -> {
                        loanSkin(skinService, loanService, playerService, currentPlayer, sc);
                    }

                    case 4 -> {
                        returnSkin(skinService, loanService, playerService, currentPlayer, sc);

                    }

                    case 5 -> {
                        currentPlayer = choosePlayer(sc, playerService, currentPlayer);
                    }

                    case 6 -> {
                        System.exit(0);
                    }

                    default -> {
                        System.out.println("Invalid choice");
                    }
                }
            }
        }
    }

    public static Player choosePlayer(Scanner sc, PlayerManagementService playerService, Player currentPlayer) {
        boolean playerChosen = false;

        int menuChoice = 0;
        int playerChoice = 0;

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
                    List<Player> allPlayers = playerService.getAllPlayers();

                    if (allPlayers.isEmpty()) {
                        System.out.println("No current players");
                        break;
                    }
                    for (Player p : allPlayers) {
                        System.out.println(p);
                    }

                    System.out.print("\nChoose player with id: ");
                    playerChoice = sc.nextInt();
                    sc.nextLine();

                    for (Player p : allPlayers) {
                        if (playerChoice == p.getId()) {
                            try {
                                currentPlayer = playerService.getById(playerChoice);
                                playerChosen = true;
                            } catch (PlayerNotFoundException e) {
                                System.out.println("Player not found: " + e.getMessage());
                            }
                        }
                    }

                    System.out.println("Your entered ID does not match an existing player ID");
                }

                case 2 -> {
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    Player newPlayer = new Player();
                    newPlayer.setName(name);

                    playerService.create(newPlayer);

                    currentPlayer = newPlayer;
                }

                case 3 -> {
                    System.exit(0);
                }

                default -> {
                    System.out.println("Invalid choice");
                }
            }
        }

        return currentPlayer;
    }

    public static void returnSkin(SkinManagementService skinService, LoanManagementService loanService,
                                PlayerManagementService playerService, Player player, Scanner sc) {
        System.out.print("Enter loan ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Loan loanToReturn = new Loan();

        try {
            List<Loan> loans = player.getLoans();
            for(Loan l : loans) {
                if(l.getId() == id) {
                    loanToReturn = l;

                    loans.remove(loanToReturn);
                    player.setLoans(loans);
                    playerService.update(player);

                    loanToReturn.getSkin().setLoaned(false);
                    skinService.update(loanToReturn.getSkin());

                    loanService.delete(loanToReturn);

                    System.out.println("Loan returned successfully!");
                    break;
                }

                System.out.println("Your entered loan ID does not match an existing loan");
            }

        } catch (LoanNotFoundException e) {
            System.out.println("Loan not found: " + e.getMessage());
        } catch (SkinNotFoundException e) {
            System.out.println("Skin not found: " + e.getMessage());
        } catch (PlayerNotFoundException e) {
            System.out.println("Player not found: " + e.getMessage());
        }
    }

    public static void loanSkin(SkinManagementService skinService, LoanManagementService loanService,
            PlayerManagementService playerService, Player player, Scanner sc) {
        System.out.print("Enter skin ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean isLoaned = loanService.getAllLoans()
                .stream()
                .anyMatch(loan -> loan.getSkin().getId() == id);

        if(isLoaned) {
            System.out.println("This skin is loaned");
            return;
        }

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
        skin1.setPrice(35.99);
        skinService.create(skin1);

        Skin skin2 = new Skin();
        skin2.setName("AWP | Dragon Lore");
        skin2.setType(Type.RIFLE);
        skin2.setRarity(Rarity.COVERT);
        skin2.setCondition(Condition.FACTORYNEW);
        skin2.setPrice(13322.45);
        skinService.create(skin2);

        Skin skin3 = new Skin();
        skin3.setName("M4A4 | Howl");
        skin3.setType(Type.RIFLE);
        skin3.setRarity(Rarity.CONTRABAND);
        skin3.setCondition(Condition.MINIMALWEAR);
        skin3.setPrice(6163.51);
        skinService.create(skin3);

        Skin skin4 = new Skin();
        skin4.setName("MP7 | Armor Core");
        skin4.setType(Type.SMG);
        skin4.setRarity(Rarity.MILSPEC);
        skin4.setCondition(Condition.FIELDTESTED);
        skin4.setPrice(1.35);
        skinService.create(skin4);

        Skin skin5 = new Skin();
        skin5.setName("USP-S | Kill Confirmed");
        skin5.setType(Type.PISTOL);
        skin5.setRarity(Rarity.COVERT);
        skin5.setCondition(Condition.FIELDTESTED);
        skin5.setPrice(76.10);
        skinService.create(skin5);

        Skin skin6 = new Skin();
        skin6.setName("Glock-18 | Fade");
        skin6.setType(Type.PISTOL);
        skin6.setRarity(Rarity.RESTRICTED);
        skin6.setCondition(Condition.FACTORYNEW);
        skin6.setPrice(2102.83);
        skinService.create(skin6);

        Skin skin7 = new Skin();
        skin7.setName("Butterfly Knife | Fade");
        skin7.setType(Type.MELEE);
        skin7.setRarity(Rarity.GOLD);
        skin7.setCondition(Condition.MINIMALWEAR);
        skin7.setPrice(2592.83);
        skinService.create(skin7);

        Skin skin8 = new Skin();
        skin8.setName("P90 | Asiimov");
        skin8.setType(Type.SMG);
        skin8.setRarity(Rarity.COVERT);
        skin8.setCondition(Condition.BATTLESCARRED);
        skin8.setPrice(165.80);
        skinService.create(skin8);

        Skin skin9 = new Skin();
        skin9.setName("MP9 | Hydra");
        skin9.setType(Type.SMG);
        skin9.setRarity(Rarity.CLASSIFIED);
        skin9.setCondition(Condition.WELLWORN);
        skin9.setPrice(5.75);
        skinService.create(skin9);

        Skin skin10 = new Skin();
        skin10.setName("FAMAS | Commemoration");
        skin10.setType(Type.RIFLE);
        skin10.setRarity(Rarity.COVERT);
        skin10.setCondition(Condition.MINIMALWEAR);
        skin10.setPrice(39.92);
        skinService.create(skin10);

        Skin skin11 = new Skin();
        skin11.setName("Mac-10 | Fade");
        skin11.setType(Type.SMG);
        skin11.setRarity(Rarity.MILSPEC);
        skin11.setCondition(Condition.MINIMALWEAR);
        skin11.setPrice(77.85);
        skinService.create(skin11);

        Skin skin12 = new Skin();
        skin12.setName("Desert Eagle | Blaze");
        skin12.setType(Type.PISTOL);
        skin12.setRarity(Rarity.RESTRICTED);
        skin12.setCondition(Condition.FACTORYNEW);
        skin12.setPrice(837.39);
        skinService.create(skin12);

        Skin skin13 = new Skin();
        skin13.setName("P250 | Valence");
        skin13.setType(Type.PISTOL);
        skin13.setRarity(Rarity.MILSPEC);
        skin13.setCondition(Condition.WELLWORN);
        skin13.setPrice(0.58);
        skinService.create(skin13);

        Skin skin14 = new Skin();
        skin14.setName("Sport Gloves | Vice");
        skin14.setType(Type.GLOVES);
        skin14.setRarity(Rarity.GOLD);
        skin14.setCondition(Condition.FACTORYNEW);
        skin14.setPrice(8449.92);
        skinService.create(skin14);

        Skin skin15 = new Skin();
        skin15.setName("AK-47 | Redline (Minimal Wear)");
        skin15.setType(Type.RIFLE);
        skin15.setRarity(Rarity.CLASSIFIED);
        skin15.setCondition(Condition.MINIMALWEAR);
        skin15.setPrice(159.40);
        skinService.create(skin15);

        Skin skin16 = new Skin();
        skin16.setName("M4A1-S | Cyrex");
        skin16.setType(Type.RIFLE);
        skin16.setRarity(Rarity.COVERT);
        skin16.setCondition(Condition.FIELDTESTED);
        skin16.setPrice(202.80);
        skinService.create(skin16);

        Skin skin17 = new Skin();
        skin17.setName("Glock-18 | Candy Apple");
        skin17.setType(Type.PISTOL);
        skin17.setRarity(Rarity.MILSPEC);
        skin17.setCondition(Condition.FACTORYNEW);
        skin17.setPrice(2.48);
        skinService.create(skin17);

        Skin skin18 = new Skin();
        skin18.setName("AWP | Dragon Lore (Souvenir)");
        skin18.setType(Type.RIFLE);
        skin18.setRarity(Rarity.CONTRABAND);
        skin18.setCondition(Condition.FIELDTESTED);
        skin18.setPrice(619712.17);
        skinService.create(skin18);

        Skin skin19 = new Skin();
        skin19.setName("USP-S | Guardian");
        skin19.setType(Type.PISTOL);
        skin19.setRarity(Rarity.RESTRICTED);
        skin19.setCondition(Condition.MINIMALWEAR);
        skin19.setPrice(4.08);
        skinService.create(skin19);

        Skin skin20 = new Skin();
        skin20.setName("Butterfly Knife | Night");
        skin20.setType(Type.MELEE);
        skin20.setRarity(Rarity.GOLD);
        skin20.setCondition(Condition.WELLWORN);
        skin20.setPrice(593.77);
        skinService.create(skin20);
    }
}