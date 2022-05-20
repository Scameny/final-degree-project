package es.udc.tfg.backend.rest.controllers.common;

public class JwtInfo {

	private Long userId;
	private String userName;
	private String roleName;

	public JwtInfo(Long userId, String userName, String roleName) {

		this.userId = userId;
		this.userName = userName;
		this.roleName = roleName;

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
