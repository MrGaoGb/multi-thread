package com.mrgao.pdf.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/3/14 14:26
 */
public class PDFConvertImageUtils {

    /**
     * 图片压缩质量比例
     */
    private static final float COMPRESS_QUALITY = 0.3f;
    /**
     * 72 DPI：适用于屏幕显示（如网页、电子设备等）。
     * 150 DPI：适用于一般的文档处理或中等质量的打印。
     * 300 DPI：适用于高质量打印（如照片、出版物等）
     */
    private static final float DPI = 150;

    /**
     * 填充PDF表单并添加电子签名
     *
     * @param pdfPath
     * @param signatureData
     * @param formData
     * @return
     * @throws IOException
     */
    public static byte[] fillFormV1(String pdfPath, byte[] signatureData, Map<String, String> formData, Integer x, Integer y, Integer width, Integer height) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             InputStream inputStream = new ByteArrayInputStream(signatureData);
             PDDocument document = PDDocument.load(new File(pdfPath))) {

            PDPage page = document.getPage(document.getNumberOfPages() - 1); // 获取最后一页

            // 获取电子签名图片
            BufferedImage originalImage = ImageIO.read(inputStream);
            BufferedImage rotatedImage = originalImage;
            if (originalImage.getHeight() > originalImage.getWidth()) {
                rotatedImage = ImageUtils.rotateImage(originalImage, 90); // 旋转90度
            }

            PDImageXObject pdImage = LosslessFactory.createFromImage(document, rotatedImage);

            // 获取PDF表单域
            PDDocumentCatalog docCatalog = document.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();

            // 填充表单域
            for (Map.Entry<String, String> entry : formData.entrySet()) {
                PDField field = acroForm.getField(entry.getKey());
                if (field != null) {
                    try {
                        if (field instanceof PDTextField) {
                            // 普通文本字段
                            PDTextField textField = (PDTextField) field;
                            textField.setDefaultAppearance("/Helv 9 Tf 0 g"); // 使用Helvetica字体
                            textField.setValue(entry.getValue());
                            textField.setReadOnly(true);
                        } else if (field instanceof PDCheckBox) {
                            // 多选框
                            PDCheckBox checkBox = (PDCheckBox) field;
                            checkBox.check(); // 假设值为 "Yes" 时选中
                            field.setReadOnly(true); // 设置字段为只读，生成不可编辑的PDF
                        }
                    } catch (Exception e) {
                        System.err.println("设置字段值时发生错误: " + entry.getKey());
                        e.printStackTrace();
                    }
                }
            }


            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                contentStream.drawImage(pdImage, x, y, width, height); // 在坐标(100, 100)处插入图片，宽度100，高度50
            }
            document.save(outputStream);

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("PDF表单填充信息失败!");
        }
    }

    public static byte[] fillForm(String pdfPath, byte[] signatureData, Map<String, String> formData, Integer x, Integer y, Integer width, Integer height) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             InputStream inputStream = new ByteArrayInputStream(signatureData);
             PDDocument document = PDDocument.load(new File(pdfPath))) {

            PDPage page = document.getPage(document.getNumberOfPages() - 1); // 获取最后一页

            // 获取电子签名图片
            BufferedImage originalImage = ImageIO.read(inputStream);
            BufferedImage rotatedImage = originalImage;
            if (originalImage.getHeight() > originalImage.getWidth()) {
                rotatedImage = ImageUtils.rotateImage(originalImage, 90); // 旋转90度
            }

            PDImageXObject pdImage = LosslessFactory.createFromImage(document, rotatedImage);

            // 获取PDF表单域
            PDDocumentCatalog docCatalog = document.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();

            // 填充表单域
            for (Map.Entry<String, String> entry : formData.entrySet()) {
                PDField field = acroForm.getField(entry.getKey());
                if (field != null) {
                    try {
                        if (field instanceof PDTextField) {
                            // 普通文本字段
                            PDTextField textField = (PDTextField) field;
                            // TODO /Helv 9 其中9代表字体的大小
                            textField.setDefaultAppearance("/Helv 9 Tf 0 g"); // 使用Helvetica字体
                            textField.setValue(entry.getValue());
                            textField.setReadOnly(true);
                        } else if (field instanceof PDCheckBox) {
                            // 多选框
                            PDCheckBox checkBox = (PDCheckBox) field;
                            checkBox.check(); // 假设值为 "Yes" 时选中
                            field.setReadOnly(true); // 设置字段为只读，生成不可编辑的PDF
                        }
                    } catch (Exception e) {
                        System.err.println("设置字段值时发生错误: " + entry.getKey());
                        e.printStackTrace();
                    }
                }
            }


            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                contentStream.drawImage(pdImage, x, y, width, height); // 在坐标(100, 100)处插入图片，宽度100，高度50
            }
            document.save(outputStream);


            try (PDDocument newDocument = PDDocument.load(new ByteArrayInputStream(outputStream.toByteArray()))) {
                PDFRenderer pdfRenderer = new PDFRenderer(newDocument);
                int pageCount = newDocument.getNumberOfPages();

                // 缓存每页的 BufferedImage
                BufferedImage[] pageImages = new BufferedImage[pageCount];
                int newwidth = 0;
                int newheight = 0;

                // 计算合并后的图片尺寸并缓存每页的图像
                for (int pageSize = 0; pageSize < pageCount; ++pageSize) {
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(pageSize, DPI);
                    pageImages[pageSize] = bim;
                    newwidth = Math.max(newwidth, bim.getWidth());
                    newheight += bim.getHeight();
                }

                // 创建合并后的图片
                BufferedImage combinedImage = new BufferedImage(newwidth, newheight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = combinedImage.createGraphics();

                int currentHeight = 0;
                for (BufferedImage bim : pageImages) {
                    g2d.drawImage(bim, 0, currentHeight, null);
                    currentHeight += bim.getHeight();
                }

                g2d.dispose();

                Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
                if (!writers.hasNext()) {
                    throw new IllegalStateException("No JPEG Image Writers available");
                }
                ImageWriter writer = writers.next();
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
                    writer.setOutput(ios);
                    ImageWriteParam param = writer.getDefaultWriteParam();
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(COMPRESS_QUALITY); // 使用传入的压缩质量

                    writer.write(null, new IIOImage(combinedImage, null, null), param);

                    return baos.toByteArray();
                } finally {
                    writer.dispose(); // 确保释放资源
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("PDF表单填充信息失败!");
        }
    }


    public static void main(String[] args) throws IOException {
        Map<String, String> formData = new HashMap<>();
        formData.put("year", "2025");
        formData.put("month", "03");
        formData.put("day", "14");

        String pdfFilePath = "D:\\workspace\\xbqx-workspace\\pdfdemo\\SD_REG_S.pdf";
        //String signImageFilePath = "D:\\workspace\\xbqx-workspace\\pdfdemo\\signature.png";
        //String signImageFilePath = "D:\\workspace\\xbqx-workspace\\pdfdemo\\test.jpg";
        String signImageFilePath = "D:\\workspace\\xbqx-workspace\\pdfdemo\\test3.png";
        //String savePdfFilePath = "D:\\workspace\\xbqx-workspace\\pdfdemo\\fil100.pdf";
        String savePdfImageFilePath = "D:\\workspace\\xbqx-workspace\\pdfdemo\\fil115.jpg";

        Path path = Paths.get(signImageFilePath);
        byte[] bytes = Files.readAllBytes(path);

        // 保存的是带签名的PDF
        //byte[] resultBytes = fillFormV1(pdfFilePath, bytes, formData, 125, 125, 115, 50);
        //Path savePath = Paths.get(savePdfFilePath).normalize();
        // 保存的是带签名的PDF的图片
        byte[] resultBytes = fillForm(pdfFilePath, bytes, formData, 125, 125, 115, 50);
        Path savePath = Paths.get(savePdfImageFilePath).normalize();
        Files.write(savePath, resultBytes);


    }
}
