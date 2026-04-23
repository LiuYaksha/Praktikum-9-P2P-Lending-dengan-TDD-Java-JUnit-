package com.p2p;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for App class.
 */
class AppTest {

    @Test
    void appInstantiates() {
        App app = new App();
        assertNotNull(app, "App object should not be null");
    }
}
