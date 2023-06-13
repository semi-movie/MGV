package vo;

public class Theater {
	private int no;
	private Location location;
	private String name;
	private String address; // 주소
	private String tel;
	private String disabled;
	
	public Theater() {}

	public int getNo() {
		return no;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getDisabled() {
		return disabled;
	}
	
	public void setDisabled(String disable) {
		this.disabled = disable;
	}
}
