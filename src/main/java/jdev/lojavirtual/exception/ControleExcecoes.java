package jdev.lojavirtual.exception;

import java.sql.SQLException;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    private static final Logger log = LoggerFactory.getLogger(ControleExcecoes.class);

    @ExceptionHandler({ExceptionMentoriaJava.class})
    public ResponseEntity<Object> handleExceptionCustom(ExceptionMentoriaJava ex){
        ObjetoErroDto objetoErroDto = new ObjetoErroDto();
        objetoErroDto.setError(ex.getMessage());
        objetoErroDto.setCode(HttpStatus.OK.toString());

        return ResponseEntity.status(HttpStatus.OK).body(objetoErroDto);

    }
	

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
        else if(ex instanceof HttpMessageNotReadableException){
            msg = "Não está sendo enviado dados para o BODY";
        }
        else{
            msg = ex.getMessage();
        }
        objetoErroDto.setError(msg);
        objetoErroDto.setCode(status.value() + "==> " + status.getReasonPhrase());
        
        return new ResponseEntity<>(objetoErroDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //captura erros do banco
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleExceptionIntegry(Exception ex){
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

        log.error("Erro interno: {} ", ex.getMessage());
        
        return new ResponseEntity<>(objetoErroDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    
}
