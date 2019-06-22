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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

interface Uninitialized {
    Configuration initialize();
}

public class Configuration implements Uninitialized {

    public static final String POCKETMOD_SOURCEDIR = "mypocketmod.sourcedir";
    public static final String POCKETMOD_TARGETDIR = "mypocketmod.targetdir";
    public static final String POCKETMOD_OUTPUT_FILENAME = "mypocketmod.output.filename";
    public static final String POCKETMOD_PAGE_1_FILENAME = "mypocketmod.page1.filename";
    public static final String POCKETMOD_PAGE_2_FILENAME = "mypocketmod.page2.filename";
    public static final String POCKETMOD_PAGE_3_FILENAME = "mypocketmod.page3.filename";
    public static final String POCKETMOD_PAGE_4_FILENAME = "mypocketmod.page4.filename";
    public static final String POCKETMOD_PAGE_5_FILENAME = "mypocketmod.page5.filename";
    public static final String POCKETMOD_PAGE_6_FILENAME = "mypocketmod.page6.filename";
    public static final String POCKETMOD_PAGE_7_FILENAME = "mypocketmod.page7.filename";
    public static final String POCKETMOD_PAGE_8_FILENAME = "mypocketmod.page8.filename";
    public static final String POCKETMOD_PAGE_1_ORIENTATION = "mypocketmod.page1.orientation";
    public static final String POCKETMOD_PAGE_2_ORIENTATION = "mypocketmod.page2.orientation";
    public static final String POCKETMOD_PAGE_3_ORIENTATION = "mypocketmod.page3.orientation";
    public static final String POCKETMOD_PAGE_4_ORIENTATION = "mypocketmod.page4.orientation";
    public static final String POCKETMOD_PAGE_5_ORIENTATION = "mypocketmod.page5.orientation";
    public static final String POCKETMOD_PAGE_6_ORIENTATION = "mypocketmod.page6.orientation";
    public static final String POCKETMOD_PAGE_7_ORIENTATION = "mypocketmod.page7.orientation";
    public static final String POCKETMOD_PAGE_8_ORIENTATION = "mypocketmod.page8.orientation";

    public static final String SEITE_1_PDF = "Seite1.pdf";
    public static final String SEITE_2_PDF = "Seite2.pdf";
    public static final String SEITE_3_PDF = "Seite3.pdf";
    public static final String SEITE_4_PDF = "Seite4.pdf";
    public static final String SEITE_5_PDF = "Seite5.pdf";
    public static final String SEITE_6_PDF = "Seite6.pdf";
    public static final String SEITE_7_PDF = "Seite7.pdf";
    public static final String SEITE_8_PDF = "Seite8.pdf";
    public static final String POCKETMODE_PDF = "MyPocketmod.pdf";


    public enum Orientation {
        AUTO("auto"),
        HOCHFORMAT("hochformat"),
        QUERFORMAT("querformat");

        protected final String id;

        Orientation(final String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public static Orientation of(final String id) {
            return Orientation.valueOf(id.toUpperCase());
        }
    }

    protected final Properties configuration;

    private Configuration() {
        configuration = new Properties();
    }

    public static Uninitialized createNewConfiguration() {
        return new Configuration();
    }

    public Configuration initialize() {
        configuration.setProperty(POCKETMOD_SOURCEDIR, Paths.get("./").toAbsolutePath().toString());
        configuration.setProperty(POCKETMOD_TARGETDIR, Paths.get("./").toAbsolutePath().toString());
        configuration.setProperty(POCKETMOD_OUTPUT_FILENAME, POCKETMODE_PDF);
        configuration.setProperty(POCKETMOD_PAGE_1_FILENAME, SEITE_1_PDF);
        configuration.setProperty(POCKETMOD_PAGE_2_FILENAME, SEITE_2_PDF);
        configuration.setProperty(POCKETMOD_PAGE_3_FILENAME, SEITE_3_PDF);
        configuration.setProperty(POCKETMOD_PAGE_4_FILENAME, SEITE_4_PDF);
        configuration.setProperty(POCKETMOD_PAGE_5_FILENAME, SEITE_5_PDF);
        configuration.setProperty(POCKETMOD_PAGE_6_FILENAME, SEITE_6_PDF);
        configuration.setProperty(POCKETMOD_PAGE_7_FILENAME, SEITE_7_PDF);
        configuration.setProperty(POCKETMOD_PAGE_8_FILENAME, SEITE_8_PDF);

        configuration.setProperty(POCKETMOD_PAGE_1_ORIENTATION, Orientation.AUTO.getId());
        configuration.setProperty(POCKETMOD_PAGE_2_ORIENTATION, Orientation.AUTO.getId());
        configuration.setProperty(POCKETMOD_PAGE_3_ORIENTATION, Orientation.AUTO.getId());
        configuration.setProperty(POCKETMOD_PAGE_4_ORIENTATION, Orientation.AUTO.getId());
        configuration.setProperty(POCKETMOD_PAGE_5_ORIENTATION, Orientation.AUTO.getId());
        configuration.setProperty(POCKETMOD_PAGE_6_ORIENTATION, Orientation.AUTO.getId());
        configuration.setProperty(POCKETMOD_PAGE_7_ORIENTATION, Orientation.AUTO.getId());
        configuration.setProperty(POCKETMOD_PAGE_8_ORIENTATION, Orientation.AUTO.getId());

        final File configFile = Paths.get("./", "mypocketmod.config").toFile();
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Konfiguration mypocketmod.config konnte nicht erstellt werden");
                return this;
            }
            try {
                final FileWriter writer = new FileWriter(configFile);
                configuration.store(writer, "Configuration für MyPocketmod");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileReader reader = new FileReader(configFile)) {
            configuration.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Konfiguration mypocketmod.config konnte nicht geladen werden");
            return this;
        }
        return this;
    }

