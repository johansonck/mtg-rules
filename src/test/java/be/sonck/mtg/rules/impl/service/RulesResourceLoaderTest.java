package be.sonck.mtg.rules.impl.service;

import org.junit.Test;
import org.springframework.core.io.InputStreamSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Created by johansonck on 03/05/15.
 */
public class RulesResourceLoaderTest {

    @Test
    public void getResource() throws IOException {
        InputStreamSource source = new RulesResourceLoader().getResource();

        assertThat(source, notNullValue());
        assertThat(readFirstLine(source), startsWith("Magic: The Gathering Comprehensive Rules"));
    }

    private String readFirstLine(InputStreamSource source) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(source.getInputStream()))) {
            return reader.readLine();
        }
    }
}
