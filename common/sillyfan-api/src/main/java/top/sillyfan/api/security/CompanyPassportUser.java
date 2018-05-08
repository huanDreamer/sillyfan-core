package top.sillyfan.api.security;

/**
 * 媒体端Passport User 信息
 * 
 * @author huanxing
 * @date 8/7/17
 */
public class CompanyPassportUser extends LoginUser {

	/** 邮箱 */
	private String email;

	/** 公司ID */
	private Long companyId;

	/** 开发者类型 1公司 2个人 */
	private Integer companyType;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}
}
