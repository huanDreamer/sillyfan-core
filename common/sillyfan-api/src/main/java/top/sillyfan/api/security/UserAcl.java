package top.sillyfan.api.security;

import java.util.List;

/**
 * 用户访问控制的授权信息
 * 
 * @author Admin
 *
 */
public interface UserAcl {

	Long getCompanyId();

	Long getUserId();

	List<Long> getAdvertiserIds();

	List<Long> getBrandIds();

}
