package pts.model;

public class Trip {
    private int tripNumber;
    private String startLocationName;
    private String destinationName;

    public Trip() {}

    public Trip(int tripNumber, String startLocationName, String destinationName) {
        this.tripNumber = tripNumber;
        this.startLocationName = startLocationName;
        this.destinationName = destinationName;
    }

    public int getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(int tripNumber) {
        this.tripNumber = tripNumber;
    }

    public String getStartLocationName() {
        return startLocationName;
    }

    public void setStartLocationName(String startLocationName) {
        this.startLocationName = startLocationName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripNumber=" + tripNumber +
                ", startLocationName='" + startLocationName + '\'' +
                ", destinationName='" + destinationName + '\'' +
                '}';
    }
}
