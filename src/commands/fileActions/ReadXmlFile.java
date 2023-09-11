package commands.fileActions;
import commands.universe.Jedi;
import commands.universe.JediRank;
import commands.universe.Planet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ReadXmlFile {
    protected void read(String fileName) {
        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList planetNodes = doc.getElementsByTagName("planet");
            if (planetNodes.getLength() == 0) {
                System.out.println("Opened file successfully. No planets found.");
                return;
            }
            for (int planetIndex = 0; planetIndex < planetNodes.getLength(); planetIndex++) {
                Node planetNode = planetNodes.item(planetIndex);
                if (planetNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element planetElement = (Element) planetNode;
                    String planetName = planetElement.getElementsByTagName("planetName").item(0).getTextContent().trim();
                    System.out.println("\nPlanet: " + planetName);
                    Planet newPlanet = new Planet();
                    newPlanet.setName(planetName);
                    Planet.listOfPlanets.add(newPlanet);
                    NodeList jediNodes = planetElement.getElementsByTagName("jedi");
                    for (int jediIndex = 0; jediIndex < jediNodes.getLength(); jediIndex++) {
                        Node jediNode = jediNodes.item(jediIndex);
                        if (jediNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element jediElement = (Element) jediNode;
                            String jediName = jediElement.getElementsByTagName("jediName").item(0).getTextContent().trim();
                            String jediAge = jediElement.getElementsByTagName("jediAge").item(0).getTextContent().trim();
                            String saberColor = jediElement.getElementsByTagName("lightSaberColor").item(0).getTextContent().trim();
                            String rank = jediElement.getElementsByTagName("rank").item(0).getTextContent().trim();
                            String strength = jediElement.getElementsByTagName("strength").item(0).getTextContent().trim();

                            System.out.println
                                    ("Jedi name: " + jediName +
                                    "\nage: " + jediAge +
                                    "\nlight saber color: " + saberColor +
                                    "\nrank: " + rank +
                                    "\nstrength: " + strength
                                    );
                            Jedi jedi = new Jedi();
                            jedi.setName(jediName);
                            jedi.setAge(Integer.parseInt(jediAge));
                            jedi.setStrength(Double.parseDouble(strength));
                            jedi.setLightsaber(saberColor);
                            jedi.setJediRank(JediRank.valueOf(rank));
                            Planet selectedPlanet = null;
                            for (Planet planet : Planet.listOfPlanets) {
                                if (planet.getName().equals(planetName)) {
                                    selectedPlanet = planet;
                                    break;
                                }
                            }

                            if (selectedPlanet == null) {
                                selectedPlanet = new Planet();
                                selectedPlanet.setName(planetName);
                                Planet.listOfPlanets.add(selectedPlanet);
                            }

                            selectedPlanet.addJedi(jedi);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
