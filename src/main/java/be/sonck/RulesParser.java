package be.sonck;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by johansonck on 12/07/15.
 */
public class RulesParser {

    private static final Object LOCK = new Object();

    private static final String CONTENTS = "Contents";
    private static final String GLOSSARY = "Glossary";

    private final Reader reader;

    private boolean initialized;
    private String effectiveDate;
    private List<String> tableOfContents = new ArrayList<>();


    public RulesParser(Reader reader) {
        this.reader = reader;
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
        try (LineReader lineReader = new LineReader(reader)) {
            parseLines(lineReader);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseLines(Iterable<String> lines) throws IOException {
        Iterator<String> iterator = lines.iterator();

        readEffectiveDate(iterator);
        readTableOfContents(iterator);
    }

    private void readTableOfContents(Iterator<String> iterator) {
        skipUntil(iterator, CONTENTS);

        while (iterator.hasNext()) {
            String line = iterator.next();

            if (isEmpty(line)) continue;
            if (GLOSSARY.equals(line)) break;

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
