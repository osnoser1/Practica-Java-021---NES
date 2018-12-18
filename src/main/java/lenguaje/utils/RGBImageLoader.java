package lenguaje.utils;


import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import sun.awt.image.IntegerInterleavedRaster;

/**
 * This class can read chunks of RGB image data out of a file and return a
 * BufferedImage. It may use an optimized technique for loading images that
 * relies on assumptions about the default image format on Windows.
 */
public class RGBImageLoader {

    private byte[] tempBuffer_;
    private final boolean fastLoading_;

    public RGBImageLoader() {
        fastLoading_ = canUseFastLoadingTechnique();
        System.out.println("fastLoading " + fastLoading_);
    }

    private boolean canUseFastLoadingTechnique() {
        // Create an image that's compatible with the screen
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage image = gc.createCompatibleImage(100, 100, Transparency.TRANSLUCENT);

        // On windows this should be an ARGB integer packed raster. If it is then we can
        // use our optimization technique
        if (image.getType() != BufferedImage.TYPE_INT_ARGB)
            return false;

        WritableRaster raster = image.getRaster();

        if (!(raster instanceof IntegerInterleavedRaster))
            return false;

        if (!(raster.getDataBuffer() instanceof DataBufferInt))
            return false;

        if (!(image.getColorModel() instanceof DirectColorModel))
            return false;

        DirectColorModel colorModel = (DirectColorModel) image.getColorModel();

        if (!(colorModel.getColorSpace() instanceof ICC_ColorSpace)
                || colorModel.getNumComponents() != 4
                || colorModel.getAlphaMask() != 0xff000000
                || colorModel.getRedMask() != 0xff0000
                || colorModel.getGreenMask() != 0xff00
                || colorModel.getBlueMask() != 0xff)
            return false;

        return !(raster.getNumBands() != 4
                || raster.getNumDataElements() != 1
                || !(raster.getSampleModel() instanceof SinglePixelPackedSampleModel));
    }

    public BufferedImage loadImage(File file, int width, int height, long imageOffset) throws IOException {
        if (fastLoading_)
            return loadImageUsingFastTechnique(file, width, height, imageOffset);
        else
            return loadImageUsingCompatibleTechnique(file, width, height, imageOffset);
    }

    private BufferedImage loadImageUsingFastTechnique(File file, int width, int height, long imageOffset) throws IOException {
        int sizeBytes = width * height * 3;

        // Make sure buffer is big enough
        if (tempBuffer_ == null || tempBuffer_.length < sizeBytes)
            tempBuffer_ = new byte[sizeBytes];

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");

            raf.seek(imageOffset);

            int bytesRead = raf.read(tempBuffer_, 0, sizeBytes);
            if (bytesRead != sizeBytes)
                throw new IOException("Invalid byte count. Should be " + sizeBytes + " not " + bytesRead);

            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            WritableRaster raster = image.getRaster();
            DataBufferInt dataBuffer = (DataBufferInt) raster.getDataBuffer();

            addAlphaChannel(tempBuffer_, sizeBytes, dataBuffer.getData());

            return image;
        } finally {
            try {
                if (raf != null)
                    raf.close();
            } catch (Exception ex) {
            }
        }
    }

    private BufferedImage loadImageUsingCompatibleTechnique(File file, int width, int height, long imageOffset) throws IOException {
        int sizeBytes = width * height * 3;

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");

            // Lets navigate to the offset
            raf.seek(imageOffset);

            DataBufferByte dataBuffer = new DataBufferByte(sizeBytes);
            byte[] bytes = dataBuffer.getData();

            int bytesRead = raf.read(bytes, 0, sizeBytes);
            if (bytesRead != sizeBytes)
                throw new IOException("Invalid byte count. Should be " + sizeBytes + " not " + bytesRead);

            WritableRaster raster = Raster.createInterleavedRaster(dataBuffer, // dataBuffer
                    width, // width
                    height, // height
                    width * 3, // scanlineStride
                    3, // pixelStride
                    new int[]{0, 1, 2}, // bandOffsets
                    null); // location

            ColorModel colorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), // ColorSpace
                    new int[]{8, 8, 8}, // bits
                    false, // hasAlpha
                    false, // isPreMultiplied
                    ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);

            BufferedImage loadImage = new BufferedImage(colorModel, raster, false, null);

            // Convert it into a buffered image that's compatible with the current screen.
            // Not ideal creating this image twice....
            BufferedImage image = createCompatibleImage(loadImage);

            return image;
        } finally {
            try {
                if (raf != null)
                    raf.close();
            } catch (Exception ex) {
            }
        }
    }

    private BufferedImage createCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        BufferedImage newImage = gc.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return newImage;
    }

    private void addAlphaChannel(byte[] rgbBytes, int bytesLen, int[] argbInts) {
        for (int i = 0, j = 0; i < bytesLen; i += 3, j++)
            argbInts[j] = ((byte) 0xff) << 24 | // Alpha
                    (rgbBytes[i] << 16) & (0xff0000) | // Red
                    (rgbBytes[i + 1] << 8) & (0xff00) | // Green
                    (rgbBytes[i + 2]) & (0xff); // Blue
    }

}
