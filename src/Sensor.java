public class Sensor {

    private int sensor_id;

    private int observation_interval;

    public Sensor(int sensor_id, int observation_interval) {
        this.sensor_id = sensor_id;
        this.observation_interval = observation_interval;
    }

    public int getObservation_interval() {
        return observation_interval;
    }

    public void setObservation_interval(int observation_interval) {
        this.observation_interval = observation_interval;
    }

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }
}
