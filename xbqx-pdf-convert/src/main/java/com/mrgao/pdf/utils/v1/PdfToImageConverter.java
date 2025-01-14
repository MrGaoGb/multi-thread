package com.mrgao.pdf.utils.v1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/1/14 10:48
 */
public class PdfToImageConverter {

    public static void main(String[] args) {

        String savePdfFilePath = "D:\\workspace\\xbqx-workspace\\pdfdemo\\fil66.pdf";
        String saveImageFilePath = "D:\\workspace\\xbqx-workspace\\pdfdemo\\fil66.jpg";

        // 将PDF文件转换为图片
        try (PDDocument document = PDDocument.load(new File(savePdfFilePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            int pageCount = document.getNumberOfPages();
            int width = 0;
            int height = 0;

            // 计算合并后的图片尺寸
            for (int page = 0; page < pageCount; ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 180);
                width = Math.max(width, bim.getWidth());
                height += bim.getHeight();
            }

            // 创建合并后的图片
            BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = combinedImage.createGraphics();

            int currentHeight = 0;
            for (int page = 0; page < pageCount; ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 180);
                g2d.drawImage(bim, 0, currentHeight, null);
                currentHeight += bim.getHeight();
            }

            g2d.dispose();

            // 压缩图片
            File outputFile = new File(saveImageFilePath);
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile);
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.3f); // 调整压缩质量

            writer.write(null, new IIOImage(combinedImage, null, null), param);
            ios.close();
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static final float COMPRESSION_QUALITY = 0.3f;

    public static void convertPdfToImage(String savePdfFilePath, String saveImageFilePath) {
        if (savePdfFilePath == null || savePdfFilePath.isEmpty() || saveImageFilePath == null || saveImageFilePath.isEmpty()) {
            throw new IllegalArgumentException("File paths cannot be null or empty.");
        }

        File pdfFile = new File(savePdfFilePath);
        File imageFile = new File(saveImageFilePath);

        if (!pdfFile.exists() || !pdfFile.isFile()) {
            throw new IllegalArgumentException("Invalid PDF file path: " + savePdfFilePath);
        }

        List<BufferedImage> pageImages = new ArrayList<>();
        int width = 0;
        int height = 0;

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            int pageCount = document.getNumberOfPages();

            if (pageCount == 0) {
                throw new IllegalArgumentException("The provided PDF file is empty.");
            }

            for (int page = 0; page < pageCount; ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 180);
                pageImages.add(bim);
                width = Math.max(width, bim.getWidth());
                height += bim.getHeight();
            }

            // 创建合并后的图片
            BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = combinedImage.createGraphics();

            int currentHeight = 0;
            for (BufferedImage bim : pageImages) {
                g2d.drawImage(bim, 0, currentHeight, null);
                currentHeight += bim.getHeight();
                bim.flush();
            }

            g2d.dispose();

            // 压缩图片
            try (ImageOutputStream ios = ImageIO.createImageOutputStream(imageFile)) {
                ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
                writer.setOutput(ios);
                ImageWriteParam param = writer.getDefaultWriteParam();
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(COMPRESSION_QUALITY);

                writer.write(null, new IIOImage(combinedImage, null, null), param);
                writer.dispose();
            }
        } catch (IOException e) {
            System.err.println("Error converting PDF to image: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
