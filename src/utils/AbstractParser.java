package utils;

import java.io.*;

public abstract class AbstractParser {

    private static final String data = "data.txt";

    public <T> T fetchData() {
        return parse(getClass().getResourceAsStream(data));
    }

    protected abstract <T> T parse(InputStream is);
}
