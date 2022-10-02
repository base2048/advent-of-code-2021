package day08.code;

import java.util.Map;

public class Digit {

    /*
     *      proper digit segments:
     *
     *        aaa       a = 0b0000001
     *      b     c     b = 0b0000010
     *      b     c     c = 0b0000100
     *        ddd       d = 0b0001000
     *      e     f     e = 0b0010000
     *      e     f     f = 0b0100000
     *        ggg       g = 0b1000000
     */
    static final Map<Integer, Integer> properDigitSegmentMapToValue = Map.of(
            0b1110111, 0,
            0b0100100, 1,
            0b1011101, 2,
            0b1101101, 3,
            0b0101110, 4,
            0b1101011, 5,
            0b1111011, 6,
            0b0100101, 7,
            0b1111111, 8,
            0b1101111, 9
    );

    int segmentMap;
    int segmentCount;

    Digit(int segmentMap) {
        this.segmentMap = segmentMap;
        this.segmentCount = Integer.bitCount(segmentMap);
    }

    int getSegmentMap() {
        return segmentMap;
    }

    int getSegmentCount() {
        return segmentCount;
    }
}
