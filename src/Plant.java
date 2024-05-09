public class Plant {
    private int plantID;
    private String plantName;
    private int moisture_Min;

    private int moisture_Max;

    private int moisture_Current;

    private Sensor sensor;

    public Plant(int plantID, String plantName, int moisture_Min, int moisture_Max) {
        this.plantID = plantID;
        this.plantName = plantName;
        this.moisture_Min = moisture_Min;
        this.moisture_Max = moisture_Max;
    }
    public Plant(int plantID, String plantName, int moisture_Min, int moisture_Max, int moisture_Current) {
        this.plantID = plantID;
        this.plantName = plantName;
        this.moisture_Min = moisture_Min;
        this.moisture_Max = moisture_Max;
        this.moisture_Current = moisture_Current;
    }
    public Plant(int plantID, String plantName, int moisture_Min, int moisture_Max, int moisture_Current, Sensor sensor) {
        this.plantID = plantID;
        this.plantName = plantName;
        this.moisture_Min = moisture_Min;
        this.moisture_Max = moisture_Max;
        this.moisture_Current = moisture_Current;
    }


    public int getPlantID() {
        return plantID;
    }

    public void setPlantID(int plantID) {
        this.plantID = plantID;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public int getMoisture_Min() {
        return moisture_Min;
    }

    public void setMoisture_Min(int moisture_Min) {
        this.moisture_Min = moisture_Min;
    }

    public int getMoisture_Max() {
        return moisture_Max;
    }

    public void setMoisture_Max(int moisture_Max) {
        this.moisture_Max = moisture_Max;
    }

    public int getMoisture_Current() {
        return moisture_Current;
    }

    public void setMoisture_Current(int moisture_Current) {
        this.moisture_Current = moisture_Current;
    }
}
