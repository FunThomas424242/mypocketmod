package com.github.funthomas424242.pdf2pocketmod;

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
