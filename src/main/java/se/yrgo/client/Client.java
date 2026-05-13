package se.yrgo.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.*;
import se.yrgo.domain.enums.*;
import se.yrgo.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext tx = new ClassPathXmlApplicationContext("application.xml")) {

            PlayerManagementService playerService = tx.getBean(PlayerManagementService.class);
            SkinManagementService skinService = tx.getBean(SkinManagementService.class);
            LoanManagementService loanService = tx.getBean(LoanManagementService.class);

            Player currentPlayer = new Player();

            try (Scanner sc = new Scanner(System.in)) {

                createData(skinService);

                currentPlayer = handlePlayer(playerService, currentPlayer, sc);

                handleLoans(skinService, loanService, playerService, currentPlayer, sc);
            }
        }
    }

    public static void handleLoans(SkinManagementService skinService,
                                   LoanManagementService loanService, PlayerManagementService playerService, Player currentPlayer, Scanner sc) {
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
            int menuChoice = readInt(sc);
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
                    try {
                        currentPlayer = playerService.getById(currentPlayer.getId());
                    } catch (PlayerNotFoundException e) {
                        System.out.println("Player not found");
                        break;
                    }

                    List<Loan> loans = currentPlayer.getLoans();
                    if (loans == null || loans.isEmpty()) {
                        System.out.println("This player has no loans");
                        break;
                    }
                    for (Loan l : loans) {
                        System.out.println(l);
                    }
                }

                case 3 -> currentPlayer = loanSkin(skinService, loanService, playerService, currentPlayer, sc);

                case 4 -> currentPlayer = returnSkin(skinService, loanService, playerService, currentPlayer, sc);

                case 5 -> currentPlayer = handlePlayer(playerService, currentPlayer, sc);

                case 6 -> System.exit(0);

                default -> System.out.println("Invalid choice");
            }
        }
    }

    public static Player handlePlayer(PlayerManagementService playerService, Player currentPlayer, Scanner sc) {
        boolean playerChosen = false;

        while (!playerChosen) {
            System.out.println("""
                    
                    Welcome
                    1: Choose a player
                    2: Create a new player
                    3: Quit
                    
                    """);

            System.out.print(": ");
            int menuChoice = readInt(sc);

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
                    int playerChoice = readInt(sc);

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

                    if (!playerChosen) {
                        System.out.println("Your entered ID does not match an existing player ID");
                    }
                }

                case 2 -> {
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    Player newPlayer = new Player();
                    newPlayer.setName(name);
                    playerService.create(newPlayer);
                    currentPlayer = newPlayer;
                }

                case 3 -> System.exit(0);

                default -> System.out.println("Invalid choice");
            }
        }
        return currentPlayer;
    }

    public static Player returnSkin(SkinManagementService skinService, LoanManagementService loanService,
                                    PlayerManagementService playerService, Player player, Scanner sc) {
        System.out.print("Enter loan ID: ");
        int id = readInt(sc);

        try {
            List<Loan> loans = player.getLoans();
            Loan loanToReturn = null;

            for (Loan l : loans) {
                if (l.getId() == id) {
                    loanToReturn = l;
                    break;
                }
            }

            if (loanToReturn == null) {
                System.out.println("Your entered loan ID does not match an existing loan");
                return player;
            }

            loanToReturn.getSkin().setLoaned(false);
            skinService.update(loanToReturn.getSkin());

            player.getLoans().remove(loanToReturn);
            loanToReturn.setPlayer(null);
            playerService.update(player);

            loanService.delete(loanToReturn);

            System.out.println("Loan returned successfully!");
            return playerService.getById(player.getId());

        } catch (LoanNotFoundException e) {
            System.out.println("Loan not found: " + e.getMessage());
        } catch (SkinNotFoundException e) {
            System.out.println("Skin not found: " + e.getMessage());
        } catch (PlayerNotFoundException e) {
            System.out.println("Player not found: " + e.getMessage());
        }
        return player;
    }

    public static Player loanSkin(SkinManagementService skinService, LoanManagementService loanService,
                                  PlayerManagementService playerService, Player player, Scanner sc) {
        System.out.print("Enter skin ID: ");
        int id = readInt(sc);

        try {
            Skin skinToAdd = skinService.getById(id);

            if (skinToAdd.isLoaned()) {
                System.out.println("This skin is already loaned");
                return player;
            }

            Loan newLoan = new Loan();
            newLoan.setPlayer(player);
            newLoan.setSkin(skinToAdd);
            loanService.create(newLoan);

            List<Loan> playerLoans = player.getLoans();
            if (playerLoans == null) {
                playerLoans = new ArrayList<>();
            }
            playerLoans.add(newLoan);
            player.setLoans(playerLoans);

            playerService.update(player);

            skinToAdd.setLoaned(true);
            skinService.update(skinToAdd);

            System.out.println("Skin loaned successfully!");
            return playerService.getById(player.getId());

        } catch (SkinNotFoundException e) {
            System.out.println("Skin not found: " + e.getMessage());
        } catch (PlayerNotFoundException e) {
            System.out.println("Player not found: " + e.getMessage());
        }
        return player;
    }

    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input, enter a number: ");
            sc.nextLine();
        }
        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    public static void createData(SkinManagementService skinService) {
        createSkin(skinService, "AK-47 | Redline", Type.RIFLE, Rarity.CLASSIFIED, Condition.FIELDTESTED, 35.99);
        createSkin(skinService, "AWP | Dragon Lore", Type.RIFLE, Rarity.COVERT, Condition.FACTORYNEW, 13322.45);
        createSkin(skinService, "M4A4 | Howl", Type.RIFLE, Rarity.CONTRABAND, Condition.MINIMALWEAR, 6163.51);
        createSkin(skinService, "MP7 | Armor Core", Type.SMG, Rarity.MILSPEC, Condition.FIELDTESTED, 1.35);
        createSkin(skinService, "USP-S | Kill Confirmed", Type.PISTOL, Rarity.COVERT, Condition.FIELDTESTED, 76.10);
        createSkin(skinService, "Glock-18 | Fade", Type.PISTOL, Rarity.RESTRICTED, Condition.FACTORYNEW, 2102.83);
        createSkin(skinService, "Butterfly Knife | Fade", Type.MELEE, Rarity.GOLD, Condition.MINIMALWEAR, 2592.83);
        createSkin(skinService, "P90 | Asiimov", Type.SMG, Rarity.COVERT, Condition.BATTLESCARRED, 165.80);
        createSkin(skinService, "MP9 | Hydra", Type.SMG, Rarity.CLASSIFIED, Condition.WELLWORN, 5.75);
        createSkin(skinService, "FAMAS | Commemoration", Type.RIFLE, Rarity.COVERT, Condition.MINIMALWEAR, 39.92);
        createSkin(skinService, "Mac-10 | Fade", Type.SMG, Rarity.MILSPEC, Condition.MINIMALWEAR, 77.85);
        createSkin(skinService, "Desert Eagle | Blaze", Type.PISTOL, Rarity.RESTRICTED, Condition.FACTORYNEW, 837.39);
        createSkin(skinService, "P250 | Valence", Type.PISTOL, Rarity.MILSPEC, Condition.WELLWORN, 0.58);
        createSkin(skinService, "Sport Gloves | Vice", Type.GLOVES, Rarity.GOLD, Condition.FACTORYNEW, 8449.92);
        createSkin(skinService, "Glock-18 | Twilight Galaxy", Type.PISTOL, Rarity.CLASSIFIED, Condition.MINIMALWEAR, 283.40);
        createSkin(skinService, "M4A1-S | Cyrex", Type.RIFLE, Rarity.COVERT, Condition.FIELDTESTED, 202.80);
        createSkin(skinService, "Glock-18 | Candy Apple", Type.PISTOL, Rarity.MILSPEC, Condition.FACTORYNEW, 2.48);
        createSkin(skinService, "AWP | Dragon Lore (Souvenir)", Type.RIFLE, Rarity.CONTRABAND, Condition.FIELDTESTED, 619712.17);
        createSkin(skinService, "USP-S | Guardian", Type.PISTOL, Rarity.RESTRICTED, Condition.MINIMALWEAR, 4.08);
        createSkin(skinService, "Butterfly Knife | Night", Type.MELEE, Rarity.GOLD, Condition.WELLWORN, 593.77);
    }

    private static void createSkin(SkinManagementService skinService, String name, Type type, Rarity rarity, Condition condition, double price) {
        Skin skin = new Skin();
        skin.setName(name);
        skin.setType(type);
        skin.setRarity(rarity);
        skin.setCondition(condition);
        skin.setPrice(price);
        skinService.create(skin);
    }
}