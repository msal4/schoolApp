package com.smart.resources.schools_app.database.model;

import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

public class NotificationsModel {

	private int id;

	private String content;

	@SerializedName("class_id")
	private Object classId;

	@SerializedName("class_name")
	private Object className;

	private String title;

	private String category;

	private LocalDateTime date;

	public int getId(){
		return id;
	}

	public String getContent(){
		return content;
	}

	public Object getClassId(){
		return classId;
	}

	public Object getClassName(){
		return className;
	}

	public String getTitle(){
		return title;
	}

	public String getCategory(){
		return category;
	}


	public LocalDateTime getDate(){
		return date;
	}

}