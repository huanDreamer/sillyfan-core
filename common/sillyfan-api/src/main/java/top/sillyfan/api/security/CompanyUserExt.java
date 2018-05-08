package top.sillyfan.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

/**
 * 扩展@User，添加附加信息，比如用户所在公司，可访问的广告主，可访问的资源等等？
 * 
 * @author guxiangguo
 *
 */
public class CompanyUserExt extends User {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;;

	private CompanyPassportUser companyUser;

	public CompanyUserExt(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,
			CompanyPassportUser info) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		this.companyUser = info;
	}

	public CompanyUserExt(String username, String password,
			Collection<? extends GrantedAuthority> authorities,
			CompanyPassportUser info) {
		this(username, password, true, true, true, true, authorities, info);
	}

	public CompanyPassportUser getCompanyUser() {
		return companyUser;
	}

	public void setCompanyUser(CompanyPassportUser companyUser) {
		this.companyUser = companyUser;
	}
}
