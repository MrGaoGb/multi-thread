package com.mrgao.pdf.utils;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/3/14 11:29
 */
public class ImageUtils {

    public static BufferedImage rotateImage(BufferedImage originalImage, double degrees) {
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.round(originalImage.getWidth() * cos + originalImage.getHeight() * sin);
        int newHeight = (int) Math.round(originalImage.getHeight() * cos + originalImage.getWidth() * sin);

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        AffineTransform at = new AffineTransform();
        at.translate(newWidth / 2, newHeight / 2);
        at.rotate(radians, 0, 0);
        at.translate(-originalImage.getWidth() / 2, -originalImage.getHeight() / 2);
        AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(originalImage, rotatedImage);
        return rotatedImage;
    }
}
