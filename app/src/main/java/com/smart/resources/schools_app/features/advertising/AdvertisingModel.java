package com.smart.resources.schools_app.features.advertising;

import java.io.Serializable;
import org.threeten.bp.LocalDateTime;

public class AdvertisingModel implements Serializable {
	private int advertisementId;
	private String title;
	private String attachment;
	private String note;
	private LocalDateTime date;

	public int getAdvertisementId(){
		return advertisementId;
	}

	public String getTitle(){
		return title;
	}

	public String getAttachment(){
		return attachment;
	}

	public String getNote(){
		return note;
	}

	public LocalDateTime getDate(){
		return date;
	}


}