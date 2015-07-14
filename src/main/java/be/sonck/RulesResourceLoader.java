package be.sonck;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;

/**
 * Created by johansonck on 03/05/15.
 */
@Deprecated
public class RulesResourceLoader {
    public InputStreamSource getResource() {
        return new ClassPathResource("MagicCompRules_20150327.txt");
    }
}
