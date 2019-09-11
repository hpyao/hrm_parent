package cn.itsource.hrm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class VelocityUtils {
	private static Properties p = new Properties();
	static {
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
		p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
		p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
	}
	
	/**
	 * 返回通过模板，将model中的数据替换后的内容
	 * @param model
	 * @param templateFilePathAndName
	 * @return
	 */
	public static String getContentByTemplate(Object model, String templateFilePathAndName){
		try {
			Velocity.init(p);
			Template template = Velocity.getTemplate(templateFilePathAndName);
			VelocityContext context = new VelocityContext();
			context.put("model", model);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			String retContent = writer.toString();
			writer.close();
			return retContent;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据模板，静态化model到指定的文件 模板文件中通过访问model来访问设置的内容
	 * 
	 * @param model
	 *            数据对象
	 * @param templateFilePathAndName
	 *            模板文件的物理路径
	 * @param targetFilePathAndName
	 *            目标输出文件的物理路径
	 */
	public static void staticByTemplate(Object model, String templateFilePathAndName, String targetFilePathAndName) {
		try {
			Velocity.init(p);
			Template template = Velocity.getTemplate(templateFilePathAndName);
			
			VelocityContext context = new VelocityContext();
			context.put("model", model);
			FileOutputStream fos = new FileOutputStream(targetFilePathAndName);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));// 设置写入的文件编码,解决中文问题
			template.merge(context, writer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 静态化内容content到指定的文件
	 * 
	 * @param content
	 * @param targetFilePathAndName
	 */
	public static void staticBySimple(Object content, String targetFilePathAndName) {
		VelocityEngine ve = new VelocityEngine();
		ve.init(p);
		String template = "${content}";
		VelocityContext context = new VelocityContext();
		context.put("content", content);
		StringWriter writer = new StringWriter();
		ve.evaluate(context, writer, "", template);
		try {
			FileWriter fileWriter = new FileWriter(new File(targetFilePathAndName));
			fileWriter.write(writer.toString());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
