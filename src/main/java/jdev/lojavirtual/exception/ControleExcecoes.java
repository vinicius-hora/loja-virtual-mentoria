package jdev.lojavirtual.exception;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jdev.lojavirtual.dto.ObjetoErroDto;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler{

    //captura exceções do projeto
    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ObjetoErroDto objetoErroDto = new ObjetoErroDto();
        
        String msg = "";
        if(ex instanceof MethodArgumentNotValidException){
            List<ObjectError> list = ((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors();
            for(ObjectError error : list){
                msg += error.getDefaultMessage() + "\n";
            }
        }
        else{
            msg = ex.getMessage();
        }
        objetoErroDto.setError(msg);
        objetoErroDto.setCode(status.value() + "==> " + status.getReasonPhrase());
        
        return new ResponseEntity<Object>(objetoErroDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //captura erros do banco
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> HandleExceptionIntegry(Exception ex){
        ObjetoErroDto objetoErroDto = new ObjetoErroDto();
        
        String msg = "";
        if(ex instanceof SQLException){
           msg = "Erro de SQL:" + ((SQLException)ex).getCause().getCause().getMessage();
            
        }
        if(ex instanceof DataIntegrityViolationException){
            msg = "ERRO DE INTEGRIDADE" + ((DataIntegrityViolationException)ex).getCause().getCause().getMessage();
             
         }
        if(ex instanceof ConstraintViolationException){
        msg = "ERRO DE CAHVE ESTRANGEIRA" + ((ConstraintViolationException)ex).getCause().getCause().getMessage();
            
        }
        else{
            msg = ex.getMessage();
        }
        objetoErroDto.setError(msg);
        objetoErroDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        
        return new ResponseEntity<Object>(objetoErroDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    
}
