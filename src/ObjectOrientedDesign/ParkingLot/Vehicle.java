package ObjectOrientedDesign.ParkingLot;

enum VehicleSize {
    Compact(1),
    Large(2);
    private int size;

    public int getSize() {
        return this.size;
    }

    private VehicleSize(int size) {
        this.size = size;
    }
}

public abstract class Vehicle {
    public abstract VehicleSize getSize();
}

