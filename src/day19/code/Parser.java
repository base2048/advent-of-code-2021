package day19.code;

import utils.AbstractParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;

public class Parser extends AbstractParser {

    @Override @SuppressWarnings("unchecked")
    protected <T> T parse(InputStream is) {

        Pattern idPAT = Pattern.compile("^.*?(\\d+).*?$");
        Pattern coordsPAT = Pattern.compile("^(-?\\d+),(-?\\d+),(-?\\d+)$");

        try (java.util.Scanner scn = new java.util.Scanner(is, StandardCharsets.UTF_8)) {
            List<Scanner> scanners = new ArrayList<>();
            Matcher matcher;

            while (scn.hasNextLine()) {
                matcher = idPAT.matcher(scn.nextLine());
                matcher.find();

                int id = Integer.parseInt(matcher.group(1));
                List<Beacon> beacons = new ArrayList<>();

                for (String beacon; scn.hasNextLine() && !(beacon = scn.nextLine()).isBlank(); ) {
                    matcher = coordsPAT.matcher(beacon);
                    matcher.find();
                    beacons.add(new Beacon(
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3))));
                }

                scanners.add(new Scanner(id, beacons));
            }

            return (T) scanners;
        }
    }
}
