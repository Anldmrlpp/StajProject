package com.StajProject.Company.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.Serializable;

import static com.StajProject.Company.exception.ErrorMessages.DEFAULT_ERROR_MESSAGE;

@Getter
@Setter
public class PermissionException extends RuntimeException implements Serializable { //Sınıfın serileşebilir olması durumu.

    private static final long serialVersionUID = 1L;

    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String detail;
    @NotNull
    private final HttpStatus status;

    protected PermissionException(HttpStatus status, Throwable throwable) {
        super(status.name(), throwable);
        this.status = status;
        this.message = throwable.getMessage();
        this.detail = !StringUtils.hasText(throwable.getMessage()) ? throwable.getMessage() : DEFAULT_ERROR_MESSAGE;
    }

    protected PermissionException(HttpStatus status, String message) {
        super(status.name());
        this.status = status;
        this.message = message;
        this.detail = null;
    }

    protected PermissionException(HttpStatus status, String message, String errorDetail) {
        super(status.name());
        this.status = status;
        this.message = message;
        this.detail = errorDetail;
    }

    //üç metod, belirli hata senaryolarına uygun PermissionException nesneleri yaratmak için kullanıldı.

    public static PermissionException withStatusAndThrowable(HttpStatus status, Throwable throwable){
        return new PermissionException(status, throwable);
    }

    public static PermissionException withStatusAndMessage(HttpStatus status, String message){
        return new PermissionException(status, message);
    }

    public static PermissionException withStatusAndDetails(HttpStatus status, String message, String errorDetail){
        return new PermissionException(status, message, errorDetail);
    }

}
