package com.example.dockerPostgres.exception;


import lombok.Data;

@Data
public class StandarError {
	
	private Integer status;
	private String msg;
	private Long timeStamp;
	
	public StandarError() {
		// TODO Auto-generated constructor stub
	}

	public StandarError(Integer status, String msg, Long timeStamp) {
		super();
		this.status = status;
		this.msg = msg;
		this.timeStamp = timeStamp;
	}
	

}
