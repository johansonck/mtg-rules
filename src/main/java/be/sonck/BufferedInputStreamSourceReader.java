package be.sonck;

import org.springframework.core.io.InputStreamSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by johansonck on 04/05/15.
 */
public class BufferedInputStreamSourceReader extends BufferedReader {

    public BufferedInputStreamSourceReader(InputStreamSource source) throws IOException {
        super(new InputStreamReader(source.getInputStream(), Charset.forName("windows-1252")));
    }

    public BufferedInputStreamSourceReader(InputStreamSource source, Charset charset) throws IOException {
        super(new InputStreamReader(source.getInputStream(), charset));
    }
}
