package day19.code;

import java.util.List;

public class Scanner {

    int id;
    List<Beacon> beacons;

    Scanner(int id, List<Beacon> beacons) {
        this.id = id;
        this.beacons = beacons;
    }

    Beacon[][] getOrientations() {
        Beacon[][] orientations = new Beacon[24][beacons.size()];

        for (int i = 0; i < beacons.size(); i++) {
            int x = beacons.get(i).x;
            int y = beacons.get(i).y;
            int z = beacons.get(i).z;

            orientations[0][i] = new Beacon(x, y, z);
            orientations[1][i] = new Beacon(x, z, -y);
            orientations[2][i] = new Beacon(x, -y, -z);
            orientations[3][i] = new Beacon(x, -z, y);

            orientations[4][i] = new Beacon(-x, z, y);
            orientations[5][i] = new Beacon(-x, -y, z);
            orientations[6][i] = new Beacon(-x, -z, -y);
            orientations[7][i] = new Beacon(-x, y, -z);

            orientations[8][i] = new Beacon(y, -x, z);
            orientations[9][i] = new Beacon(y, z, x);
            orientations[10][i] = new Beacon(y, x, -z);
            orientations[11][i] = new Beacon(y, -z, -x);

            orientations[12][i] = new Beacon(-y, z, -x);
            orientations[13][i] = new Beacon(-y, x, z);
            orientations[14][i] = new Beacon(-y, -z, x);
            orientations[15][i] = new Beacon(-y, -x, -z);

            orientations[16][i] = new Beacon(z, y, -x);
            orientations[17][i] = new Beacon(z, -x, -y);
            orientations[18][i] = new Beacon(z, -y, x);
            orientations[19][i] = new Beacon(z, x, y);

            orientations[20][i] = new Beacon(-z, -x, y);
            orientations[21][i] = new Beacon(-z, -y, -x);
            orientations[22][i] = new Beacon(-z, x, -y);
            orientations[23][i] = new Beacon(-z, y, x);
        }

        return orientations;
    }
}
