package cn.itsource.hrm.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 用于获取指定包名下的所有类名（包含jar包）.<br/>
 * 并可设置是否遍历该包名下的子包的类名.<br/>
 */
public class ClassUtils {
	public static List<Class<?>> getClassList(String pkgName , boolean isRecursive) {
		List<Class<?>> classList = new ArrayList<Class<?>>();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			String strFile = pkgName.replaceAll("\\.", "/");
			Enumeration<URL> urls = loader.getResources(strFile);
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
                if (url != null) {
                	String protocol = url.getProtocol();
                	String pkgPath = url.getPath();
                    if ("file".equals(protocol)) {
						findClassName(classList, pkgName, pkgPath, isRecursive);
					} else if ("jar".equals(protocol)) {
						findClassName(classList, pkgName, url, isRecursive);
                    }
                }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classList;
	}
	
	public static void findClassName(List<Class<?>> clazzList, String pkgName, String pkgPath, boolean isRecursive) {
		if(clazzList == null){
			return;
		}
		File[] files = filterClassFiles(pkgPath);
		if(files != null){
			for (File f : files) {
				String fileName = f.getName();
				if (f.isFile()) {
					String clazzName = getClassName(pkgName, fileName);
					addClassName(clazzList, clazzName);
				} else {
					if(isRecursive){
						String subPkgName = pkgName +"."+ fileName;
						String subPkgPath = pkgPath +"/"+ fileName;
						findClassName(clazzList, subPkgName, subPkgPath, true);
					}
				}
			}
		}
	}
	
	public static void findClassName(List<Class<?>> clazzList, String pkgName, URL url, boolean isRecursive) throws IOException {
		JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		JarFile jarFile = jarURLConnection.getJarFile();
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		while (jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			String jarEntryName = jarEntry.getName(); 
			String clazzName = jarEntryName.replace("/", ".");
			int endIndex = clazzName.lastIndexOf(".");
			String prefix = null;
			if (endIndex > 0) {
				clazzName = clazzName.substring(0, endIndex);
				endIndex = clazzName.lastIndexOf(".");
				if(endIndex > 0){
					prefix = clazzName.substring(0, endIndex);
				}
			}
			if (prefix != null && jarEntryName.endsWith(".class")) {
				if(prefix.equals(pkgName)){
					addClassName(clazzList, clazzName);
				} else if(isRecursive && prefix.startsWith(pkgName)){
					addClassName(clazzList, clazzName);
				}
			}
		}
	}
	
	private static File[] filterClassFiles(String pkgPath) {
		if(pkgPath == null){
			return null;
		}
		return new File(pkgPath).listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
			}
		});
    }
	
	private static String getClassName(String pkgName, String fileName) {
		int endIndex = fileName.lastIndexOf(".");
		String clazz = null;
		if (endIndex >= 0) {
			clazz = fileName.substring(0, endIndex);
		}
		String clazzName = null;
		if (clazz != null) {
			clazzName = pkgName + "." + clazz;
		}
		return clazzName;
	}
	
	private static void addClassName(List<Class<?>> clazzList, String clazzName) {
		if (clazzList != null && clazzName != null) {
			Class<?> clazz = null;
			try {
				//Thread.currentThread().getContextClassLoader()
				ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
				clazz = Class.forName(clazzName,false,contextClassLoader);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (clazz != null) {
					clazzList.add(clazz);
			}
		}
	}
}