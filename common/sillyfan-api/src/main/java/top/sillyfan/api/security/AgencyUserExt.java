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
public class AgencyUserExt extends User {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;;

	private AgencyPassportUser agencyUser;

	public AgencyUserExt(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,
			AgencyPassportUser info) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		this.agencyUser = info;
	}

	public AgencyUserExt(String username, String password,
			Collection<? extends GrantedAuthority> authorities,
			AgencyPassportUser info) {
		this(username, password, true, true, true, true, authorities, info);
	}

	public AgencyPassportUser getAgencyUser() {
		return agencyUser;
	}

	public void setAgencyUser(AgencyPassportUser agencyUser) {
		this.agencyUser = agencyUser;
	}
}
