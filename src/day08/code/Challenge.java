package day08.code;

import utils.Solvable;

import java.util.*;
import java.util.stream.*;

public class Challenge implements Solvable {

    private final int MAX_SEGMENT_COUNT = 7;
    private final List<Display> displays;

    public Challenge(List<Display> displays) {
        this.displays = displays;
    }

    @Override
    public long solvePart1() {
        int[] segmentCounts = new int[MAX_SEGMENT_COUNT + 1];

        for (int digit : Digit.properDigitSegmentMapToValue.keySet())
            segmentCounts[Integer.bitCount(digit)]++;

        return displays.stream()
                .flatMap(display -> display.outputs.stream())
                .map(Digit::getSegmentCount)
                .filter(count -> segmentCounts[count] == 1)
                .count();
    }

    @Override
    public long solvePart2() {
        int result = 0;

        for (Display display : displays) {

            List<Digit> samples = display.samples;
            List<Digit> outputs = display.outputs;

            Map<Integer, Integer> mappings = computeMappings(samples);

            int outputValue = 0;

            for (Digit digit : outputs) {
                int correspondingProperSegmentMap = decodeOutput(digit.segmentMap, mappings);
                outputValue *= 10;
                outputValue += Digit.properDigitSegmentMapToValue.get(correspondingProperSegmentMap);
            }

            result += outputValue;
        }

        return result;
    }

    private Map<Integer, Integer> computeMappings(List<Digit> samples) {
        /* This map will eventually assign each mis-wired segment to its proper counterpart.
           Initially, each mis-wired segment is mapped to all possible proper segments. */
        Map<Integer, Integer> mappings = Stream.iterate(1, pos -> pos < 1 << MAX_SEGMENT_COUNT, pos -> pos << 1)
                .collect(Collectors.toMap(segment -> segment, segment -> 127));

        /* All group members have the same number of active segments. Yes, we could shift this
           operation outside the loop within which this method is called, or even more performant:
           we could hardcode the identifiers. Anyhow, we'll leave it that way. */
        List<Integer> groupIdentifiers = samples.stream().map(Digit::getSegmentCount).distinct().toList();

        for (int identifier : groupIdentifiers) {

            List<Integer> samplesGroup = samples.stream().filter(digit -> digit.getSegmentCount() == identifier).map(Digit::getSegmentMap).toList();
            List<Integer> propersGroup = Digit.properDigitSegmentMapToValue.keySet().stream().filter(digit -> Integer.bitCount(digit) == identifier).toList();

            /* Identification of all common active segments within a group. */
            int samplesCommonActiveSegments = samplesGroup.stream().reduce(127, (common, sample) -> common &= sample);
            int propersCommonActiveSegments = propersGroup.stream().reduce(127, (common, proper) -> common &= proper);

            /* Discard all impossible wiring. */
            keepAndDiscard(mappings, samplesCommonActiveSegments, propersCommonActiveSegments);

            /* Identification of all non-common active segments within a group. */
            int samplesAllActiveSegments = samplesGroup.stream().reduce(0, (actives, sample) -> actives |= sample);
            int propersAllActiveSegments = propersGroup.stream().reduce(0, (actives, proper) -> actives |= proper);

            int samplesNonCommonActiveSegments = samplesAllActiveSegments ^ samplesCommonActiveSegments;
            int propersNonCommonActiveSegments = propersAllActiveSegments ^ propersCommonActiveSegments;

            keepAndDiscard(mappings, samplesNonCommonActiveSegments, propersNonCommonActiveSegments);

            /* As a final step within a common group, we identify all inactive segments
             * and use the result to refine the wiring one last time. Applying a not-operation
             * is possible, since we ignore all further bits above the highest possible segment. */
            int samplesAllInactiveSegments = ~samplesAllActiveSegments;
            int propersAllInactiveSegments = ~propersAllActiveSegments;

            keepAndDiscard(mappings, samplesAllInactiveSegments, propersAllInactiveSegments);
        }

        return mappings;
    }

    private void keepAndDiscard(Map<Integer, Integer> mappings, int samplesSegments, int propersSegments) {
        for (int pos = 0; pos < MAX_SEGMENT_COUNT; pos++)
            if ((samplesSegments >>> pos & 1) == 1)
                mappings.compute(1 << pos, (k, v) -> v & propersSegments);
    }

    private int decodeOutput(int mixedUpSegmentMap, Map<Integer, Integer> mappings) {
        int correspondingProperSegmentMap = 0;

        /* Build up the corresponding proper segment map. */
        for (int pos = 0; pos < MAX_SEGMENT_COUNT; pos++)
            if ((mixedUpSegmentMap >>> pos & 1) == 1)
                correspondingProperSegmentMap |= mappings.get(1 << pos);

        return correspondingProperSegmentMap;
    }
}
