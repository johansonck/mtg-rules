package be.sonck;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by johansonck on 12/07/15.
 */
public class RulesParser {

    private static final Object LOCK = new Object();

    private final InputStream inputStream;

    private boolean initialized;
    private String effectiveDate;
    private List<String> tableOfContents = new ArrayList<String>();


    public RulesParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getEffectiveDate() {
        initialize();

        return effectiveDate;
    }

    public Iterable<String> getTableOfContents() {
        initialize();

        return tableOfContents;
    }

    private void initialize() {
        if (initialized) return;

        synchronized (LOCK) {
            if (initialized) return;

            parseFile();
            initialized = true;
        }
    }

    private void parseFile() {
        try (LineReader reader = createLineReader()) {
            parseLines(reader);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LineReader createLineReader() {
        return new LineReader(new InputStreamReader(inputStream, Charset.forName("windows-1252")));
    }

    private void parseLines(Iterable<String> lines) throws IOException {
        Iterator<String> iterator = lines.iterator();

        readEffectiveDate(iterator);
        readTableOfContents(iterator);
    }

    private void readTableOfContents(Iterator<String> iterator) {
        skipUntil(iterator, "Contents");

        while (iterator.hasNext()) {
            String line = iterator.next();

            if (isEmpty(line)) continue;
            if ("Glossary".equals(line)) break;

            tableOfContents.add(line);
        }
    }

    private void readEffectiveDate(Iterator<String> iterator) {
        while (iterator.hasNext()) {
            String line = iterator.next();

            if (isEffectiveDate(line)) {
                effectiveDate = line;
                break;
            }
        }
    }

    private void skipUntil(Iterator<String> iterator, String expectedLine) {
        while (iterator.hasNext()) {
            String line = iterator.next();

            if (line.equals(expectedLine)) return;
        }
    }

    private boolean isEffectiveDate(String line) {
        return line.startsWith("These rules are effective");
    }
}
