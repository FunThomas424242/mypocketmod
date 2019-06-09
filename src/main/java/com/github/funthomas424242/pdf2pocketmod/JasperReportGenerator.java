package com.github.funthomas424242.pdf2pocketmod;

/*-
 * #%L
 * pdf2pocketmod
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

import net.sf.jasperreports.engine.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class JasperReportGenerator {

    public static void main(String[] args) throws JRException, IOException {
        final JasperReportGenerator report = new JasperReportGenerator();
        report.run();
    }

    public void run() throws JRException, IOException {
        final PDF2Pocketmod app = new PDF2Pocketmod();
        final byte[] image1 = app.getPDFPageAsBytes(Paths.get("/home/huluvu/git/pdf2pocketmod/src/test/resources/Seite1.pdf"));


        HashMap<String, Object> parameter =
                new HashMap<>();

        parameter.put("imageSources", new ImageHolder(image1,
                null,
                null,
                null,
                null,
                null,
                null,
                null));
        parameter.put("ImageSrc2", "/home/huluvu/git/pdf2pocketmod/src/main/resources/flower1.png");
        parameter.put("ImageSrc3", "/home/huluvu/git/pdf2pocketmod/src/main/resources/flower1.png");
        parameter.put("ImageSrc4", "/home/huluvu/git/pdf2pocketmod/src/main/resources/flower1.png");
        parameter.put("ImageSrc5", "/home/huluvu/git/pdf2pocketmod/src/main/resources/flower1.png");
        parameter.put("ImageSrc6", "/home/huluvu/git/pdf2pocketmod/src/main/resources/flower1.png");
        parameter.put("ImageSrc7", "/home/huluvu/git/pdf2pocketmod/src/main/resources/flower1.png");
        parameter.put("ImageSrc8", "/home/huluvu/git/pdf2pocketmod/src/main/resources/flower1.png");

        final String filePath = Paths.get(".", "/src/main/resources/jrxml/Blank_A4.jrxml").toAbsolutePath().toString();
        final JasperReport jasperReport =
                JasperCompileManager.compileReport(filePath);
        final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                Paths.get(".","/generated/src/test/resources/Pocketmod.pdf").toAbsolutePath().toString());
    }

}
