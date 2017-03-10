package com.bean;

public class TaskSummary {
	// 任务号
	private Integer id;
	// 任务名
	private String name;
	// 任务概述
	private String overview;
	// 责任人
	private Integer owner_id;
	// 完成比例
	private float progress;
	// 任务状态
	private String status;
	// 父任务号
	private Integer parent_task_id;
	// 子任务数
	private Long subtasksize;

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

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getParent_task_id() {
		return parent_task_id;
	}

	public void setParent_task_id(Integer parent_task_id) {
		this.parent_task_id = parent_task_id;
	}

	public Long getSubtasksize() {
		return subtasksize;
	}

	public void setSubtasksize(Long subtasksize) {
		this.subtasksize = subtasksize;
	}

	@Override
	public String toString() {
		return "TaskSummary [id=" + id + ", name=" + name + ", overview=" + overview + ", owner_id=" + owner_id
				+ ", progress=" + progress + ", status=" + status + ", parent_task_id=" + parent_task_id
				+ ", subtasksize=" + subtasksize + "]";
	}

}
