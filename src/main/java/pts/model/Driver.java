package pts.model;

public class Driver {
    private String driverName;
    private String driverTelephoneNumber;

    public Driver() {}

    public Driver(String driverName, String driverTelephoneNumber) {
        this.driverName = driverName;
        this.driverTelephoneNumber = driverTelephoneNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverTelephoneNumber() {
        return driverTelephoneNumber;
    }

    public void setDriverTelephoneNumber(String driverTelephoneNumber) {
        this.driverTelephoneNumber = driverTelephoneNumber;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverName='" + driverName + '\'' +
                ", driverTelephoneNumber='" + driverTelephoneNumber + '\'' +
                '}';
    }
}
