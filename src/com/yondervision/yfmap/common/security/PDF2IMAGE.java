package com.yondervision.yfmap.common.security;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import com.lowagie.text.pdf.PdfReader;
 
public class PDF2IMAGE {
 
	/***
	 * PDF文件转PNG/JPG图片，全部页数
	 * @param PdfFilePath pdf完整路径
	 * @param imgFilePath 图片存放的文件夹
	 * @param dpi dpi越大转换后越清晰，相对转换速度越慢
	 * @return
	 */
	public static int pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
		File file = new File(PdfFilePath);
		PDDocument pdDocument;
		int pages = 0;
		try {
			String imgPDFPath = file.getParent();
			int dot = file.getName().lastIndexOf('.');
			String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
			String imgFolderPath = null;
			if (dstImgFolder.equals("")) {
				imgFolderPath = imgPDFPath + File.separator + imagePDFName;// 获取图片存放的文件夹路径
			} else {
				imgFolderPath = dstImgFolder + File.separator + imagePDFName;
			}
 
			if (createDirectory(imgFolderPath)) {
 
				pdDocument = PDDocument.load(file);
				PDFRenderer renderer = new PDFRenderer(pdDocument);
				/* dpi越大转换后越清晰，相对转换速度越慢 */
				PdfReader reader = new PdfReader(PdfFilePath);
				pages = reader.getNumberOfPages();
				StringBuffer imgFilePath = null;
				for (int i = 0; i < pages; i++) {
					String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
					imgFilePath = new StringBuffer();
					imgFilePath.append(imgFilePathPrefix);
					imgFilePath.append("_");
					imgFilePath.append(String.valueOf(i + 1));
					imgFilePath.append(".png");
					File dstFile = new File(imgFilePath.toString());
					BufferedImage image = renderer.renderImageWithDPI(i, dpi);
					ImageIO.write(image, "png", dstFile);
				}
				System.out.println("PDF文档转PNG图片成功！");
 
			} else {
				System.out.println("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pages;
	}
 
	private static boolean createDirectory(String folder) {
		File dir = new File(folder);
		if (dir.exists()) {
			return true;
		} else {
			return dir.mkdirs();
		}
	}
	
	
	public static void main(String[] args) {
		pdf2Image("D:/2018330212MM0061101A.pdf", "D:/pdf", 100);
	}
}
