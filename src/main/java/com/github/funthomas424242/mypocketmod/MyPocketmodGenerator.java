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

import net.sf.jasperreports.engine.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;

public class MyPocketmodGenerator {

    final protected Configuration configuration;


    public static void main(String[] args) throws JRException, IOException {
        final MyPocketmodGenerator report = new MyPocketmodGenerator();
        report.run();
    }

    public MyPocketmodGenerator() {
        configuration = new Configuration();
    }

    public void run() throws JRException, IOException {


        final MyPocketmod app = new MyPocketmod();
        final byte[] image1 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage1Filename()),3,configuration.getPocketmodPage1Orientation());
        final byte[] image2 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage2Filename()),1,configuration.getPocketmodPage2Orientation());
        final byte[] image3 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage3Filename()),1,configuration.getPocketmodPage3Orientation());
        final byte[] image4 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage4Filename()),1,configuration.getPocketmodPage4Orientation());
        final byte[] image5 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage5Filename()),1,configuration.getPocketmodPage5Orientation());
        final byte[] image6 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage6Filename()),3,configuration.getPocketmodPage6Orientation());
        final byte[] image7 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage7Filename()),3,configuration.getPocketmodPage7Orientation());
        final byte[] image8 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage8Filename()),3,configuration.getPocketmodPage8Orientation());


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

        // nur so im JAR möglich ranzukommen
        final InputStream inputStream = getClass().getResourceAsStream("/jrxml/Blank_A4.jrxml");
        final JasperReport jasperReport =
                JasperCompileManager.compileReport(inputStream);
        // TODO Statt wie oben zu kompilieren, können wir auch gleich das Compiliat nutzen: Blank_A4.jasper
        final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                Paths.get(configuration.getPocketmodOutputFilename()).toAbsolutePath().toString());
    }

}
