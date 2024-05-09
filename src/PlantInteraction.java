import java.util.Scanner;

public class PlantInteraction {
    DatabaseAccess dbAccess;
    private Scanner scanner;

    public PlantInteraction() {
        this.dbAccess = DatabaseAccess.getInstance();
        this.scanner = new Scanner(System.in);
    }

    public void promptForNewPlants(int userID) {
        String input;
        do {
            System.out.println("Choose an option:");
            System.out.println("1. Add new plant");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            input = scanner.nextLine();

            switch (input) {
                case "1":
                    dbAccess.addNewPlant(userID);  // Assume this method handles all plant addition logic
                    break;
                case "2":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid input, please enter 1 or 2.");
                    break;
            }
        } while (!"2".equals(input));
    }
}

