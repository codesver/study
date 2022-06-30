package codesver.tannaeserver.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @RequestMapping("/account/login")
    public String login() {
        JSONObject response = new JSONObject("{'message': 'OK'}");
        return response.toString();
    }
}
