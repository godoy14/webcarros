package com.webcarros.api.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

public class Problem {
	
	private Integer status;
	
	private String type;
	
	private String detail;
	
	@CreationTimestamp
	private OffsetDateTime timestamp;
	
	private List<Object> objects;

	public Problem(Integer status, String type, String detail, List<Object> objects) {
		super();
		this.status = status;
		this.type = type;
		this.detail = detail;
		this.objects = objects;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public List<Object> getObjects() {
		return objects;
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
	
	public static class Object {

		private String name;
		private String userMessage;
		
		public Object(String name, String userMessage) {
			super();
			this.name = name;
			this.userMessage = userMessage;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUserMessage() {
			return userMessage;
		}
		public void setUserMessage(String userMessage) {
			this.userMessage = userMessage;
		}
		
		
	}

}
