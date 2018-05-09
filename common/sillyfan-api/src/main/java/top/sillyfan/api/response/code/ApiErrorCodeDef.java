package top.sillyfan.api.response.code;

public enum ApiErrorCodeDef {

    SUCCESS(ApiErrorCode.SUCCESS, "成功"),
    CODE_1000(ApiErrorCode.CODE_1000, "帐号或密码错误"),
	CODE_1001(ApiErrorCode.CODE_1001, "请登录"),

	;


	/** 编号 */
	private String code;

	/** 编号信息 */
	private String message;

	/**
	 * 构造器
	 * 
	 * @param code
	 * @param message
	 */
	private ApiErrorCodeDef(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 通过编号获取枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	public static ApiErrorCodeDef getByMessage(String message) {
		for (ApiErrorCodeDef item : ApiErrorCodeDef.values()) {
			if (item.getMessage().equals(message)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 通过编号信息获取枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	public static ApiErrorCodeDef getByCode(String code) {
		for (ApiErrorCodeDef item : ApiErrorCodeDef.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}