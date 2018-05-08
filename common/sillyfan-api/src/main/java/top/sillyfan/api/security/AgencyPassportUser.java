package top.sillyfan.api.security;

/**
 * 代理端Passport User 信息
 * 
 * @author huanxing
 * @date 8/7/17
 */
public class AgencyPassportUser extends LoginUser {

	private String email;

	private Long agencyId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
}
