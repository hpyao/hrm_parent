package cn.itsource.hrm.util;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CodeGenerateUtils {
	
	/**
	 * 获取商品编码
	 * 商品编码规则：nanoTime(后5位)*5位随机数(10000~99999)
	 * @return
	 */
	public static String generateProductCode(){
		long nanoPart = System.nanoTime() % 100000L;
		if(nanoPart<10000L){
			nanoPart+=10000L;
		}
		long randomPart = (long)(Math.random()*(90000)+10000);
		String code = "0"+String.valueOf((new BigDecimal(nanoPart).multiply(new BigDecimal(randomPart))));
		return code.substring(code.length()-10);
	}
	
	/**
	 * @param id: 用户id
	 * 生成订单编号
	 * 订单编号规则：(10位)：(年末尾*月，取后2位)+（用户ID%3.33*日取整后2位）+(timestamp*10000以内随机数，取后6位)
	 * @return
	 */
	public static String generateOrderSn(long id){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		year = year % 10;
		if(year == 0) year = 10;
		int month = calendar.get(Calendar.MONTH)+1;
		int yearMonth  =  year * month;
		String yearMonthPart = "0"+yearMonth;
		yearMonthPart = yearMonthPart.substring(yearMonthPart.length() - 2 );
		
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int dayNum = (int)((id % 3.33) * day);
		String dayPart = "0"+dayNum;
		dayPart = dayPart.substring(dayPart.length() - 2);
		
		String timestampPart = ""+(Math.random() * 10000) * (System.currentTimeMillis()/10000);
		timestampPart = timestampPart.replace(".", "").replace("E", "");
		timestampPart = timestampPart.substring(0,6);
		return yearMonthPart+dayPart+timestampPart;
	}
	
	/**
	 * 生成统一支付单号
	 * 规则：年(2)月(2)日(2)时(2)分(2)+timestamp*5位随机整数取后5位
	 * @return
	 */
	public static String generateUnionPaySn(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm");
		String dateTime = dateFormat.format(calendar.getTime());
		dateTime = dateTime.substring(2);
		String timestampPart = ""+(Math.random() * 10000) * (System.currentTimeMillis()/10000);
		timestampPart = timestampPart.replace(".", "").replace("E", "");
		timestampPart = timestampPart.substring(0,5);
		return dateTime+timestampPart;
	}
	
	public static void main(String[] args) {
		for(long i=0;i<100;i++)
		{
			//String timestampPart = ""+(Math.random() * 10000) * (System.currentTimeMillis()/10000);
			//System.out.println(timestampPart);
			//System.out.println(generateOrderSn(i));
			System.out.println(generateUnionPaySn());
		}
	}
	
}
