package com.example.dockerPostgres.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.dockerPostgres.constantes.MensagemConstants;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = MensagemConstants.CPF_JA_EXISTE)
public class ObjectBadRequestException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
		public ObjectBadRequestException(String msg) {
			super(msg);
		}
		
		public ObjectBadRequestException(String msg,Throwable cause) {
			super(msg,cause);
		}
		
		
}
