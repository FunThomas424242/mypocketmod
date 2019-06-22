package com.github.funthomas424242.mypocketmod;

/*-
 * #%L
 * MyPocketmod
 * %%
 * Copyright (C) 2018 - 2019 PIUG
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.testautomationguru.utility.CompareMode;
import com.testautomationguru.utility.PDFUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class MyPocketmodGeneratorIntegrationTest {

    protected MyPocketmodGenerator generator;

    @BeforeEach
    protected void setupTestfall() {
        generator = new MyPocketmodGenerator();
        Paths.get(".", "/generated/src/test/resources/").toFile().mkdirs();
    }

    @Test
    protected void main() {
        try {
            MyPocketmodGenerator.main(null);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        final String generatedPDF = Paths.get("./","generated/src/test/MyPocketmod.pdf").toAbsolutePath().toString();
        final String expectedPDF = Paths.get("./","src/test/resources/MyPocketmod.pdf").toAbsolutePath().toString();
        PDFUtil pdfUtil = new PDFUtil();
        pdfUtil.setCompareMode(CompareMode.VISUAL_MODE);
        try {
            assertTrue(pdfUtil.compare(generatedPDF, expectedPDF));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

    }

}
