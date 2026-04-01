package com.rohan.accounts;

public class AccountHolders 
{
	private String accName;
	private String email;
	private Long phone;
	private String address;
	private String accType;
	private int balance;
	
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		if (accType.equalsIgnoreCase("saving")) 
		{
			this.accType="saving";
		} 
		else {
			this.accType = "current";
		}
		
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
}
