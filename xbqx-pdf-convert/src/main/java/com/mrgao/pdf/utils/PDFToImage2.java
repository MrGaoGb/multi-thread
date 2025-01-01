package com.mrgao.pdf.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * @Description 优化后
 * @Author Mr.Gao
 * @Date 2025/1/1 22:11
 * <p>
 * JVM 参数配置：
 * -Xms128m -Xmx200m -XX:+HeapDumpOnOutOfMemoryError
 * </p>
 */
public class PDFToImage2 {

    private static final int DPI = 150;

    public static void main(String[] args) {
        String pdfFilePath = "D:\\Project\\xbqx\\file\\origin.pdf";
        String outputImagePath = "D:\\Project\\xbqx\\file\\image5.jpg";
        try {
            long startTime = System.currentTimeMillis();
            MemoryUtils.prinfMemoryInfo(1);
            mergeAndCompressPDF(pdfFilePath, outputImagePath);
            MemoryUtils.prinfMemoryInfo(6);
            long endTime = System.currentTimeMillis();
            System.out.println("PDF 转换为图片并合并完成，耗时：" + (endTime - startTime) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static void mergeAndCompressPDF(String pdfFilePath, String outputImagePath) throws IOException {
//        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
//            PDFRenderer pdfRenderer = new PDFRenderer(document);
//            int totalPages = document.getNumberOfPages();
//
//            // 计算合并后的图像尺寸
//            int totalHeight = 0;
//            int maxWidth = 0;
//            for (int page = 0; page < totalPages; ++page) {
//                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 200); // 降低DPI以缩小图像
//                totalHeight += bim.getHeight();
//                maxWidth = Math.max(maxWidth, bim.getWidth());
//                bim.flush(); // 释放当前页面的图像内存
//            }
//
//            MemoryUtils.prinfMemoryInfo(2);
//
//            // 创建合并后的图像
//            BufferedImage mergedImage = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
//            Graphics2D g2d = mergedImage.createGraphics();
//            g2d.setPaint(Color.WHITE);
//            g2d.fillRect(0, 0, maxWidth, totalHeight);
//
//            MemoryUtils.prinfMemoryInfo(3);
//            int currentY = 0;
//            for (int page = 0; page < totalPages; ++page) {
//                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 200); // 降低DPI以缩小图像
//                int chunkHeight = bim.getHeight();
//                int chunkWidth = bim.getWidth();
//
//                // 分块处理
//                int chunkSize = 1024; // 每块的高度
//                for (int y = 0; y < chunkHeight; y += chunkSize) {
//                    int height = Math.min(chunkSize, chunkHeight - y);
//                    BufferedImage chunk = bim.getSubimage(0, y, chunkWidth, height);
//                    g2d.drawImage(chunk, 0, currentY + y, null);
//                    chunk.flush(); // 释放当前块的图像内存
//                }
//
//                currentY += bim.getHeight();
//                bim.flush(); // 释放当前页面的图像内存
//            }
//
//            MemoryUtils.prinfMemoryInfo(4);
//            g2d.dispose();
//
//            // 压缩并保存合并后的图像
//            compressAndSaveImage(mergedImage, outputImagePath);
//            MemoryUtils.prinfMemoryInfo(5);
//        }
//    }

    private static void mergeAndCompressPDF(String pdfFilePath, String outputImagePath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            int totalPages = document.getNumberOfPages();

            // 计算合并后的图像尺寸
            int totalHeight = 0;
            int maxWidth = 0;
            for (int page = 0; page < totalPages; ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, DPI);
                totalHeight += bim.getHeight();
                maxWidth = Math.max(maxWidth, bim.getWidth());
                bim.flush(); // 释放当前页面的图像内存
            }

            MemoryUtils.prinfMemoryInfo(2);

            //DPI=300 计算后的图像尺寸,宽度:2480,高度:10521
            //DPI=200 计算后的图像尺寸,宽度:1653,高度:7014
            //DPI=180 计算后的图像尺寸,宽度:1488,高度:6312
            //DPI=150 计算后的图像尺寸,宽度:1240,高度:5259
            System.out.println("计算后的图像尺寸,宽度:" + maxWidth + ",高度:" + totalHeight);

            // 创建合并后的图像
            BufferedImage mergedImage = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = mergedImage.createGraphics();
            g2d.setPaint(Color.WHITE);
            g2d.fillRect(0, 0, maxWidth, totalHeight);

            MemoryUtils.prinfMemoryInfo(3);
            int currentY = 0;
            for (int page = 0; page < totalPages; ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, DPI);//降低DPI以缩小图像
                g2d.drawImage(bim, 0, currentY, null);
                currentY += bim.getHeight();
                bim.flush(); // 释放当前页面的图像内存
            }

            MemoryUtils.prinfMemoryInfo(4);
            g2d.dispose();

            // 压缩并保存合并后的图像
            MemoryUtils.prinfMemoryInfo(4.1);
            compressAndSaveImage(mergedImage, outputImagePath);
            MemoryUtils.prinfMemoryInfo(5);
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
            image.flush();
        }
    }

}
