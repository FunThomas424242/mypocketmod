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

    final protected Configuration configuration;


    public static void main(String[] args) throws JRException, IOException {
        final JasperReportGenerator report = new JasperReportGenerator();
        report.run();
    }

    public JasperReportGenerator() {
        configuration = new Configuration();
    }

    public void run() throws JRException, IOException {


        final PDF2Pocketmod app = new PDF2Pocketmod();
        final byte[] image1 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage1Filename()));
        final byte[] image2 = app.getPDFPageAsBytes(Paths.get("/home/huluvu/git/pdf2pocketmod/src/test/resources/Seite2.pdf"));
        final byte[] image3 = app.getPDFPageAsBytes(Paths.get("/home/huluvu/git/pdf2pocketmod/src/test/resources/Seite3.pdf"));
        final byte[] image4 = app.getPDFPageAsBytes(Paths.get("/home/huluvu/git/pdf2pocketmod/src/test/resources/Seite4.pdf"));
        final byte[] image5 = app.getPDFPageAsBytes(Paths.get("/home/huluvu/git/pdf2pocketmod/src/test/resources/Seite5.pdf"));
        final byte[] image6 = app.getPDFPageAsBytes(Paths.get("/home/huluvu/git/pdf2pocketmod/src/test/resources/Seite6.pdf"));
        final byte[] image7 = app.getPDFPageAsBytes(Paths.get("/home/huluvu/git/pdf2pocketmod/src/test/resources/Seite7.pdf"));
        final byte[] image8 = app.getPDFPageAsBytes(Paths.get("/home/huluvu/git/pdf2pocketmod/src/test/resources/Seite8.pdf"));


        HashMap<String, Object> parameter =
                new HashMap<>();

        parameter.put("imageSources", new ImageHolder(image1,
                image2,
                image3,
                image4,
                image5,
                image6,
                image7,
                image8));

        final String filePath = Paths.get(".", "/src/main/resources/jrxml/Blank_A4.jrxml").toAbsolutePath().toString();
        final JasperReport jasperReport =
                JasperCompileManager.compileReport(filePath);
        final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                Paths.get(".", "/generated/src/test/resources/Pocketmod.pdf").toAbsolutePath().toString());
    }

}
