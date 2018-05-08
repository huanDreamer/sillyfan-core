package top.sillyfan.api.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;

/**
 * 使用ValidatorBinder进行参数业务验证时，抛出此异常。
 * 
 * 由{@GlobalExceptionHandler}做统一返回结果处理。
 * 
 * @author donghk
 *
 */
public class RequestParamNotValidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final BindingResult bindingResult;

	private String message;

	public RequestParamNotValidException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

	public BindingResult getBindingResult() {
		return this.bindingResult;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {

		if (StringUtils.isEmpty(message)) {
			return super.getMessage();
		}

		return message;
	}
}
