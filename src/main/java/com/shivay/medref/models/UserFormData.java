package com.shivay.medref.models;

public class UserFormData {
	private Long userId;
	private Long addressId;
	private String firstName;
	private String lastName;
	private Integer age;
	private String email;
	private Long phoneNumber;
	private String password;
	private String city;
	private String state;
	private String repeatPassword;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	public UserFormData(Long userId, Long addressId, String firstName, String lastName, Integer age, String email,
			Long phoneNumber, String password, String city, String state, String repeatPassword) {
		super();
		this.userId = userId;
		this.addressId = addressId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.city = city;
		this.state = state;
		this.repeatPassword = repeatPassword;
	}
	public UserFormData() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserFormData [userId=" + userId + ", addressId=" + addressId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", age=" + age + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", password=" + password + ", city=" + city + ", state=" + state + ", repeatPassword="
				+ repeatPassword + "]";
	}

	public Address extractAddress() {
		return new Address(getAddressId(),getCity(),getState());
	}
	
	
	
	
	
	
	
	
}
