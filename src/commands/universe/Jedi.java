package commands.universe;

import contracts.Add;

import java.text.DecimalFormat;
import java.util.*;

public class Jedi implements Add {
    String name, lightsaber, planet, currentName, currentLightsaber;
    JediRank rank;
    int age, currentAge, currentRank, number;
    double strength, currentStrength;
    boolean validAge;
    Scanner scanner = new Scanner(System.in);
    public Map<Integer, JediRank> getRankList() {
        Map<Integer, JediRank> possibleRanks = new HashMap<>();
        possibleRanks.put(1, JediRank.YOUNGLING);
        possibleRanks.put(2, JediRank.INITIATE);
        possibleRanks.put(3, JediRank.PADAWAN);
        possibleRanks.put(4, JediRank.KNIGHT_ASPIRANT);
        possibleRanks.put(5, JediRank.KNIGHT);
        possibleRanks.put(6, JediRank.MASTER);
        possibleRanks.put(7, JediRank.BATTLE_MASTER);
        possibleRanks.put(8, JediRank.GRAND_MASTER);
        return possibleRanks;
    }
    public void setName(String name) {
        this.name = name;
    }
    protected String checkName() {
        String result = "";
        do {
            System.out.println("Please enter the name of your Jedi: ");
            currentName = scanner.nextLine();

            boolean isUnique = isNameUnique(currentName);
            boolean isValidFormat = isNameValid(currentName);

            if (isUnique && isValidFormat) {
                result = currentName;
            } else {
                if (!isUnique) {
                    System.out.println("A Jedi with this name already exists on this or another planet!");
                }
                if (!isValidFormat) {
                    System.out.println("Invalid name format. Name can't be empty, start with a digit, or contain special characters.");
                }
            }

        } while (result.isEmpty());

        return result;
    }
    private boolean isNameUnique(String name) {
        for (Planet planet : Planet.listOfPlanets) {
            for (Jedi jediOnPlanet : planet.getJedis()) {
                if (jediOnPlanet.name.equalsIgnoreCase(name)) {
                    return false;
                }
            }
        }
        return true; // Name is unique
    }
    private boolean isNameValid(String name) {
        return !name.isBlank() && !Character.isDigit(name.charAt(0));
    }
    private int checkAge() {
        int result = 0;
        do {
            System.out.println("Please enter the age of your Jedi: ");
            if (scanner.hasNextInt()) {
                currentAge = scanner.nextInt();
                scanner.nextLine();
                validAge = isAgeValid(currentAge);
                result = currentAge;
                if (!validAge) {
                    System.out.println("Invalid age. Please enter a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the non-integer token from the input buffer
                validAge = false;
            }
        } while (!validAge);
        return result;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getLightsaber() {
        return lightsaber;
    }
    public double getStrength() {
        DecimalFormat df = new DecimalFormat("0.00"); // Format with two decimal places
        return Double.parseDouble(df.format(strength));
    }
    public JediRank checkRank() {
        boolean validRank = false;
        JediRank result = JediRank.BATTLE_MASTER;
        do {
            System.out.println("Please choose your Jedi's Rank: \n" + getRankList());
            if (scanner.hasNextInt()) {
                try {
                    currentRank = scanner.nextInt();
                    if (currentRank < 1 || currentRank > 8) {
                        System.out.println("Please write down a number between 1-8 for your Jedi's rank!");
                    } else {
                        result = getRankList().get(currentRank); // Adjust index
                        validRank = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); // Clear the invalid line from the input buffer
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid line from the input buffer
            }
        } while ((currentRank < 1 || currentRank > 8) && !validRank);

        return result;
    }
    private String checkSaber() {
        String result = "";
        do {
            System.out.println("Please enter the color of your Jedi's lightsaber: ");
            currentLightsaber = scanner.nextLine();
            result = currentLightsaber;
            if (!isString(currentLightsaber)) {
                System.out.println("The color should be a word and not start with a space! ");
            }
        } while (!isString(currentLightsaber));
        return result;
    }
    private double checkStrength() {
        double result = 0;
        do {
            System.out.println("Please enter your Jedi's power: ");
            if (scanner.hasNextDouble()) {
                currentStrength = scanner.nextDouble();
                result = currentStrength;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
                currentStrength = Double.NaN; // Set currentStrength to an invalid value to repeat the loop
            }
        } while (Double.isNaN(currentStrength));
        return result;
    }
    protected String checkPlanet() {
        String result = "";
        Planet planet1 = new Planet();
        Map<Integer, String> planetNumberMap = new HashMap<>();
        do {
            if (!Planet.listOfPlanets.isEmpty()) {
                System.out.println("These are all the planets that you can choose from or you can create your own planet using the 'add_planet' command. Look at 'help' section for more information. ");
                int counter = 1;
                for (Planet planet : Planet.listOfPlanets) {
                    planetNumberMap.put(counter, planet.getName());
                    counter++;
                }
                for (Map.Entry<Integer, String> entry : planetNumberMap.entrySet()) {
                    System.out.println(entry.getKey() + ". " + entry.getValue());
                }
                System.out.println("0. add_planet");
                System.out.println("Please enter just the number of the chosen planet: ");
                number = scanner.nextInt();
                if (number == 0) {
                    planet1.addPlanet();
                }
                result = planetNumberMap.get(number);
            } else {
                System.out.println("There are no planets created. Please add a planet first!");
                planet1.addPlanet();
            }
        } while (number < 1 || number > planetNumberMap.size());
        return result;
    }
    public void setJediRank(JediRank rank) {
        this.rank = rank;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setLightsaber(String lightsaber) {
        this.lightsaber = lightsaber;
    }
    public void setStrength(double strength) {
        this.strength = strength;
    }
    private void setPlanet(String planet) {
        this.planet = planet;
    }
    private boolean isAgeValid(int age) {
        return age > 0;
    }
    private boolean isString(String input) {
        return input != null && input.matches("^[a-zA-Z_\\s]*$");
    }
    @Override
    public void addJedi() {
        Jedi jedi = new Jedi();
        jedi.setName(checkName());
        jedi.setAge(checkAge());
        jedi.setLightsaber(checkSaber());
        jedi.setJediRank(checkRank());
        jedi.setStrength(checkStrength());
        jedi.setPlanet(checkPlanet());
        Planet selectedPlanet = Planet.listOfPlanets.get(number - 1);
        selectedPlanet.addJedi(jedi);
        System.out.println("Jedi added successfully!");
        scanner.nextLine();
    }
    public String removeJedi() {
        System.out.println("Please enter the jedi's name you would like to remove: ");
        String jediName = scanner.nextLine();
        System.out.println("Please enter the planet's name you would like to remove the jedi from: ");
        String planetName = scanner.nextLine();
        for (Planet planet : Planet.listOfPlanets) {
            if (planetName.equalsIgnoreCase(planet.getName())) {
                for (Jedi jediOnPlanet : planet.getJedis()) {
                    if (jediOnPlanet.name.equalsIgnoreCase(jediName)) {
                        planet.getJedis().remove(jediOnPlanet);
                        return "Removed " + jediName + " from " + planetName;
                    }
                }
                return "There is no jedi named: " + jediName + " on " + planetName;
            }
        }
        return "There is no planet named: " + planetName;
    }
    public JediRank getRank() {
        return rank;
    }
//    private void getList() {
//        for (Planet planet : Planet.listOfPlanets) {
//            System.out.println("Jedis on " + planet.getName() + ":");
//            for (Jedi jediOnPlanet : planet.getJedis()) {
//                System.out.println(jediOnPlanet);
//            }
//        }
//    }
    @Override
    public String toString() {
        return "Jedi: " +
                "name: '" + name + '\'' +
                ", rank: " + rank +
                ", age: " + age +
                ", lightsaber: '" + lightsaber + '\'' +
                ", strength: " + getStrength();
    }
    public Jedi findJedi(String jediName) {
        for (Planet planet : Planet.listOfPlanets) {
            for (Jedi jediOnPlanet : planet.getJedis()) {
                if (jediOnPlanet.name.equalsIgnoreCase(jediName)) {
                    return jediOnPlanet;
                }
            }
        }
        return null;
    }
    public void promote(JediRank newRank, double multiplier) {
        if (rank != JediRank.GRAND_MASTER) {
            rank = newRank;
            setStrength(getStrength() + (multiplier * getStrength())); // Apply the formula to the strength
        } else {
            rank = newRank; // Update the rank without changing strength
        }
    }
    public void demote(JediRank newRank, double multiplier) {
        if (rank != JediRank.YOUNGLING) {
            rank = newRank;
            setStrength(getStrength() - (multiplier * getStrength())); // Apply the formula to the strength
        } else {
            setStrength(getStrength());
        }
    }
    public String getStrongestJedi() {
        Jedi jedi = new Jedi();
        System.out.println("Please enter the number of the planet: ");
        String planetName = jedi.checkPlanet();
        double highestStrength = 0;
        Jedi strongestJedi = null;
        boolean planetFound = false; // Flag to check if the planet was found

        for (Planet planet : Planet.listOfPlanets) {
            if (planet.getName().equalsIgnoreCase(planetName)) {
                planetFound = true; // Set the flag to true since planet is found
                for (Jedi jediOnPlanet : planet.getJedis()) {
                    if (jediOnPlanet.getStrength() > highestStrength) {
                        highestStrength = jediOnPlanet.getStrength();
                        strongestJedi = jediOnPlanet;
                    }
                }
            }
        }

        if (!planetFound) {
            return "Planet not found.";
        }

        if (strongestJedi != null) {
            return strongestJedi.toString();
        } else {
            return "No Jedi found on the specified planet or all Jedi have zero strength.";
        }
    }
    public String getYoungestJedi() {
        Jedi jedi = new Jedi();
        String planetName = jedi.checkPlanet();
        int youngestAge = Integer.MAX_VALUE;
        JediRank rank = jedi.checkRank();
        List<Jedi> youngestJedis = new ArrayList<>();
        boolean rankFound = false;
        StringBuilder builder = new StringBuilder("");
        for (Planet planet : Planet.listOfPlanets) {
            if (planet.getName().equalsIgnoreCase(planetName)) {
                for (Jedi jediOnPlanet : planet.getJedis()) {
                    if (jediOnPlanet.getRank() == rank) {
                        rankFound = true;
                        if (jediOnPlanet.getAge() <= youngestAge) {
                            youngestJedis.add(jediOnPlanet);
                            youngestAge = jediOnPlanet.getAge();
                        }
                    }
                }
                if (!rankFound) {
                    builder.append("No Jedi found with the specified rank on the planet.");
                } else if (!youngestJedis.isEmpty()) {
                    Jedi youngestJedi = Collections.min(youngestJedis, Comparator.comparing(Jedi::getName, String.CASE_INSENSITIVE_ORDER));
                    builder.append("Youngest Jedi with the specified rank: " + youngestJedi.toString());
                }
                break; // Exit the loop since we've processed the desired planet
            }
        }
        return builder.toString();
    }
    public String getMostUsedSaberColorGrandMaster() {
        Jedi jedi = new Jedi();
        String planetName = jedi.checkPlanet();
        Map<String, Integer> colorCountMap = new HashMap<>();
        Set<String> grandMasterColors = new HashSet<>();
        boolean grandMasterFound = false;

        for (Planet planet : Planet.listOfPlanets) {
            if (planet.getName().equalsIgnoreCase(planetName)) {
                for (Jedi jediOnThisPlanet : planet.getJedis()) {
                    String lightsaberColor = jediOnThisPlanet.getLightsaber();
                    colorCountMap.put(lightsaberColor, colorCountMap.getOrDefault(lightsaberColor, 0) + 1);
                    if (jediOnThisPlanet.getRank() == JediRank.GRAND_MASTER) {
                        grandMasterColors.add(lightsaberColor);
                        grandMasterFound = true;
                    }
                }
            }
        }

        if (!grandMasterFound) {
            return "No Grand Master Jedi found on the specified planet.";
        }

        int maxCount = 0;
        Set<String> mostUsedColors = new HashSet<>(); // Store colors with the same max count
        for (Map.Entry<String, Integer> entry : colorCountMap.entrySet()) {
            if (entry.getValue() >= maxCount && grandMasterColors.contains(entry.getKey())) {
                maxCount = entry.getValue();
                mostUsedColors.add(entry.getKey());//in case there are more than 1 color with same count -> show both
            }
        }
        return "Most used lightsaber color/s used by at least one Grand Master: " + mostUsedColors;
    }
    public String getMostUsedSaberColor() {
        Jedi jedi = new Jedi();
        StringBuilder result = new StringBuilder();
        String planetName = jedi.checkPlanet();
        JediRank rank = jedi.checkRank();
        boolean rankFound = false;
        Map<String, Integer> colorCountMap = new HashMap<>();

        for (Planet planet : Planet.listOfPlanets) {
            if (planet.getName().equalsIgnoreCase(planetName)) {
                for (Jedi jediOnThisPlanet : planet.getJedis()) {
                    if (jediOnThisPlanet.getRank().equals(rank)) {
                        rankFound = true;
                        String lightsaberColor = jediOnThisPlanet.getLightsaber();
                        colorCountMap.put(lightsaberColor, colorCountMap.getOrDefault(lightsaberColor, 0) + 1);
                    }
                }
            }
        }

        if (!rankFound) {
            result.append("No Jedi found with the specified rank on the planet.");
        } else {
            List<String> mostUsedColors = new ArrayList<>();
            int maxCount = 0;
            for (Map.Entry<String, Integer> entry : colorCountMap.entrySet()) {
                int count = entry.getValue();
                if (count > maxCount) {
                    maxCount = count;
                    mostUsedColors.clear(); // Clear previous entries since a higher count is found
                    mostUsedColors.add(entry.getKey());
                } else if (count == maxCount) {
                    mostUsedColors.add(entry.getKey());
                }
            }
            result.append("Most used lightsaber color/s: " + mostUsedColors);
        }
        return result.toString();
    }
    public String printJedi() {
        Scanner scanner = new Scanner(System.in);
        String name;
        System.out.println("Please enter the name of a jedi you would like to see information about: ");
        name = scanner.nextLine();
        String result = "No jedi named " + name; // Default message

        for (Planet planet : Planet.listOfPlanets) {
            for (Jedi jediOnPlanet : planet.getJedis()) {
                if (jediOnPlanet.getName().equalsIgnoreCase(name)) {
                    result = jediOnPlanet + ", planet: " + planet.getName();
                    return result; // Return the information once a match is found
                }
            }
        }
        return result; // Return the "No jedi named" message if no match is found
    }
}