package menu;

import commands.fileActions.*;
import commands.universe.Jedi;
import commands.universe.JediRank;
import commands.universe.Planet;
import contracts.Command;

import java.text.DecimalFormat;
import java.util.Scanner;

public class MainMenu implements Command {
    boolean isFileOpened = false;
    String fileName;
    Scanner scanner = new Scanner(System.in);
    Jedi testJedi = new Jedi();
    Planet testPlanet = new Planet();
    FileActions file = new FileActions();
    boolean flag = false;

    @Override
    public void runCommand(String[] option) throws Exception {
        if (option.length >= 3 && option[1].equals("+")) {
            System.out.println(testPlanet.printTwoPlanetsJedis(option[0], option[2]));
            flag = true;
        }
        switch (option[0].toLowerCase()) {
            case "open":
                if (option.length != 2) {
                    System.out.println("Please after the command 'open' enter the file name or path!");
                } else {
                    fileName = option[1];
                    file.open(fileName);
                    isFileOpened = true;
                }
                break;
            case "help":
                System.out.println(file.help());
                break;
            case "close":
                file.close(fileName, isFileOpened);
                isFileOpened = false;
                fileName = null;
                break;
            case "save":
                if (isFileOpened) {
                    String filePath = fileName;
                    file.save(filePath);
                } else {
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            case "saveas": {
                if (isFileOpened) {
                    System.out.println("Please enter the path you would like to save your file: ");
                    String filePath = scanner.nextLine();
                    file.save(filePath);
                } else {
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            }
            case "add_planet":
                if (isFileOpened) {
                    testPlanet.addPlanet();
                } else {
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            case "create_jedi": {
                if (isFileOpened) {
                    testJedi.addJedi();
                } else {
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            }
            case "remove_jedi": {
                if (isFileOpened) {
                    System.out.println(testJedi.removeJedi());
                } else {
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            }
            case "promote_jedi": {
                if (isFileOpened) {
                    String jediNameToPromote = "";
                    System.out.println("Please enter the name of the Jedi you want to promote: ");
                    jediNameToPromote = scanner.nextLine();
                    DecimalFormat df = new DecimalFormat("0.00"); // Format with two decimal places
                    Jedi jediInstance = new Jedi();
                    Jedi jediToPromote = jediInstance.findJedi(jediNameToPromote);

                    if (jediToPromote != null) {
                        Object[] result = jediToPromote.getRank().promote(); // Promote the rank and get the result
                        JediRank newRank = (JediRank) result[0];
                        double multiplier = (double) result[1];
                        jediToPromote.promote(newRank, multiplier);
                        System.out.println("Jedi named " + jediNameToPromote + " rank:  " + newRank);
                        System.out.println("Strength: " + df.format(jediToPromote.getStrength()));
                    } else {
                        System.out.println("Jedi with the given name not found.");
                    }
                } else {
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            }
            case "demote_jedi": {
                if (isFileOpened) {
                    String jediNameToDemote = "";
                    System.out.println("Please enter the name of the Jedi you want to demote: ");
                    jediNameToDemote = scanner.nextLine();
                    Jedi jediInstance = new Jedi();
                    Jedi jediToDemote = jediInstance.findJedi(jediNameToDemote);
                    if (jediToDemote != null) {
                        Object[] result = jediToDemote.getRank().demote(); // Promote the rank and get the result
                        JediRank newRank = (JediRank) result[0];
                        double multiplier = (double) result[1];
                        jediToDemote.demote(newRank, multiplier);
                        System.out.println("Jedi named " + jediNameToDemote + " rank: " + newRank);
                        System.out.println("Strength: " + jediToDemote.getStrength());
                    } else {
                        System.out.println("Jedi with the given name not found!");
                    }
                } else {
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            }
            case "get_strongest_jedi": {
                if(isFileOpened){
                    System.out.println(testJedi.getStrongestJedi());
                }else{
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");

                }
                break;
            }
            case "get_youngest_jedi": {
                if (isFileOpened) {
                    System.out.println(testJedi.getYoungestJedi());
                } else {
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            }
            case "get_most_used_saber_color": {
                if (isFileOpened) {
                    System.out.println(testJedi.getMostUsedSaberColor());
                } else {
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            }
            case "get_most_used_saber_color_grand_master": {
                if (isFileOpened) {
                    System.out.println(testJedi.getMostUsedSaberColorGrandMaster());
                } else {
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            }
            case "print_jedi": {
                if (isFileOpened) {
                    System.out.println(testJedi.printJedi());
                } else {
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            }
            case "print_planet": {
                if (isFileOpened) {
                System.out.println(testPlanet.printPlanet());
                }else{
                    System.out.println("No files opened! Please check 'help' to see all the available commands.");
                }
                break;
            }
            default:
                if (!flag) {
                    System.out.println("Invalid command! Please look at the 'help' section to choose the correct command!");
                }
        }

    }
}