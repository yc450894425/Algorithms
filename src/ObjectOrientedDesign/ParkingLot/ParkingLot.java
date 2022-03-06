package ObjectOrientedDesign.ParkingLot;

public class ParkingLot {

    private final Level[] levels;

    public ParkingLot(int numLevels, int numSpotsPerLevel, float largeRatio) {
        levels = new Level[numLevels];
        for (int i = 0; i < numLevels; i++) {
            levels[i] = new Level(numSpotsPerLevel, largeRatio);
        }
    }

    public ParkingLot(int numLevels, int numSpotsPerLevel) {
        levels = new Level[numLevels];
        for (int i = 0; i < numLevels; i++) {
            levels[i] = new Level(numSpotsPerLevel);
        }
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
                return true;
            }
        }
        return false;
    }

    public boolean leave(Vehicle v) {
        for (Level level : levels) {
            if (level.leave(v)) {
                return true;
            }
        }
        return false;
    }
}
