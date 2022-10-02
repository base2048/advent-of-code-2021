import utils.Presenter;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Runner {

    public static void main(String[] args) throws IOException, ReflectiveOperationException {
        List<Class<?>> mains = getMains();

        Presenter.printMessage("~~~~~~~~~~~~~~~~~~");
        Presenter.printMessage("Let the Dive Begin");
        Presenter.printMessage("");

        for (Class<?> clazz : mains)
            clazz.getMethod("main", String[].class).invoke(null, (Object) null);

        Presenter.printMessage("Merry Christmas Everyone");
    }

    private static List<Class<?>> getMains() throws IOException {
        return Collections.list(Runner.class.getClassLoader().getResources("")).stream()
                .map(URL::getFile)
                .map(File::new)
                .flatMap(dir -> findMainClasses(dir, "").stream())
                .sorted(Comparator.comparing(Class::getName))
                .toList();
    }

    private static List<Class<?>> findMainClasses(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) return classes;

        packageName += packageName.length() > 0 ? "." : "";

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory())
                classes.addAll(findMainClasses(file, packageName + file.getName()));

            if (!file.isDirectory() && file.getName().endsWith("Main.class"))
                classes.add(getClass(file.getName(), packageName));
        }

        return classes;
    }

    private static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }
}
