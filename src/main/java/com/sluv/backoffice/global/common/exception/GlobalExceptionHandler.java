package com.sluv.backoffice.global.common.exception;

import com.sluv.backoffice.global.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sluv.backoffice.global.common.exception.ErrorCode.DB_ACCESS_ERROR;
import static com.sluv.backoffice.global.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;

/*
 * Application 전역 Exception Handler
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Error: {}, Class : {}, Message : {}";
    private static final String LOG_CODE_FORMAT = "Error: {}, Class : {}, Code : {}, Message : {}";

    /**
     * Application Exception
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> applicationException(ApplicationException exception){
        log.error(
                LOG_CODE_FORMAT,
                "ApplicationException",
                exception.getClass().getSimpleName(),
                exception.getErrorCode(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(ErrorResponse.builder()
                        .code(exception.getErrorCode())
                        .message(exception.getMessage())
                        .build()
                );
    }


    /**
     * 런타임 Exception
     */

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeException(RuntimeException exception){
       log.error(
               LOG_FORMAT,
               "RuntimeException",
               exception.getClass().getSimpleName(),
               exception.getMessage()
       );

       return ResponseEntity.internalServerError()
               .body(ErrorResponse.customBuilder()
                       .errorCode(INTERNAL_SERVER_ERROR)
                       .build()
               );
    }

    /**
     * DB Exception
     */

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> dataAccessException(DataAccessException exception) {
        log.error(
                LOG_FORMAT,
                "DataAccessException",
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );

        return ResponseEntity.badRequest()
                .body(ErrorResponse.customBuilder()
                        .errorCode(DB_ACCESS_ERROR)
                        .build()
                );
    }

    /**
     * 기타 Exception
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error(
                LOG_FORMAT,
                "Exception",
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.customBuilder()
                                    .errorCode(INTERNAL_SERVER_ERROR)
                                    .build()
                );

    }

}
