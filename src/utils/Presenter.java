package utils;

import utils.Timer.*;

import java.util.Arrays;

import static utils.ANSI.*;

public class Presenter {

    private static final int WIDTH_MAIN = 42;
    private static final int WIDTH_RUNNER = 44; /* aligned to the maximum title length */
    private static final int RUNTIME_LENGTH = 5;
    private static final int SPACE = 1;
    private static final String ALINEA = "-";

    private static final boolean DISPLAY_PREPARATION_TIME = false;   /* average prep.time 0 ms -> discard display */

    public static void displayResults(Preparation prep, Summary part1, Summary part2, String title, int width) {

        boolean triggeredByRunner = Arrays.stream(Thread.currentThread().getStackTrace())
                .map(StackTraceElement::getClassName)
                .anyMatch("Runner"::equalsIgnoreCase);
        width = Math.max(triggeredByRunner ? WIDTH_RUNNER : width, title.length() + 4);

        String leftAlinea = ALINEA.repeat((width - title.length()) / 2 - SPACE);
        String rightAlinea = ALINEA.repeat((int) Math.ceil((width - title.length()) / 2.0) - SPACE);
        String bottomLine = ALINEA.repeat(width);

        String prepIntro = "Challenge preparation";
        String prepFormat = "%s%" + (width - prepIntro.length() - 3 /* " ms" */) + "d ms%n";

        String resultIntro = "Result part %s: ";
        String resultOutro = " ms";
        int resultLength = width - (resultIntro.length() - 1 /* %s -> 1|2 */) - RUNTIME_LENGTH - resultOutro.length() - SPACE /* min space between result and runtime*/;
        String resultFormat = resultIntro + "%-" + resultLength + "d %" + RUNTIME_LENGTH + "d" + resultOutro + "%n";

        System.out.printf("%s%s %s %s%s%n", ANSI_GREEN, leftAlinea, title, rightAlinea, ANSI_RESET);

        if (DISPLAY_PREPARATION_TIME)
            System.out.printf(prepFormat, prepIntro, prep.duration);

        System.out.printf(resultFormat, 1, part1.result, part1.duration);
        System.out.printf(resultFormat, 2, part2.result, part2.duration);

        System.out.printf("%s%s%s%n", ANSI_GREEN, triggeredByRunner ? "" : bottomLine, ANSI_RESET);
    }

    public static void displayResults(Preparation prep, Summary part1, Summary part2, String title) {
        displayResults(prep, part1, part2, title, WIDTH_MAIN);
    }

    public static void printMessage(String message) {
        String paddingLeft = " ".repeat((WIDTH_MAIN - message.length()) / 2);
        System.out.printf("%s%s%s%s%n", ANSI_GREEN, paddingLeft, message, ANSI_RESET);
    }
}
