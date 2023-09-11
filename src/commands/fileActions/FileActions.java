package commands.fileActions;

import commands.universe.Jedi;
import commands.universe.Planet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

public class FileActions {
    public void close(String fileName, boolean isFileOpened) throws Exception {
        if(!isFileOpened){
            throw new Exception("No files opened! Please use 'open' command and write the name of your file.");
        }else{
            Planet.listOfPlanets.clear();
            System.out.println("Closed file: " +fileName);
        }
    }
    public void open(String fileName) {
        ReadXmlFile reading = new ReadXmlFile();
        String filePath = fileName; // Include the package path
        try {
            if (!Files.exists(Path.of(filePath))) {
                Files.createFile(Path.of(filePath));
                System.out.println("File " + fileName + " created successfully.");
            } else {
                reading.read(filePath);
                System.out.println("File " + fileName + " opened successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String help() {
        String helpInfo = "These are all the commands you can run: \n" +
                "open (file) -> open a file after entering its name \n" +
                "close -> closes currently opened file \n" +
                "save -> saves the currently opened file \n" +
                "saveas -> saves the currently opened file in a directory or another file \n" +
                "help -> prints commands information \n" +
                "add_planet -> add a new planet \n" +
                "create_jedi -> create a new Jedi \n" +
                "remove_jedi -> remove a jedi from a planet \n" +
                "promote_jedi -> let the jedi get better and promote his rank and strength \n" +
                "demote_jedi -> demote the jedi a level down on the rank list and lower the strength \n" +
                "get_strongest_jedi -> get the strongest jedi of all Jedis on the planet \n" +
                "get_youngest_jedi -> get the youngest jedi on the planet with a specified by you rank \n" +
                "get_most_used_saber_color -> get the most used saber color by jedis on the planet and with a specified by you rank \n"  +
                "get_most_used_saber_color_grand_master -> get the most used saber color used from at least one Grand Master \n" +
                "print_planet -> print jedis on a planet sorted by rank and then lexicographically \n" +
                "print_jedi -> print jedi details and on which planet it is located \n" +
                "planetName + planetName -> get the jedis on two planets sorted lexicographically \n" +
                "exit -> exits the program";
        return helpInfo;
    }
    public void save(String fileName) {
        String dataToSave = generateDataToSave();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(dataToSave);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String generateDataToSave() {
        StringBuilder data = new StringBuilder();
        data.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
        data.append("<planets>\n");
        DecimalFormat df = new DecimalFormat("0.00"); // Format with two decimal places

        for (Planet planet : Planet.listOfPlanets) {
            data.append("\t<planet>\n");
            data.append("\t\t<planetName>").append(planet.getName()).append("</planetName>\n");
            data.append("\t\t<jedis>\n");
            for (Jedi jediOnPlanet : planet.getJedis()) {
                data.append("\t\t\t<jedi>\n");
                data.append("\t\t\t\t<jediName>").append(jediOnPlanet.getName()).append("</jediName>\n");
                data.append("\t\t\t\t<jediAge>").append(jediOnPlanet.getAge()).append("</jediAge>\n");
                data.append("\t\t\t\t<lightSaberColor>").append(jediOnPlanet.getLightsaber()).append("</lightSaberColor>\n");
                data.append("\t\t\t\t<rank>").append(jediOnPlanet.getRank()).append("</rank>\n");
                data.append("\t\t\t\t<strength>").append(df.format(jediOnPlanet.getStrength())).append("</strength>\n");
                data.append("\t\t\t</jedi>\n");
            }
            data.append("\t\t</jedis>\n");
            data.append("\t</planet>\n");
        }
        data.append("</planets>\n");

        return data.toString();
    }
}
