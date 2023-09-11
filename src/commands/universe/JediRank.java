package commands.universe;

import java.util.Scanner;

public enum JediRank {
    YOUNGLING,
    INITIATE,
    PADAWAN,
    KNIGHT_ASPIRANT,
    KNIGHT,
    MASTER,
    BATTLE_MASTER,
    GRAND_MASTER;

    public double getMultiplier() {
        double multiplier = 0;
        boolean valid = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the multiplier you would like to promote/demote your Jedi's strength: ");
        do {
            if (scanner.hasNextDouble()) {
                multiplier = scanner.nextDouble();

                if (multiplier > 0) {
                    valid = true;
                } else {
                    System.out.println("Invalid input. Please enter a positive number!");
                    valid = false;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid positive number!");
                scanner.next(); // Clear the invalid input
            }
        }while (!valid);

        return multiplier;
    }
    public Object[] promote() {
        double multiplier = getMultiplier();
        int newOrdinal = Math.min(this.ordinal() + 1, JediRank.values().length - 1);

        if (newOrdinal == this.ordinal()) {
            System.out.println("Jedi has already achieved the highest rank.");
            return new Object[] { this, multiplier };
        }

        return new Object[] { JediRank.values()[newOrdinal], multiplier };
    }
    public Object[] demote() {
        double multiplier = getMultiplier();
        int newOrdinal = Math.max(this.ordinal() - 1, 0);

        if (newOrdinal == this.ordinal()) {
            System.out.println("Jedi cannot be demoted further.");
            return new Object[] {this, multiplier}; // Return the same rank
        }

        return new Object[] { JediRank.values()[newOrdinal], multiplier };
    }
}

