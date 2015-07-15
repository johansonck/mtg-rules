package be.sonck.mtg.rules.impl.service.initializer;

/**
 * Created by johansonck on 15/07/15.
 */
public class Initializer {

    private final Object lock;
    private final Initializable initializable;

    private boolean initialized;


    public Initializer(Object lock, Initializable initializable) {
        this.lock = lock;
        this.initializable = initializable;
    }

    public void initialize() {
        if (initialized) return;

        synchronized (lock) {
            if (initialized) return;

            initializable.initialize();
            initialized = true;
        }
    }
}
