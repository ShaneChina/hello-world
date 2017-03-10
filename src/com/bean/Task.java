package com.bean;

public class Task {
	// 任务号
	private Integer id;
	// 任务名
	private String name;
	// 任务概述
	private String overview;
	// 任务描述
	private String description;
	// 责任人
	private Integer owner_id;
	// 开始时间
	private String start_time;
	// 结束时间
	private String finish_time;
	// 完成比例
	private float progress;
	// 备注
	private String remark;
	// 任务状态
	private String status;
	// 项目号
	private Integer project_id;
	// 父任务号
	private Integer parent_task_id;

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

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
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

	public Integer getProject_id() {
		return project_id;
	}

	public void setProject_id(Integer project_id) {
		this.project_id = project_id;
	}

	public Integer getParent_task_id() {
		return parent_task_id;
	}

	public void setParent_task_id(Integer parent_task_id) {
		this.parent_task_id = parent_task_id;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", overview=" + overview + ", description=" + description
				+ ", owner_id=" + owner_id + ", start_time=" + start_time + ", finish_time=" + finish_time
				+ ", progress=" + progress + ", remark=" + remark + ", status=" + status + ", project_id=" + project_id
				+ ", parent_task_id=" + parent_task_id + "]";
	}

}
