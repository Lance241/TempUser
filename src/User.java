import java.util.ArrayList;

public class User implements Observer {
    private static User instance;
    private int userID;
    private String username;
    private String password;
    private ArrayList<Plant> plants;

    DatabaseAccess dbAccess = DatabaseAccess.getInstance();
    private User(String username, String password ) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public static synchronized User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<Plant> plants) {
        this.plants = plants;
    }

    @Override
    public void update() {

    }
    public static synchronized void resetInstance() {
        instance = null;
    }
}
