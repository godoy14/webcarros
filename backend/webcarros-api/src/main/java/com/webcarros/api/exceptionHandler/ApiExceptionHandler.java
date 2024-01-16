package com.webcarros.api.exceptionHandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.webcarros.domain.exception.EntidadeNaoEncontradaException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		HttpStatus status1 = HttpStatus.BAD_REQUEST;
		String problemType = "DADOS_INVALIDOS";
		
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento" + " correto e tente novamente";
		
		List<Problem.Object> list = new ArrayList<>();
		
		ex.getBindingResult().getAllErrors().forEach(objectError -> {
			
			String name = ((FieldError) objectError).getField();
			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			
			Problem.Object obj = new Problem.Object(name, message);
			list.add(obj);
		});
		
		Problem problem = new Problem(status1.value(), problemType, detail, list);
		
		return handleExceptionInternal(ex, problem, headers, status1, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		String problemType = "RECURSO_NAO_ENCONTRADO";
		String detail = ex.getMessage();
		
		Problem problem = new Problem(status.value(), problemType, detail, null);
		
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

}
