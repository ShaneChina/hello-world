package com.bean;

public class ProjectSummary {
	// 项目号
	private Integer id;
	// 项目名
	private String name;
	// 项目概述
	private String overview;
	// 项目经理
	private Integer owner_id;
	// 项目状态
	private String status;
	// 任务数（一级）
	private Long tasksize;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public Integer getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Integer owner_id) {
		this.owner_id = owner_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTasksize() {
		return tasksize;
	}

	public void setTasksize(Long tasksize) {
		this.tasksize = tasksize;
	}

	@Override
	public String toString() {
		return "ProjectSummary [id=" + id + ", name=" + name + ", overview=" + overview + ", owner_id=" + owner_id
				+ ", status=" + status + ", tasksize=" + tasksize + "]";
	}

}
