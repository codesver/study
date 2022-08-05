package codesver.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    public MemberDTO getMember(@PathVariable("id") String id) {
        if (id.equals("ex"))
            throw new RuntimeException("Wrong user");
        if (id.equals("bad"))
            throw new IllegalArgumentException("Wrong input value");

        return new MemberDTO(id, "hello " + id);
    }


    @Data
    @AllArgsConstructor
    static class MemberDTO {
        private String memberId;
        private String name;
    }
}
