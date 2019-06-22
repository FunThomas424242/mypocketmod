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

public class ImageHolder {

    protected byte[] image1Content;
    protected byte[] image2Content;
    protected byte[] image3Content;
    protected byte[] image4Content;
    protected byte[] image5Content;
    protected byte[] image6Content;
    protected byte[] image7Content;
    protected byte[] image8Content;

    public ImageHolder( byte[] image1Content,
                        byte[] image2Content,
                        byte[] image3Content,
                        byte[] image4Content,
                        byte[] image5Content,
                        byte[] image6Content,
                        byte[] image7Content,
                        byte[] image8Content
    ) {
        this.image1Content=image1Content;
        this.image2Content=image2Content;
        this.image3Content=image3Content;
        this.image4Content=image4Content;
        this.image5Content=image5Content;
        this.image6Content=image6Content;
        this.image7Content=image7Content;
        this.image8Content=image8Content;
    }

    public byte[] getImage1Content() {
        return image1Content;
    }

    public byte[] getImage2Content() {
        return image2Content;
    }

    public byte[] getImage3Content() {
        return image3Content;
    }

    public byte[] getImage4Content() {
        return image4Content;
    }

    public byte[] getImage5Content() {
        return image5Content;
    }

    public byte[] getImage6Content() {
        return image6Content;
    }

    public byte[] getImage7Content() {
        return image7Content;
    }

    public byte[] getImage8Content() {
        return image8Content;
    }
}
