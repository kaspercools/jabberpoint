package nl.ou.jabberpoint.domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;

public class ImageItem extends SlideItem {
    private final File imageFile;
    private Image fileAsImage;
    private double preferredHeight;
    private double preferredWidth;

    public ImageItem(File imageFile, PresentableElement underlyingContent) {
        super(underlyingContent);
        this.imageFile = imageFile;
        loadImage();
    }

    public ImageItem(File imageFile) {
        this(imageFile, null);
    }

    private void loadImage() {
        try {
            if (preferredHeight == 0) {
                fileAsImage = new Image(String.valueOf(imageFile.toURI().toURL()));
            } else {
                fileAsImage = new Image(String.valueOf(imageFile.toURI().toURL()), preferredWidth, preferredHeight, false, true);
            }
        } catch (MalformedURLException e) {
            throw new IllegalStateException("file image");
        }
    }

    @Override
    public double calculateHeight(GraphicsContext graphics, double scale) {
        return fileAsImage.getHeight() * scale;
    }

    @Override
    public double calculateWidth(GraphicsContext graphics, double scale) {
        return fileAsImage.getWidth() * scale;
    }

    public void setPreferredSize(double width, double height) {
        preferredWidth = width;
        preferredHeight = height;
        loadImage();
    }

    @Override
    protected void onDraw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation) {
        graphics.drawImage(fileAsImage, getActualX(drawLocation, scale), getActualY(drawLocation, scale), calculateWidth(graphics, scale), calculateHeight(graphics, scale));
        drawLocation.moveY(20 * scale);
    }

}
