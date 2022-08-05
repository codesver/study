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
