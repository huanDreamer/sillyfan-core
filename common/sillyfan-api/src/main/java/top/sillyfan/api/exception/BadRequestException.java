package top.sillyfan.api.exception;

/**
 * 数据逻辑验证不通过时，抛出此异常。返回的httpStatus为400，通常为非表单异常。
 *
 * 由{@GlobalExceptionHandler}做统一返回结果处理。
 *
 */
public class BadRequestException extends RuntimeException {

	public BadRequestException() {
		super();
	}

	public BadRequestException(String code) {
		super(code);
	}

	public BadRequestException(String code, Throwable cause) {
		super(code, cause);
	}

	public BadRequestException(Throwable cause) {
		super(cause);
	}

}
