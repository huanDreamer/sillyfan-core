package top.sillyfan.api.response;

public class ApiMessage {

	public interface API {
		String ERROR = "api.message.common.error";
	}

	/**
	 * 从Error对象获取国际化资源定义key
	 * 
	 * @param status
	 * 
	 * @return String
	 */
	public static String getI1n8nKey(ApiResponseCode status) {
		return String.format("%s.%s", API.ERROR, status.getCode());
	}

}
