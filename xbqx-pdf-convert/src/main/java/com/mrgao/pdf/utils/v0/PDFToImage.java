package com.mrgao.pdf.utils.v0;

import com.mrgao.pdf.utils.MemoryUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2025/1/1 14:57
 *
 * <p>
 *     JVM参数配置：
 * </p>
 */
public class PDFToImage {

    public static void main(String[] args) {
        String pdfFilePath = "D:\\Project\\xbqx\\file\\origin.pdf";
        String outputImagePath = "D:\\Project\\xbqx\\file\\image.jpg";
        try {
            long startTime = System.currentTimeMillis();
            MemoryUtils.prinfMemoryInfo();
            List<BufferedImage> images = extractImagesFromPDF(pdfFilePath);
            MemoryUtils.prinfMemoryInfo();
            BufferedImage mergedImage = mergeImages(images);
            MemoryUtils.prinfMemoryInfo();
            compressAndSaveImage(mergedImage, outputImagePath);
//            String base64Image = compressAndSaveImage(mergedImage);
//            MemoryUtils.prinfMemoryInfo();
//            saveBase64ToFile(base64Image, outputImagePath);
//            MemoryUtils.prinfMemoryInfo();
            long endTime = System.currentTimeMillis();
            System.out.println("PDF 转换为图片并合并完成，耗时：" + (endTime - startTime) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<BufferedImage> extractImagesFromPDF(String pdfFilePath) throws IOException {
        List<BufferedImage> images = new ArrayList<>();
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 150);
                images.add(bim);
            }
        }
        return images;
    }

    private static BufferedImage mergeImages(List<BufferedImage> images) {
        int totalHeight = 0;
        int maxWidth = 0;
        for (BufferedImage image : images) {
            totalHeight += image.getHeight();
            maxWidth = Math.max(maxWidth, image.getWidth());
        }

        BufferedImage mergedImage = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = mergedImage.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, maxWidth, totalHeight);

        int currentY = 0;
        for (BufferedImage image : images) {
            g2d.drawImage(image, 0, currentY, null);
            currentY += image.getHeight();
        }

        g2d.dispose();
        return mergedImage;
    }

//    private static void compressAndSaveImage(BufferedImage image, String outputImagePath) throws IOException {
//        float quality = 0.5f; // 设置压缩质量，范围 0.0 到 1.0
//        File outputFile = new File(outputImagePath);
//        ImageIO.write(image, "jpg", outputFile);
//    }


    /**
     * 将图片进行压缩并保存，返回base64编码的字符串
     *
     * @param image
     * @return
     * @throws IOException
     */
    private static String compressAndSaveImage(BufferedImage image) throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IOException("No JPEG writer found");
        }
        ImageWriter writer = writers.next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionQuality(0.1f); // 设置压缩质量，范围 0.0 到 1.0
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
            writer.setOutput(ios);
            writer.write(null, new javax.imageio.IIOImage(image, null, null), writeParam);
            // 将压缩后的图片数据转换为 Base64 编码的字符串
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } finally {
            writer.dispose();
        }
    }

    private static void saveBase64ToFile(String base64Image, String outputFilePath) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            fos.write(imageBytes);
        }
    }

    private static void compressAndSaveImage(BufferedImage image, String outputImagePath) throws IOException {
        File outputFile = new File(outputImagePath);
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IOException("No JPEG writer found");
        }

        ImageWriter writer = writers.next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionQuality(0.5f); // 设置压缩质量，范围 0.0 到 1.0

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
            writer.setOutput(ios);
            writer.write(null, new javax.imageio.IIOImage(image, null, null), writeParam);
        } finally {
            writer.dispose();
        }
    }
}
