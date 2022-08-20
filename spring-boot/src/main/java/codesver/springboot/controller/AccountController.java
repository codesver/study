package codesver.springboot.controller;

import codesver.springboot.transfer.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spring-boot/test")
public class AccountController {

    @GetMapping("/get")
    public Response get() {
        return new Response();
    }

    @PostMapping("/post")
    public Response post() {
        return new Response();
    }

    @PutMapping("/put")
    public Response put() {
        return new Response();
    }


}
