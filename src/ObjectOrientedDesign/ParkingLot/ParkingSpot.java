package ObjectOrientedDesign.ParkingLot;

/* APIs:
boolean canFit(Vehicle v)
boolean park(Vehicle v)
void leave()
public Vehicle getVehicle()
* */
public class ParkingSpot {
    private final VehicleSize size;
    private Vehicle vehicle;

    public ParkingSpot(VehicleSize size) {
        this.size = size;
    }

    public boolean fit(Vehicle v) {
        return this.size.ordinal() >= v.getSize().ordinal();
    }

    public boolean tryPark(Vehicle v) {
        if (this.vehicle == null && fit(v)) {
            this.vehicle = v;
            return true;
        }
        return false;
    }

    public void leave() {
        this.vehicle = null;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }
}
