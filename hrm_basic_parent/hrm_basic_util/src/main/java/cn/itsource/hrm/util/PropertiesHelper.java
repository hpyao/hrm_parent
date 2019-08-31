package cn.itsource.hrm.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesHelper {
	public static Properties load(String fileName) {
		try {
			InputStream in = PropertiesHelper.class.getResourceAsStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			Properties p = new Properties();
			p.load(br);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Properties load(InputStream is) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			Properties p = new Properties();
			p.load(br);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
