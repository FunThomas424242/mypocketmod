package com.github.funthomas424242.pdf2pocketmod;

import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JasperReportGeneratorTest {

    protected JasperReportGenerator generator;

    @BeforeEach
    void setupTestfall(){
        generator = new JasperReportGenerator();
    }

    @Test
    void run() {
        try {
            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}