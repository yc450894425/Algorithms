package ObjectOrientedDesign.ParkingLot;

import java.util.Arrays;
import java.util.HashMap;

/* APIs:
boolean hasSpot(Vehicle v)
boolean park(Vehicle v)
boolean leave(Vehicle v)
ParkingSpot getSpot(Vehicle v)
* */
public class ParkingLot {
    private final Level[] levels;
    private HashMap<Vehicle, Level> vehicleLevel;

    public ParkingLot(int numLevels, int numSpotsPerLevel, float compactRatio) {
        levels = new Level[numLevels];
        for (int i = 0; i < numLevels; i++) {
            levels[i] = new Level(numSpotsPerLevel, compactRatio);
        }
        vehicleLevel = new HashMap<>();
    }

    public boolean hasSpot(Vehicle v) {
        for (Level level : levels) {
            if (level.hasSpot(v)) {
                return true;
            }
        }
        return false;
    }

    public boolean park(Vehicle v) {
        for (Level level : levels) {
            if (level.park(v)) {
                vehicleLevel.put(v, level);
                return true;
            }
        }
        return false;
    }

    public boolean leave(Vehicle v) {
        Level level = vehicleLevel.get(v);
        if (level != null) {
            level.leave(v);
            vehicleLevel.remove(v);
            return true;
        }
        return false;
    }

    public ParkingSpot getSpot(Vehicle v) {
        Level level = vehicleLevel.get(v);
        if (level != null) {
            return level.getSpot(v);
        }
        return null;
    }
}
