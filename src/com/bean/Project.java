package com.bean;

public class Project {
	// 项目号
	private Integer id;
	// 项目名
	private String name;
	// 项目概述
	private String overview;
	// 项目描述
	private String description;
	// 项目经理
	private Integer owner_id;
	// 开始时间
	private String start_time;
	// 结束时间
	private String finish_time;
	// 创建人
	private Integer create_id;
	// 创建时间
	private String create_time;
	// 备注
	private String remark;
	// 项目状态
	private String status;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Integer owner_id) {
		this.owner_id = owner_id;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}

	public Integer getCreate_id() {
		return create_id;
	}

	public void setCreate_id(Integer create_id) {
		this.create_id = create_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", overview=" + overview + ", description=" + description
				+ ", owner_id=" + owner_id + ", start_time=" + start_time + ", finish_time=" + finish_time
				+ ", create_id=" + create_id + ", create_time=" + create_time + ", remark=" + remark + ", status="
				+ status + "]";
	}

}
