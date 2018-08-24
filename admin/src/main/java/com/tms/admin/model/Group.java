package com.tms.admin.model;

import java.util.List;

public class Group {
	
	private int adminId;
	private String adminName;
	private List<Employee> groupEmpIds;
	private String groupName;
	private String description;
	
	
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Employee> getGroupEmpIds() {
		return groupEmpIds;
	}
	public void setGroupEmpIds(List<Employee> groupEmpIds) {
		this.groupEmpIds = groupEmpIds;
	}
	
}
