package pts.model;

public class TripStopInfo {
    private int tripNumber;
    private int stopNumber;
    private int sequenceNumber;
    private String drivingTime;

    public TripStopInfo() {}

    public TripStopInfo(int tripNumber, int stopNumber, int sequenceNumber, String drivingTime) {
        this.tripNumber = tripNumber;
        this.stopNumber = stopNumber;
        this.sequenceNumber = sequenceNumber;
        this.drivingTime = drivingTime;
    }

    public int getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(int tripNumber) {
        this.tripNumber = tripNumber;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getDrivingTime() {
        return drivingTime;
    }

    public void setDrivingTime(String drivingTime) {
        this.drivingTime = drivingTime;
    }

    @Override
    public String toString() {
        return "TripStopInfo{" +
                "tripNumber=" + tripNumber +
                ", stopNumber=" + stopNumber +
                ", sequenceNumber=" + sequenceNumber +
                ", drivingTime='" + drivingTime + '\'' +
                '}';
    }
}
