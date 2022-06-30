package codesver.tannaeserver.controller;


import codesver.tannaeserver.domain.Login;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {

    @GetMapping("/account/login")
    @ResponseBody
    public String login(Login login) {
        System.out.println(login.getId() + " " + login.getPw());
        System.out.println("SUCCESS");
        JSONObject test = new JSONObject();
        test.put("message", "success");
        return test.toString();
    }
}
