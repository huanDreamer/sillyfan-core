package top.sillyfan.api.response;

/**
 * ApiResponseCode定义
 * 
 * @author donghk
 *
 */
public enum ApiResponseCode {

	// @formatter:off
	CODE_0(0, "成功"),
	CODE_112(112, "参数错误"),
	CODE_403(403, "权限错误"),
	CODE_404(404, "没有资源"),
	CODE_997(997, "SDK异常"),
	CODE_998(998, "SDK下发网络超时"),
	CODE_999(999, "未知异常");
	// @formatter:on

	/** 编号 */
	private Integer code;

	/** 编号信息 */
	private String message;

	/**
	 * 构造器
	 * 
	 * @param code
	 * @param message
	 */
	private ApiResponseCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 通过编号获取枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	public static ApiResponseCode getByMessage(String message) {
		for (ApiResponseCode item : ApiResponseCode.values()) {
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
	public static ApiResponseCode getByCode(String code) {
		for (ApiResponseCode item : ApiResponseCode.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
