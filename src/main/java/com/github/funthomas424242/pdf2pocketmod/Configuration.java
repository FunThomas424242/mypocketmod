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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {

    public static final String POCKETMOD_SOURCEDIR = "pocketmod.sourcedir";
    public static final String POCKETMOD_TARGETDIR = "pocketmod.targetdir";
    public static final String POCKETMOD_PAGE_1_FILENAME = "pocketmod.page1.filename";
    public static final String POCKETMOD_PAGE_2_FILENAME = "pocketmod.page2.filename";
    public static final String POCKETMOD_PAGE_3_FILENAME = "pocketmod.page3.filename";
    public static final String POCKETMOD_PAGE_4_FILENAME = "pocketmod.page4.filename";
    public static final String POCKETMOD_PAGE_5_FILENAME = "pocketmod.page5.filename";
    public static final String POCKETMOD_PAGE_6_FILENAME = "pocketmod.page6.filename";
    public static final String POCKETMOD_PAGE_7_FILENAME = "pocketmod.page7.filename";
    public static final String POCKETMOD_PAGE_8_FILENAME = "pocketmod.page8.filename";
    public static final String SEITE_1_PDF = "Seite1.pdf";
    public static final String SEITE_2_PDF = "Seite2.pdf";
    public static final String SEITE_3_PDF = "Seite3.pdf";
    public static final String SEITE_4_PDF = "Seite4.pdf";
    public static final String SEITE_5_PDF = "Seite5.pdf";
    public static final String SEITE_6_PDF = "Seite6.pdf";
    public static final String SEITE_7_PDF = "Seite7.pdf";
    public static final String SEITE_8_PDF = "Seite8.pdf";

    final Properties configuration;

    public Configuration() {
        configuration = new Properties();
        initialize();
    }

    protected void initialize() {
        configuration.setProperty(POCKETMOD_SOURCEDIR, Paths.get("./").toAbsolutePath().toString());
        configuration.setProperty(POCKETMOD_TARGETDIR, Paths.get("./").toAbsolutePath().toString());
        configuration.setProperty(POCKETMOD_PAGE_1_FILENAME, SEITE_1_PDF);
        configuration.setProperty(POCKETMOD_PAGE_2_FILENAME, SEITE_2_PDF);
        configuration.setProperty(POCKETMOD_PAGE_3_FILENAME, SEITE_3_PDF);
        configuration.setProperty(POCKETMOD_PAGE_4_FILENAME, SEITE_4_PDF);
        configuration.setProperty(POCKETMOD_PAGE_5_FILENAME, SEITE_5_PDF);
        configuration.setProperty(POCKETMOD_PAGE_6_FILENAME, SEITE_6_PDF);
        configuration.setProperty(POCKETMOD_PAGE_7_FILENAME, SEITE_7_PDF);
        configuration.setProperty(POCKETMOD_PAGE_8_FILENAME, SEITE_8_PDF);

        final File configFile = Paths.get("./", "pocketmod.config").toFile();
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            boolean created;
            try {
                created = !configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Konfiguration pocketmod.config konnte nicht erstellt werden");
                return;
            }
            try {
                final FileWriter writer = new FileWriter(configFile);
                configuration.store(writer, "Configuration für PDF2Pocketmod");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileReader reader = new FileReader(configFile)) {
            configuration.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Konfiguration pocketmod.config konnte nicht geladen werden");
            return;
        }
    }

    public String getPocketmodSourcedir(){
        String dirName= configuration.getProperty(POCKETMOD_SOURCEDIR,"./");
        if( dirName.endsWith(".")){
            dirName=dirName+"/";
        }
        return dirName;
    }

    public String getPocketmodPage1Filename(){
        return getPocketmodSourcedir()+configuration.getProperty(POCKETMOD_PAGE_1_FILENAME,SEITE_1_PDF);
    }


}


