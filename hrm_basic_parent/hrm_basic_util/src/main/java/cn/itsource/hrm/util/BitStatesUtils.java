package cn.itsource.hrm.util;

/**
 * 用户状态类，记录用户在平台使用系统中所有的状态。
 * @author nixianhua
 */
public class BitStatesUtils {
	
	/**
	 * 用户注册成功的标示,及为默认初始状态
	 */
	public final static long OP_REGISTED = 1L << 0;
	/**
	 * 是否已激活（认证手机或认证邮箱）。
	 */
	public final static long OP_ACTIVED = 1L << 1;
	/**
	 * 是否锁定（未锁定则没有该状态），安全监测程序/后台对用户的锁定操作。
	 */
	public final static long OP_LOCKED = 1L << 2;
	/**
	 * 是否手机认证
	 */
	public final static long OP_AUTHED_PHONE = 1L << 3;
	/**
	 * 是否邮箱认证
	 */
	public final static long OP_AUTHED_EMAIL = 1L << 4;
	
	/**
	 * 是否完善个人信息
	 */
	public final static long OP_SAVE_BASIC_INFO = 1L << 5;
	
	/**
	 * 是否正在进行实名认证
	 */
	public final static long OP_IDENTITY_AUTHING = 1L << 6;
	
	/**
	 * 是否完成实名认证
	 */
	public final static long OP_IDENTITY_AUTHED = 1L << 7;
	/**
	 * 是否初始化支付密码
	 */
	public final static long OP_INIT_PAY_PASSWORD = 1L << 8;
	
	/**
	 * @param states 用户当前状态值
	 * @param value  需要判断状态值
	 * @return 是否存在
	 */
	public static boolean hasState(long states,long value){
		return (states & value) == value;
	}
	
	/**
	 * @param states 已有状态值
	 * @param value  需要添加状态值
	 * @return 新的状态值
	 */
	public static long addState(long states,long value){
		if(hasState(states, value)){
			return states;
		}
		return (states | value);
	}
	
	/**
	 * @param states 已有状态值
	 * @param value  需要删除状态值
	 * @return 新的状态值
	 */
	public static long removeState(long states,long value){
		if(!hasState(states, value)){
			return states;
		}
		return states ^ value;
	}
	
}
