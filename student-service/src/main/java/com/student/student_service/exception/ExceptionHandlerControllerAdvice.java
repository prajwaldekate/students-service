package com.student.student_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.student.student_service.model.ExceptionModel;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//			Map<String, Object> responseBody = new LinkedHashMap<>();
//		return super.handleMethodArgumentNotValid(ex, headers, status, request);
//	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionModel> handleResourcesNotFoundException(final ResourceNotFoundException exception) {
	    log.error("@ExceptionHandlerControllerAdvice :: handleResourcesNotFoundException :: Exception details: {}", exception.getErrorDesc());

		ExceptionModel error = new ExceptionModel();
		error.setErrorCode(exception.getErrorCode());
		error.setErrorDesc(exception.getErrorDesc());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}
