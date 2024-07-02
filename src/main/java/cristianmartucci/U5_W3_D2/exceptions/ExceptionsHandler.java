package cristianmartucci.U5_W3_D2.exceptions;

import cristianmartucci.U5_W3_D2.payloads.ErrorsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorsDTO handleBadRequest(BadRequestException error){
		if (error.getErrorsList() != null){
			String message = error.getErrorsList().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
			return new ErrorsDTO(message, LocalDateTime.now());
		} else {
			return new ErrorsDTO(error.getMessage(), LocalDateTime.now());
		}
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorsDTO handleUnauthorized(NotFoundException error){
		return new ErrorsDTO(error.getMessage(), LocalDateTime.now());
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorsDTO handleNotFound(NotFoundException error){
		return new ErrorsDTO(error.getMessage(), LocalDateTime.now());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorsDTO handleGeneric(Exception error) {
		error.printStackTrace();
		return new ErrorsDTO("Errore generico, risolveremo il prima possibile", LocalDateTime.now());
	}

}
