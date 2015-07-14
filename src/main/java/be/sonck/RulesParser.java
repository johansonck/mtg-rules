package be.sonck;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by johansonck on 12/07/15.
 */
public class RulesParser {

    private static final Object LOCK = new Object();

    private static final String GLOSSARY = "Glossary";
    public static final String CREDITS = "Credits";

    private final Reader reader;

    private boolean initialized;
    private String effectiveDate;
    private Map<String, String> rules = new LinkedHashMap<>();
    private Map<String, Iterable<String>> glossary = new TreeMap<>();


    public RulesParser(Reader reader) {
        this.reader = reader;
    }

    public String getEffectiveDate() {
        initialize();

        return effectiveDate;
    }

    public Map<String, String> getRules() {
        initialize();

        return rules;
    }

    public Map<String, Iterable<String>> getGlossary() {
        initialize();

        return glossary;
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
        readRules(iterator);
        readGlosssary(iterator);
    }

    private void readGlosssary(Iterator<String> iterator) {
        while (iterator.hasNext()) {
            String line = iterator.next();

            if (isEmpty(line)) continue;
            if (CREDITS.equals(line)) break;

            String key = line;
            List<String> values = readGlossaryValues(iterator);

            glossary.put(key, values);
        }
    }

    private List<String> readGlossaryValues(Iterator<String> iterator) {
        List<String> values = new ArrayList<>();

        while (iterator.hasNext()) {
            String line = iterator.next();

            if (isEmpty(line)) break;

            values.add(line);
        }

        return values;
    }

    private void readRules(Iterator<String> iterator) {
        skipUntil(iterator, CREDITS);

        while (iterator.hasNext()) {
            String line = iterator.next();

            if (isEmpty(line)) continue;
            if (GLOSSARY.equals(line)) break;

            rules.put(determineKey(line), determineValue(line));
        }
    }

    private String determineValue(String line) {
        return line.substring(line.indexOf(' ') + 1);
    }

    private String determineKey(String line) {
        return line.substring(0, line.indexOf(' '));
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
