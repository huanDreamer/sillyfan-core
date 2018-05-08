package top.sillyfan.api.security;

import java.io.Serializable;

/**
 * @author huanxing
 * @date 8/4/17
 */
public class LoginUser implements Serializable {

	private Long userId;

	private String username;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