    public String getPocketmodSourcedir() {
        String dirName = configuration.getProperty(POCKETMOD_SOURCEDIR, "./");
        if (dirName.endsWith(".")) {
            dirName = dirName + "/";
        }
        return dirName;
    }

    public String getPocketmodTargetdir() {
        String dirName = configuration.getProperty(POCKETMOD_TARGETDIR, "./");
        if (dirName.endsWith(".")) {
            dirName = dirName + "/";
        }
        return dirName;
    }


    public String getPocketmodPage1Filename() {
        return getPocketmodSourcedir() + configuration.getProperty(POCKETMOD_PAGE_1_FILENAME, SEITE_1_PDF);
    }

    public String getPocketmodPage2Filename() {
        return getPocketmodSourcedir() + configuration.getProperty(POCKETMOD_PAGE_2_FILENAME, SEITE_2_PDF);
    }

    public String getPocketmodPage3Filename() {
        return getPocketmodSourcedir() + configuration.getProperty(POCKETMOD_PAGE_3_FILENAME, SEITE_3_PDF);
    }

    public String getPocketmodPage4Filename() {
        return getPocketmodSourcedir() + configuration.getProperty(POCKETMOD_PAGE_4_FILENAME, SEITE_4_PDF);
    }

    public String getPocketmodPage5Filename() {
        return getPocketmodSourcedir() + configuration.getProperty(POCKETMOD_PAGE_5_FILENAME, SEITE_5_PDF);
    }

    public String getPocketmodPage6Filename() {
        return getPocketmodSourcedir() + configuration.getProperty(POCKETMOD_PAGE_6_FILENAME, SEITE_6_PDF);
    }

    public String getPocketmodPage7Filename() {
        return getPocketmodSourcedir() + configuration.getProperty(POCKETMOD_PAGE_7_FILENAME, SEITE_7_PDF);
    }

    public String getPocketmodPage8Filename() {
        return getPocketmodSourcedir() + configuration.getProperty(POCKETMOD_PAGE_8_FILENAME, SEITE_8_PDF);
    }

    public String getPocketmodOutputFilename() {
        return getPocketmodTargetdir() + configuration.getProperty(POCKETMOD_OUTPUT_FILENAME, POCKETMODE_PDF);
    }

    public Orientation getPocketmodPage1Orientation() {
        return Orientation.of(configuration.getProperty(POCKETMOD_PAGE_1_ORIENTATION, Orientation.AUTO.getId()));
    }

    public Orientation getPocketmodPage2Orientation() {
        return Orientation.of(configuration.getProperty(POCKETMOD_PAGE_2_ORIENTATION, Orientation.AUTO.getId()));
    }

    public Orientation getPocketmodPage3Orientation() {
        return Orientation.of(configuration.getProperty(POCKETMOD_PAGE_3_ORIENTATION, Orientation.AUTO.getId()));
    }

    public Orientation getPocketmodPage4Orientation() {
        return Orientation.of(configuration.getProperty(POCKETMOD_PAGE_4_ORIENTATION, Orientation.AUTO.getId()));
    }

    public Orientation getPocketmodPage5Orientation() {
        return Orientation.of(configuration.getProperty(POCKETMOD_PAGE_5_ORIENTATION, Orientation.AUTO.getId()));
    }

    public Orientation getPocketmodPage6Orientation() {
        return Orientation.of(configuration.getProperty(POCKETMOD_PAGE_6_ORIENTATION, Orientation.AUTO.getId()));
    }

    public Orientation getPocketmodPage7Orientation() {
        return Orientation.of(configuration.getProperty(POCKETMOD_PAGE_7_ORIENTATION, Orientation.AUTO.getId()));
    }

    public Orientation getPocketmodPage8Orientation() {
        return Orientation.of(configuration.getProperty(POCKETMOD_PAGE_8_ORIENTATION, Orientation.AUTO.getId()));
    }


}


