package top.sillyfan.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 请求资源未找到/无该资源的访问权限 抛出此异常 <br/>
 *
 * 由{@GlobalExceptionHandler}做统一返回结果处理。
 */
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private final Object resource;

	public ResourceNotFoundException(Object resource) {
		super("resource not found: " + resource);
		this.resource = resource;
	}

	public Object getResource() {
		return resource;
	}
}
