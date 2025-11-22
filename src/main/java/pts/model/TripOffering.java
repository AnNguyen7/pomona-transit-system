package pts.model;

public class TripOffering {
    private int tripNumber;
    private String date;
    private String scheduledStartTime;
    private String scheduledArrivalTime;
    private String driverName;
    private String busID;

    public TripOffering() {}

    public TripOffering(int tripNumber, String date, String scheduledStartTime,
                        String scheduledArrivalTime, String driverName, String busID) {
        this.tripNumber = tripNumber;
        this.date = date;
        this.scheduledStartTime = scheduledStartTime;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.driverName = driverName;
        this.busID = busID;
    }

    public int getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(int tripNumber) {
        this.tripNumber = tripNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScheduledStartTime() {
        return scheduledStartTime;
    }

    public void setScheduledStartTime(String scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    public String getScheduledArrivalTime() {
        return scheduledArrivalTime;
    }

    public void setScheduledArrivalTime(String scheduledArrivalTime) {
        this.scheduledArrivalTime = scheduledArrivalTime;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    @Override
    public String toString() {
        return "TripOffering{" +
                "tripNumber=" + tripNumber +
                ", date='" + date + '\'' +
                ", scheduledStartTime='" + scheduledStartTime + '\'' +
                ", scheduledArrivalTime='" + scheduledArrivalTime + '\'' +
                ", driverName='" + driverName + '\'' +
                ", busID='" + busID + '\'' +
                '}';
    }
}
