package pts.model;

public class Bus {
    private String busID;
    private String model;
    private String year;

    public Bus() {}

    public Bus(String busID, String model, String year) {
        this.busID = busID;
        this.model = model;
        this.year = year;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "busID='" + busID + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
