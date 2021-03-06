package ObjectOrientedDesign.ParkingLot;

public class ParkingSpot {

    private final VehicleSize size;
    private Vehicle vehicle;

    public ParkingSpot(VehicleSize size) {
        this.size = size;
    }

    public boolean fit(Vehicle v) {
        return this.size.ordinal() >= v.getSize().ordinal();
    }

    public void park(Vehicle v) {
        if (this.vehicle == null && fit(v)) {
            this.vehicle = v;
        }
    }

    public void leave() {
        this.vehicle = null;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }
}
