package day19.code;

import utils.Solvable;

import java.util.*;

public class Challenge implements Solvable {

    private final List<Scanner> scanners;
    private final Set<Beacon> beaconsMap = new HashSet<>();
    private final int[][] scannersCoords;

    public Challenge(List<Scanner> scanners) {
        this.scanners = scanners;
        this.scannersCoords = new int[scanners.size()][];
    }

    @Override
    public long solvePart1() {
        Scanner base = scanners.remove(0);
        scannersCoords[base.id] = new int[]{0, 0, 0};
        beaconsMap.addAll(base.beacons);

        while (!scanners.isEmpty())
            for (Scanner scanner : scanners)
                if (extendMap(scanner)) break;

        return beaconsMap.size();
    }

    @Override
    public long solvePart2() {
        if (beaconsMap.isEmpty()) this.solvePart1();
        int manhattan = 0;

        for (int i = 0; i < scannersCoords.length - 1; i++) {
            for (int j = i + 1; j < scannersCoords.length; j++) {

                int dx = Math.abs(scannersCoords[i][0] - scannersCoords[j][0]);
                int dy = Math.abs(scannersCoords[i][1] - scannersCoords[j][1]);
                int dz = Math.abs(scannersCoords[i][2] - scannersCoords[j][2]);

                manhattan = Math.max(dx + dy + dz, manhattan);
            }
        }

        return manhattan;
    }

    private boolean extendMap(Scanner addition) {
        for (Beacon[] orientation : addition.getOrientations()) {
            Map<Long, Integer> deltas = new HashMap<>();

            for (Beacon tryBeacon : orientation) {
                for (Beacon mapBeacon : beaconsMap) {
                    long dx = mapBeacon.x - tryBeacon.x;
                    long dy = mapBeacon.y - tryBeacon.y;
                    long dz = mapBeacon.z - tryBeacon.z;

                    long delta = dx << 32 | dy << 16 & 0xFFFF0000L | dz & 0xFFFFL;

                    if (deltas.merge(delta, 1, Integer::sum) == 12) {
                        for (Beacon addBeacon : orientation) {
                            addBeacon.x += dx;
                            addBeacon.y += dy;
                            addBeacon.z += dz;
                            beaconsMap.add(addBeacon);
                        }

                        scannersCoords[addition.id] = new int[]{(int) dx, (int) dy, (int) dz};
                        scanners.remove(addition);
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
