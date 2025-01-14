package com.mrgao.pdf.utils.v1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/1/14 10:49
 */
public class PdfSignatureInserter {

    public static void main(String[] args) {
        Map<String, String> formData = new HashMap<>();
        formData.put("year", "2024");
        formData.put("month", "12");
        formData.put("day", "27");

        String pdfFilePath = "D:\\workspace\\xbqx-workspace\\pdfdemo\\SD_REG_S.pdf";
        String signImageFilePath = "D:\\workspace\\xbqx-workspace\\pdfdemo\\signature.png";
        String savePdfFilePath = "D:\\workspace\\xbqx-workspace\\pdfdemo\\fil66.pdf";

        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            //PDPage page = document.getPage(0); // 获取第一页
            PDPage page = document.getPage(document.getNumberOfPages() - 1); // 获取最后一页

            File signImageFile = new File(signImageFilePath);
            System.out.println("加载图片文件" + (signImageFile.exists() ? "成功" : "失败"));
            PDImageXObject pdImage = PDImageXObject.createFromFileByContent(signImageFile, document);

            // 获取PDF表单域
            PDDocumentCatalog docCatalog = document.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();

            for (Map.Entry<String, String> entry : formData.entrySet()) {
                PDTextField field = (PDTextField) acroForm.getField(entry.getKey());
                if (field != null) {
                    try {
                        field.setDefaultAppearance("/Helv 0 Tf 0 g"); // 使用Helvetica字体
                        field.setValue(entry.getValue());
                        field.setReadOnly(true); // 设置字段为只读，生成不可编辑的PDF
                    } catch (IOException e) {
                        System.err.println("设置字段值时发生错误: " + entry.getKey());
                        e.printStackTrace();
                    }
                }
            }

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                contentStream.drawImage(pdImage, 125, 125, 110, 40); // 在坐标(100, 100)处插入图片，宽度100，高度50
            } catch (Exception e) {
                // 可以选择重新抛出异常或进行其他处理
                throw new RuntimeException("Failed to draw image on page", e);
            }

            document.save(savePdfFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
