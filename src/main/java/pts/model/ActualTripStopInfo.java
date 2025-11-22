package pts.model;

public class ActualTripStopInfo {
    private int tripNumber;
    private String date;
    private String scheduledStartTime;
    private int stopNumber;
    private String scheduledArrivalTime;
    private String actualStartTime;
    private String actualArrivalTime;
    private int numberOfPassengerIn;
    private int numberOfPassengerOut;

    public ActualTripStopInfo() {}

    public ActualTripStopInfo(int tripNumber, String date, String scheduledStartTime,
                              int stopNumber, String scheduledArrivalTime, String actualStartTime,
                              String actualArrivalTime, int numberOfPassengerIn, int numberOfPassengerOut) {
        this.tripNumber = tripNumber;
        this.date = date;
        this.scheduledStartTime = scheduledStartTime;
        this.stopNumber = stopNumber;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.actualStartTime = actualStartTime;
        this.actualArrivalTime = actualArrivalTime;
        this.numberOfPassengerIn = numberOfPassengerIn;
        this.numberOfPassengerOut = numberOfPassengerOut;
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

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public String getScheduledArrivalTime() {
        return scheduledArrivalTime;
    }

    public void setScheduledArrivalTime(String scheduledArrivalTime) {
        this.scheduledArrivalTime = scheduledArrivalTime;
    }

    public String getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(String actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public String getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(String actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public int getNumberOfPassengerIn() {
        return numberOfPassengerIn;
    }

    public void setNumberOfPassengerIn(int numberOfPassengerIn) {
        this.numberOfPassengerIn = numberOfPassengerIn;
    }

    public int getNumberOfPassengerOut() {
        return numberOfPassengerOut;
    }

    public void setNumberOfPassengerOut(int numberOfPassengerOut) {
        this.numberOfPassengerOut = numberOfPassengerOut;
    }

    @Override
    public String toString() {
        return "ActualTripStopInfo{" +
                "tripNumber=" + tripNumber +
                ", date='" + date + '\'' +
                ", scheduledStartTime='" + scheduledStartTime + '\'' +
                ", stopNumber=" + stopNumber +
                ", scheduledArrivalTime='" + scheduledArrivalTime + '\'' +
                ", actualStartTime='" + actualStartTime + '\'' +
                ", actualArrivalTime='" + actualArrivalTime + '\'' +
                ", numberOfPassengerIn=" + numberOfPassengerIn +
                ", numberOfPassengerOut=" + numberOfPassengerOut +
                '}';
    }
}
