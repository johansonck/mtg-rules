package be.sonck.mtg.rules.impl.service.initializer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by johansonck on 15/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class InitializerTest {

    @Mock
    private Initializable initializable;

    @Test
    public void testInitialize() throws Exception {
        Initializer initializer = new Initializer(InitializerTest.class, () -> initializable.initialize());

        initializer.initialize();
        initializer.initialize();
        initializer.initialize();
        initializer.initialize();
        initializer.initialize();

        verify(initializable, times(1)).initialize();
    }
}