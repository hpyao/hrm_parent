package cn.itsource.hrm.util;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 取得给定汉字串的首字母串,即声母串 Title: ChineseCharToEn(含常用汉字，不常见汉字及多音字)
 * @date: 2014-1-15 注：只支持GB2312字符集中的汉字
 * 
 */
public class LetterUtil {

	private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086,
			4390, 4558, 4684, 4925, 5249, 5590 };

	private final static String[] lc_FirstLetter = { "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "w", "x",
			"y", "z" };

	private static Map<String, String> exceptWords = new HashMap<String, String>();
	static {
		exceptWords.put("a", "庵鳌");
		exceptWords.put("b", "璧亳並侼別匂");
		exceptWords.put("c", "茌丞丒丳刅");
		exceptWords.put("d", "渎砀棣儋丟");
		exceptWords.put("e", "");
		exceptWords.put("f", "邡冹兝");
		exceptWords.put("g", "崮藁莞丐丱乢亁仠冮匃匄");
		exceptWords.put("h", "骅珲潢湟丆冴匢");
		exceptWords.put("j", "泾蛟暨缙旌莒鄄丌丩丮丯丼亅伋冏匊匛匞");
		exceptWords.put("k", "丂匟");
		exceptWords.put("l", "崂涞栾溧漯浏耒醴泸阆崃両刢劽啰");
		exceptWords.put("m", "渑汨丏冐冺兞冇");
		exceptWords.put("n", "");
		exceptWords.put("o", "瓯");
		exceptWords.put("p", "邳濮郫丕伂冸");
		exceptWords.put("q", "喬綦衢岐朐邛丠丬亝冾兛匤");
		exceptWords.put("r", "榕刄");
		exceptWords.put("s", "泗睢沭嵊歙莘嵩鄯丄丗侺兙");
		exceptWords.put("t", "潼滕郯亣侹侻");
		exceptWords.put("w", "婺涠汶亾仼卍卐");
		exceptWords.put("x", "鑫盱浔荥淅浠亵丅伈兇");
		exceptWords.put("y", "懿眙黟颍兖郓偃鄢晏丣亜伇偐円匜");
		exceptWords.put("z", "梓涿诏柘秭圳伀冑刣");
	}

	private final static String polyphoneTxt = "重庆|cq,音乐|yy";

	/**
	 * 取得给定汉字串的首字母串,即声母串
	 * 
	 * @param str 给定汉字串
	 * @return 声母串
	 */
	public static String getAllFirstLetter(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		}

		// 多音字判定
		for (String polyphone : polyphoneTxt.split(",")) {
			String[] chinese = polyphone.split("[|]");
			if (str.indexOf(chinese[0]) != -1) {
				str = str.replace(chinese[0], chinese[1]);
			}
		}

		String _str = "";
		for (int i = 0; i < str.length(); i++) {
			_str = _str + getFirstLetter(str.substring(i, i + 1));
		}

		return _str;
	}

	/**
	 * 取得给定汉字的首字母,即声母
	 * 
	 * @param chinese 给定的汉字
	 * @return 给定汉字的声母
	 */
	public static String getFirstLetter(String chinese) {
		if (chinese == null || chinese.trim().length() == 0) {
			return "";
		}
		String chineseTemp = chinese;
		chinese = conversionStr(chinese, "GB2312", "ISO8859-1");

		if (chinese.length() > 1) {
			// 判断是不是汉字
			int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
			int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
			li_SectorCode = li_SectorCode - 160;
			li_PositionCode = li_PositionCode - 160;
			int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
			if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
				for (int i = 0; i < 23; i++) {
					if (li_SecPosCode >= li_SecPosValue[i] && li_SecPosCode < li_SecPosValue[i + 1]) {
						chinese = lc_FirstLetter[i];
						break;
					}
				}
			} else {
				// 非汉字字符,如图形符号或ASCII码
				chinese = matchPinYin(chinese);
			}
		}

		// 如还是无法匹配，再次进行拼音匹配
		if (chinese.equals("?")) {
			chinese = matchPinYin(chineseTemp, false);
		}

		return chinese;
	}

	/**
	 * 汉字匹配拼音对照
	 * 
	 * @param chinese
	 * @return
	 */
	private static String matchPinYin(String chinese, boolean needConvert) {
		String chineseTemp = chinese;
		if (needConvert) {
			chinese = conversionStr(chinese, "ISO8859-1", "GB2312");
		}
		chinese = chinese.substring(0, 1);

		// findRepeatWord(exceptWords);

		for (Entry<String, String> letterSet : exceptWords.entrySet()) {
			if (letterSet.getValue().indexOf(chinese) != -1) {
				chinese = letterSet.getKey();
				break;
			}
		}
		chinese = chineseTemp.equals(chinese) ? "?" : chinese;
		return chinese;
	}

	private static String matchPinYin(String chinese) {
		return matchPinYin(chinese, true);
	}

	/**
	 * 字符串编码转换
	 * 
	 * @param str 要转换编码的字符串
	 * @param charsetName 原来的编码
	 * @param toCharsetName 转换后的编码
	 * @return 经过编码转换后的字符串
	 */
	private static String conversionStr(String str, String charsetName, String toCharsetName) {
		try {
			str = new String(str.getBytes(charsetName), toCharsetName);
		} catch (UnsupportedEncodingException ex) {
			System.out.println("字符串编码转换异常：" + ex.getMessage());
		}
		return str;
	}


	@SuppressWarnings("unused")
	private static void findRepeatWord(Map<String, String> wordsMap) {
		String words = wordsMap.values().toString().replaceAll("[, ]", "");
		words = words.substring(1, words.length() - 1);
		for (char word : words.toCharArray()) {
			int count = findStr2(words, String.valueOf(word));
			if (count > 1) {
				System.out.println("汉字：【" + word + "】出现了" + count + "次！");
			}
		}
	}

	private static int findStr2(String srcText, String keyword) {
		int count = 0;
		Pattern p = Pattern.compile(keyword);
		Matcher m = p.matcher(srcText);
		while (m.find()) {
			count++;
		}
		return count;
	}
	
	public static void main(String[] args) {

		System.out.println(LetterUtil.getFirstLetter("源码时代"));
		String address = "(金浜小区)栖山路1689弄";
		 System.out.println("获取拼音首字母：" + LetterUtil.getAllFirstLetter(address));
	}
}
