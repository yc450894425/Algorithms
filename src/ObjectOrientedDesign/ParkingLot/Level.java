package ObjectOrientedDesign.ParkingLot;

import java.util.HashMap;

/*
one half compact spot, one half large spot;

APIs:
boolean hasSpot(Vehicle v)
boolean park(Vehicle v)
boolean leave(Vehicle v)
* */
public class Level {
    private final ParkingSpot[] spots;
    private HashMap<Vehicle, ParkingSpot> vehicleSpot;

    public Level(int numSpots, float compactRatio) {
        spots = new ParkingSpot[numSpots];
        int i = 0;
        for (; i < spots.length * compactRatio; i++) {
            spots[i] = new ParkingSpot(VehicleSize.Compact);
        }
        for (; i < spots.length; i++) {
            spots[i] = new ParkingSpot(VehicleSize.Large);
        }
        vehicleSpot = new HashMap<>();
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
            if (spot.tryPark(v)) {
                vehicleSpot.put(v, spot);
                return true;
            }
        }
        return false;
    }
    public boolean leave(Vehicle v) {
        ParkingSpot spot = vehicleSpot.get(v);
        if (spot != null) {
            spot.leave();
            vehicleSpot.remove(v);
            return true;
        }
        return false;
    }
    public ParkingSpot getSpot(Vehicle v) {
        return vehicleSpot.get(v);
    }
}
