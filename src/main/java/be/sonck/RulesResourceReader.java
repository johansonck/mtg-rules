package be.sonck;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by johansonck on 12/07/15.
 */
@Deprecated
public class RulesResourceReader extends BufferedInputStreamSourceReader {

    public RulesResourceReader(RulesResourceLoader rulesResourceLoader) throws IOException {
        super(rulesResourceLoader.getResource(), Charset.forName("windows-1252"));
    }
}
