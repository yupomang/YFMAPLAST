package com.yondervision.yfmap.common.security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	// 不能在同一个路径，否则会死循环 add by 徐志文
	public static void dozip(String srcfile, String zipfile) throws IOException {
		String temp = "";
		System.out.println("srcfile=====" + srcfile);
		File src = new File(srcfile);
		File zipFile = new File(zipfile);
		// 判断要压缩的文件存不存在
		if (!src.exists()) {
			System.out.println("要压缩的文件不存在！");
			System.exit(1);
		}
		// 如果说压缩路径不存在，则创建
		if (!zipFile.getParentFile().exists()) {
			zipFile.getParentFile().mkdirs();
			// System.out.println("创建ok");
		}
		// 封装压缩的路径
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(zipfile));
		// 这里可以加入校验
		// CheckedOutputStream cos = new CheckedOutputStream(bos,new CRC32());
		ZipOutputStream zos = new ZipOutputStream(bos);
		zip(src, zos, temp);
		// 关闭流
		zos.flush();
		zos.close();
		System.out.println("压缩完成！");
		System.out.println("压缩文件的位置是：" + zipfile);
		// System.out.println("检验和："+cos.getChecksum().getValue());
	}

	private static void zip(File file, ZipOutputStream zos, String temp)
			throws IOException {
		// 如果不加"/"将会作为文件处理，空文件夹不需要读写操作
		if (file.isDirectory()) {
			System.out.println("是目录");
			String str = temp + file.getName() + "/";
			System.out.println("str========" + str);
			zos.putNextEntry(new ZipEntry(str));
			File[] files = file.listFiles();
			System.out.println("files========" + files.length);
			for (File file2 : files) {
				System.out.println("这个执行了一遍");
				zip(file2, zos, str);
			}
		} else {
			System.out.println("压缩执行了一遍");
			System.out.println("当前文件的父路径：" + temp);
			ZipFile(file, zos, temp);
		}
	}

	private static void ZipFile(File srcfile, ZipOutputStream zos, String temp)
			throws IOException {
		// 默认的等级压缩-1
		// zos.setLevel(xxx);
		// 封装待压缩文件
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				srcfile));
		zos.putNextEntry(new ZipEntry(temp + srcfile.getName()));

		byte buf[] = new byte[1024];
		int len;
		while ((len = bis.read(buf)) != -1) {
			zos.write(buf, 0, len);
		}
		// 按标准需要关闭当前条目，不写也行
		zos.closeEntry();
		bis.close();
	}

	/*
	 * 好压的解压规则：
	 * 1.如果解压到与压缩文件同名的文件夹，则直接解压 如果自定义了其他文件夹xxx，则先创建xxx，再放入解压后的文件夹
	 * 2.好压压缩的时候，是采用GBK格式的,所以在解压的时候，为了统一，采用GBK解压另外再说一下WINRAR，因为RAR压缩是申请了专利（商业软件），
	 * 所以RAR压缩算法是不公开的，但是解压算法是有的，其压缩默认也是GBK格式的；
	 * 经过测试，发现，不管压缩的时候采用UTF-8还是GBK，解压的时候用GBK都可以正确解压！（具体原因还不清楚）
	 * 本java程序是直接解压到文件夹的，默认解压到与压缩文件同路径
	 */

	public static void unZip(String zipfile) throws IOException {
		// 检查是否是zip文件,并判断文件是否存在
		checkFileName(zipfile);
		long startTime = System.currentTimeMillis();
		File zfile = new File(zipfile);
		// 获取待解压文件的父路径
		String Parent = zfile.getParent() + "/";
		FileInputStream fis = new FileInputStream(zfile);
		Charset charset = Charset.forName("GBK");// 默认UTF-8
		// CheckedInputStream cis = new CheckedInputStream(fis,new CRC32());
		ZipInputStream zis = new ZipInputStream(fis);// 输入源zip路径
		ZipEntry entry = null;
		BufferedOutputStream bos = null;
		while ((entry = zis.getNextEntry()) != null) {
			if (entry.isDirectory()) {
				File filePath = new File(Parent + entry.getName());
				// 如果目录不存在，则创建
				if (!filePath.exists()) {
					filePath.mkdirs();
				}
			} else {
				FileOutputStream fos = new FileOutputStream(Parent
						+ entry.getName());
				bos = new BufferedOutputStream(fos);
				byte buf[] = new byte[1024];
				int len;
				while ((len = zis.read(buf)) != -1) {
					bos.write(buf, 0, len);
				}
				zis.closeEntry();
				// 关闭的时候会刷新
				bos.close();
			}
		}
		zis.close();
		long endTime = System.currentTimeMillis();
		System.out.println("解压完成！所需时间为：" + (endTime - startTime) + "ms");
		// System.out.println("校验和："+cis.getChecksum().getValue());
	}

	private static void checkFileName(String name) {
		// 文件是否存在
		if (!new File(name).exists()) {
			System.err.println("要解压的文件不存在！");
			System.exit(1);
		}
		// 判断是否是zip文件
		int index = name.lastIndexOf(".");
		String str = name.substring(index + 1);
		if (!"zip".equalsIgnoreCase(str)) {
			System.err.println("不是zip文件,无法解压！");
			System.exit(1);
		}
	}

/*	利用zipFile解压,方法跟ZipInputStream类似，都是连续取到Entry，
	然后再用Entry判断，听说zipFile内建了缓冲流，所以对于同一个文件解压多次效率比ZipInputStream高些*/
	public static void dozip(String zipfile) throws IOException {
		checkFileName(zipfile);
		long startTime = System.currentTimeMillis();
		// 获取待解压文件的父路径
		File zfile = new File(zipfile);
		String Parent = zfile.getParent() + "/";
		// 设置,默认是UTF-8
		Charset charset = Charset.forName("GBK");
		java.util.zip.ZipFile zip = new java.util.zip.ZipFile(zfile, 10);
		ZipEntry entry = null;
		// 封装解压后的路径
		BufferedOutputStream bos = null;
		// 封装待解压文件路径
		BufferedInputStream bis = null;
		Enumeration<ZipEntry> enums = (Enumeration<ZipEntry>) zip.entries();
		while (enums.hasMoreElements()) {
			entry = enums.nextElement();
			if (entry.isDirectory()) {
				File filePath = new File(Parent + entry.getName());
				// 如果目录不存在，则创建
				if (!filePath.exists()) {
					filePath.mkdirs();
				}
			} else {
				bos = new BufferedOutputStream(new FileOutputStream(Parent
						+ entry.getName()));
				// 获取条目流
				bis = new BufferedInputStream(zip.getInputStream(entry));
				byte buf[] = new byte[1024];
				int len;
				while ((len = bis.read(buf)) != -1) {
					bos.write(buf, 0, len);
				}

				bos.close();
			}
		}
		bis.close();
		zip.close();
		System.out.println("解压后的路径是：" + Parent);
		long endTime = System.currentTimeMillis();
		System.out.println("解压成功,所需时间为:" + (endTime - startTime) + "ms");
	}

}
