import java.util.Scanner;

public class Main {

    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        DatabaseAccess dbAccess = DatabaseAccess.getInstance();
        User user = User.getInstance();
        PlantInteraction plantInteraction = new PlantInteraction();
        while (true){
            System.out.println("Please choose an option:");
            System.out.println("1. Sign in");
            System.out.println("2. Create new account");
            System.out.println("3. Exit");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    System.out.println("You have chosen to sign in.");
                    dbAccess.signIn();
                    System.out.println(user.getUsername());
                    System.out.println(user.getPlants());
                    plantInteraction.promptForNewPlants(user.getUserID());
                    System.out.println(user.getPlants());

                    return;  // Break out of the loop if input is valid
                case "2":
                    System.out.println("You have chosen to create a new account.");
                    dbAccess.createNewAccount();
                    System.out.println(user.getUsername());
                    plantInteraction.promptForNewPlants(user.getUserID());
                    System.out.println(user.getPlants());
                    return;  // Break out of the loop if input is valid
                case "3":
                    System.out.println("Exiting the program.");
                    return;  // Break out of the loop if input is valid
                default:
                    System.out.println("Invalid input, please enter 1, 2, or 3.");
                    break;  // Continue looping on invalid input
            }
            User.resetInstance();
        }
    }
}