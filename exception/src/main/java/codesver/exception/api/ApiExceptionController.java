package codesver.exception.api;

import codesver.exception.exception.BadRequestException;
import codesver.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    public MemberDTO getMember(@PathVariable("id") String id) {
        if (id.equals("ex"))
            throw new RuntimeException("Wrong user");
        if (id.equals("bad"))
            throw new IllegalArgumentException("Wrong input value");

        if (id.equals("user-ex"))
            throw new UserException("User error");

        return new MemberDTO(id, "hello " + id);
    }

    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException();
    }

    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
    }

    @Data
    @AllArgsConstructor
    static class MemberDTO {
        private String memberId;
        private String name;
    }
}
