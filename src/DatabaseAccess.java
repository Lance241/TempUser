import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Scanner;

public class DatabaseAccess implements AutoCloseable{
    private static DatabaseAccess instance;
    private Connection connection;

    private List<Observer> observers = new ArrayList<>();

    User user;

    static final Scanner scanner = new Scanner(System.in);
    public User getUser() {
        if (user == null) {
            user = User.getInstance();
        }
        return user;
    }
    private DatabaseAccess() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection to the database
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/plantproject", "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

        public static synchronized DatabaseAccess getInstance() {
            if (instance == null) {
                instance = new DatabaseAccess();
            }
            return instance;
        }

    public void modifyDatabase(String updateQuery) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(updateQuery);
            System.out.println("Database updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean queryDatabaseForUser(String username, String password) {
        String sql = "SELECT COUNT(*) AS count FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt("count") > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void signIn() {
        final int MAX_ATTEMPTS = 3;
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("Enter your username (enter 1 to exit):");
            String username = scanner.nextLine();
            if ("1".equals(username)) {
                System.out.println("Exiting the program.");
                return;
            }

            System.out.println("Enter your password (enter 1 to exit):");
            String password = scanner.nextLine();
            if ("1".equals(password)) {
                System.out.println("Exiting the program.");
                return;
            }

            if (queryDatabaseForUser(username, password)) {
                System.out.println("You have successfully signed in!");
                User user = User.getInstance();
                user.setUsername(username);
                user.setPassword(password);
                user.setUserID(getUserIdByUsernameAndPassword(user.getUsername(),user.getPassword()));
                loadUserPlants(user);
                return;
            } else {
                System.out.println("Invalid username or password. Try again.");
                attempts++;
            }
        }

    }
    public boolean isUsernameUnique(String username) {
        String sql = "SELECT COUNT(*) AS count FROM user WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt("count") == 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void createNewAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your desired username:");
        String username = scanner.nextLine();

        // Check username uniqueness
        while (!isUsernameUnique(username)) {
            System.out.println("Username already taken. Please enter a different username:");
            username = scanner.nextLine();
        }

        System.out.println("Enter a password:");
        String password = scanner.nextLine();

        // Check password validity
        while (password.trim().isEmpty()) {
            System.out.println("Password cannot be empty. Please enter a valid password:");
            password = scanner.nextLine();
        }

        int plantInventoryId = getNextPlantInventoryId();
        String sql = "INSERT INTO user (username, password, plant_inventory_ID) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, plantInventoryId);
            pstmt.executeUpdate();
            System.out.println("New account created successfully with plant_inventory_ID: " + plantInventoryId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user = User.getInstance();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserID(getUserIdByUsernameAndPassword(user.getUsername(),user.getPassword()));


    }

    private int getNextPlantInventoryId() {
        String sql = "SELECT MAX(plant_inventory_ID) AS max_id FROM user";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next() && rs.getInt("max_id") != 0) {
                return rs.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default start ID
    }
    public int getUserIdByUsernameAndPassword(String username, String password) {
        String sql = "SELECT user_ID FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no user is found or an error occurs
    }
    public void addNewPlant(int userID) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter plant name:");
        String plantName = scanner.nextLine();
        System.out.println("Enter moisture minimum: (0/100)");
        int moistureMin = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter moisture maximum: (0/100)" );
        int moistureMax = Integer.parseInt(scanner.nextLine());

        int plantNameID = getOrAddPlantName(plantName);

        String sql = "INSERT INTO plants (plant_name_ID, moisture_min, moisture_max) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, plantNameID);
            pstmt.setInt(2, moistureMin);
            pstmt.setInt(3, moistureMax);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int plantID = generatedKeys.getInt(1);
                    linkPlantToUser(userID, plantID);
                } else {
                    throw new SQLException("Creating plant failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int getOrAddPlantName(String plantName) {
        String checkSql = "SELECT plant_name_ID FROM plant_names WHERE plant_name = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, plantName);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("plant_name_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String insertSql = "INSERT INTO plant_names (plant_name) VALUES (?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, plantName);
            insertStmt.executeUpdate();
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating plant name failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indicate failure
    }
    private void linkPlantToUser(int plant_inventoryID, int plantID) {
        String linkSql = "INSERT INTO plant_link_inventory (plant_inventory_ID, plant_ID) VALUES (?, ?)";
        try (PreparedStatement linkStmt = connection.prepareStatement(linkSql)) {
            linkStmt.setInt(1, plant_inventoryID); // Assuming plant_inventory_ID is the same as userID for simplicity
            linkStmt.setInt(2, plantID);
            linkStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void loadUserPlants(User user) {
        ArrayList<Plant> plants = new ArrayList<>();
        String sql = "SELECT p.plant_ID, pn.plant_name, p.moisture_min, p.moisture_max " +
                "FROM plants p " +
                "JOIN plant_link_inventory pli ON p.plant_ID = pli.plant_ID " +
                "JOIN plant_names pn ON p.plant_name_ID = pn.plant_name_ID " +
                "WHERE pli.plant_inventory_ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, user.getUserID());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                plants.add(new Plant(rs.getInt("plant_ID"),
                        rs.getString("plant_name"),
                        rs.getInt("moisture_min"),
                        rs.getInt("moisture_max")));
            }
            user.setPlants(plants);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed");
        }
    }
}