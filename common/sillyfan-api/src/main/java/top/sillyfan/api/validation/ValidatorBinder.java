package top.sillyfan.api.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import top.sillyfan.api.exception.RequestParamNotValidException;

/**
 * validator分步构建工具
 * 
 * @author twicrow
 *
 */
public class ValidatorBinder extends DataBinder {


	static Logger logger = LoggerFactory.getLogger(ValidatorBinder.class);

	/**
	 * 创建构建工具 需要指定验证对象
	 * 
	 * @param target
	 */
	public ValidatorBinder(Object target) {
		super(target);
	}

	/**
	 * 验证 获取验证结果
	 * 
	 * @return
	 */
	public void validate() {

		for (Validator validator : super.getValidators()) {
			validator.validate(getTarget(), getBindingResult());
		}

		// 判断api请求字段业务验证，有错误抛出
		if (super.getBindingResult() != null
				&& super.getBindingResult().hasErrors()) {

			logger.warn("Validate error:{} ",super.getBindingResult().getAllErrors());

			throw new RequestParamNotValidException(super.getBindingResult());
		}
	}

	/**
	 * 添加验证器
	 * 
	 * @param validators
	 * @return
	 */
	public DataBinder addMoreValidators(Validator... validators) {
		super.addValidators(validators);
		return this;
	}

}
