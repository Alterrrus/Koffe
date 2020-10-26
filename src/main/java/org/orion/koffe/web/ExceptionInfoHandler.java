package org.orion.koffe.web;


import static org.orion.koffe.util.exception.ErrorType.APP_ERROR;
import static org.orion.koffe.util.exception.ErrorType.VALIDATION_ERROR;

import javax.servlet.http.HttpServletRequest;
import org.orion.koffe.util.ValidationUtil;
import org.orion.koffe.util.exception.ApplicationException;
import org.orion.koffe.util.exception.ErrorInfo;
import org.orion.koffe.util.exception.ErrorType;
import org.orion.koffe.util.exception.IllegalRequestDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {

  private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);


  private final MessageSourceAccessor messageSourceAccessor;

  public ExceptionInfoHandler(MessageSourceAccessor messageSourceAccessor) {
    this.messageSourceAccessor = messageSourceAccessor;
  }

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<ErrorInfo> applicationError(HttpServletRequest req,
      ApplicationException appEx) {
    ErrorInfo errorInfo = logAndGetErrorInfo(req, appEx, false, appEx.getType(),
        messageSourceAccessor.getMessage(appEx.getMsgCode(), appEx.getArgs()));
    return ResponseEntity.status(appEx.getType().getStatus()).body(errorInfo);
  }


  @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
  @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
  public ErrorInfo bindValidationError(HttpServletRequest req, Exception e) {
    BindingResult result = e instanceof BindException ?
        ((BindException) e).getBindingResult()
        : ((MethodArgumentNotValidException) e).getBindingResult();

    String[] details = result.getFieldErrors().stream()
        .map(messageSourceAccessor::getMessage)
        .toArray(String[]::new);

    return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, details);
  }

  @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
  @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class,
      HttpMessageNotReadableException.class})
  public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
    return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ErrorInfo handleError(HttpServletRequest req, Exception e) {
    return logAndGetErrorInfo(req, e, true, APP_ERROR);
  }

  //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
  private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException,
      ErrorType errorType, String... details) {
    Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e, logException, errorType);
    return new ErrorInfo(req.getRequestURL(), errorType,
        messageSourceAccessor.getMessage(errorType.getErrorCode()),
        details.length != 0 ? details : new String[]{ValidationUtil.getMessage(rootCause)});
  }
}