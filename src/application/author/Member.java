package application.author;

public class Member {
	
	private String name;
	private String code;
	private String role;
	private String contribution;
	private String imagePath;
	
	public Member(String name, String code, String role, String contribution, String imagePath) {
		super();
		this.name = name;
		this.code = code;
		this.role = role;
		this.contribution = contribution;
		this.imagePath = imagePath;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getContribution() {
		return contribution;
	}
	public void setContribution(String contribution) {
		this.contribution = contribution;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	

}
