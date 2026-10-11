package com.rit.library;

import org.junit.jupiter.api.Test;

class LibraryApplicationTest {

    @Test
    void mainStartsApplicationWithoutWebServer() {
        LibraryApplication.main(new String[] {
                "--spring.main.web-application-type=none",
                "--spring.main.banner-mode=off"
        });
    }
}