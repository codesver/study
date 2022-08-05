package codesver.exception.api;

import codesver.exception.exception.UserException;
import codesver.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "Inner error");
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResult runtimeExHandler(RuntimeException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", e.getMessage());
    }

    @GetMapping("/api2/members/{id}")
    public MemberDTO getMember(@PathVariable("id") String id) {
        if (id.equals("ex"))
            throw new RuntimeException("Wrong user");
        if (id.equals("bad"))
            throw new IllegalArgumentException("Wrong input value");
        if (id.equals("user-ex"))
            throw new UserException("User error");

        return new MemberDTO(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDTO {
        private String memberId;
        private String name;
    }
}
