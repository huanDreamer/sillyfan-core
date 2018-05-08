package top.sillyfan.api.validation;

import org.springframework.stereotype.Component;

/**
 * 逻辑验证工具构建
 * 
 * @author twicrow
 *
 */
@Component
public class ValidatorBuilderFactory {

	public ValidatorBinder create(Object target) {
		return new ValidatorBinder(target);
	}

}
