package commands.universe;

import java.util.*;

public class Planet {
    String name;
    public static ArrayList<Planet> listOfPlanets = new ArrayList<>();
    private List<Jedi> jedis;

    public Planet() {
        this.jedis = new ArrayList<>();
    }

    public List<Jedi> getJedis() {
        return jedis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "planet name: " + name;
    }

    protected boolean isNameUnique(String name) {
        for (Planet planet : listOfPlanets) { // for each of the planet's in the list
            if (planet.getName().trim().equalsIgnoreCase(name.trim())) {
                return false; // Name already exists, not unique
            }
        }
        return true; // Name is unique
    }

    private boolean isNameValid(String name) {
        return !name.isBlank() && !Character.isDigit(name.charAt(0)) && !name.contains(" ");
    }

    private String checkName() {
        Scanner scanner = new Scanner(System.in);
        String result = "";
        do {
            System.out.println("Please enter the name of your planet: ");
            name = scanner.nextLine();
            if (isNameValid(name)) {
                if (isNameUnique(name)) {
                    result = name;
                } else {
                    System.out.println("UUPS! A planet with this name already exists!");
                    result = checkName();
                }
            }else{
                System.out.println("The name should not contain spaces!");
            }
        } while (!isNameValid(name));
        return result;
    }

    public void addPlanet() {
        Planet planet = new Planet();
        planet.setName(checkName());
        listOfPlanets.add(planet);
        System.out.println("Planet added successfully!");
        //System.out.println(listOfPlanets);
    }
    public void addJedi(Jedi jedi) {
        jedis.add(jedi);
    }
    private int compareJediNamesLexicographically(Jedi jedi1, Jedi jedi2) {
        String name1 = jedi1.getName().toLowerCase();
        String name2 = jedi2.getName().toLowerCase();

        // Extract numeric parts from the names
        String[] parts1 = name1.split("(?<=\\D)(?=\\d)");
        String[] parts2 = name2.split("(?<=\\D)(?=\\d)");

        // Compare non-numeric parts lexicographically
        int result = parts1[0].compareTo(parts2[0]);

        // If non-numeric parts are the same, compare numeric parts as integers
        if (result == 0 && parts1.length > 1 && parts2.length > 1) {
            int number1 = Integer.parseInt(parts1[1]);
            int number2 = Integer.parseInt(parts2[1]);
            result = Integer.compare(number1, number2);
        }

        return result;
    }
    public String printPlanet() {
        System.out.println(listOfPlanets);
        System.out.println("Please enter the name of the planet you would like to see the jedis on: ");
        Scanner scanner = new Scanner(System.in);
        String planetName = scanner.nextLine();
        StringBuilder result = new StringBuilder();
        boolean planetFound = false;
        for (Planet planet : Planet.listOfPlanets) {
            if (planet.getName().equalsIgnoreCase(planetName)) {
                planetFound = true;
                Collections.sort(planet.getJedis(), new Comparator<Jedi>() {
                    @Override
                    public int compare(Jedi jedi1, Jedi jedi2) {
                        return jedi1.getRank().compareTo(jedi2.getRank());
                    }
                });

                result.append("Sorted by Rank:\n");
                for (Jedi jedi : planet.getJedis()) {
                    result.append(jedi).append("\n");
                }
                Collections.sort(planet.getJedis(), new Comparator<Jedi>() {
                    @Override
                    public int compare(Jedi jedi1, Jedi jedi2) {
                        return compareJediNamesLexicographically(jedi1, jedi2);
                    }
                });

                // Print the sorted Jedi list by name
                result.append("\nSorted by Name:\n");
                for (Jedi jedi : planet.getJedis()) {
                    result.append(jedi).append("\n");
                }
                break;
            }
        }
        // If the specified planet is not found
        if (!planetFound) {
            System.out.println("Planet not found!");
        }
        return result.toString();
    }
    public String printTwoPlanetsJedis(String planet1, String planet2) {
        boolean planet1Found = false;
        boolean planet2Found = false;
        List<Jedi> allJedis = new ArrayList<>(); // Create a list to hold all Jedi from both planets

        for (Planet planet : Planet.listOfPlanets) {
            if (planet.getName().equalsIgnoreCase(planet1)) {
                planet1Found = true;
                allJedis.addAll(planet.getJedis()); // Add all Jedi from planet1 to the list
            } else if (planet.getName().equalsIgnoreCase(planet2)) {
                planet2Found = true;
                allJedis.addAll(planet.getJedis()); // Add all Jedi from planet2 to the list
            }
        }

        if (!planet1Found && !planet2Found) {
            return "Both planets " + planet1 + " and " + planet2 + " not found!";
        } else if (!planet1Found) {
            return "Planet " + planet1 + " not found!";
        } else if (!planet2Found) {
            return "Planet " + planet2 + " not found!";
        }

        // Sort the combined list of Jedi by name using your custom method
        Collections.sort(allJedis, new Comparator<Jedi>() {
            @Override
            public int compare(Jedi jedi1, Jedi jedi2) {
                return compareJediNamesLexicographically(jedi1, jedi2);
            }
        });

        StringBuilder result = new StringBuilder();
        for (Jedi jedi : allJedis) {
            result.append(jedi).append("\n");
        }

        return result.toString();
    }
}