package ObjectOrientedDesign.ParkingLot;

public class Level {

    private static final float DEFAULT_LARGERATIO = 0.375f;
    private final ParkingSpot[] spots;

    public Level(int numOfSpots, float largeRatio) {
        spots = new ParkingSpot[numOfSpots];
        int i = 0;
        for (; i < spots.length * largeRatio; i++) {
            spots[i] = new ParkingSpot(VehicleSize.Large);
        }
        for (; i < spots.length; i++) {
            spots[i] = new ParkingSpot(VehicleSize.Compact);
        }
    }

    public Level(int numOfSpots) {
        this(numOfSpots, DEFAULT_LARGERATIO);
    }

    public boolean hasSpot(Vehicle v) {
        for (ParkingSpot spot : spots) {
            if (spot.fit(v) && spot.getVehicle() == null) {
                return true;
            }
        }
        return false;
    }

    public boolean park(Vehicle v) {
        for (ParkingSpot spot : spots) {
            if (spot.fit(v) && spot.getVehicle() == null) {
                spot.park(v);
                return true;
            }
        }
        return false;
    }

    public boolean leave(Vehicle v) {
        for (ParkingSpot spot : spots) {
            if (spot.getVehicle() == v) {
                spot.leave();
                return true;
            }
        }
        return false;
    }
}
