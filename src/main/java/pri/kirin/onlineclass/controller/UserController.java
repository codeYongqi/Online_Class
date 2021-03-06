package pri.kirin.onlineclass.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pri.kirin.onlineclass.Model.entity.User;
import pri.kirin.onlineclass.Model.request.LoginRequest;
import pri.kirin.onlineclass.Service.UserService;
import pri.kirin.onlineclass.Utils.JsonData;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pri/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public JsonData register(@RequestBody Map<String, String> userInfo) {
        int rows = userService.save(userInfo);
        return rows == 1 ? JsonData.buildSuccess("注册成功") : JsonData.buildError("注册失败，请重试");
    }

    @PostMapping("login")
    public JsonData login(@RequestBody LoginRequest loginRequest) {
        String token = userService.findByPhoneAndPwd(loginRequest.getPhone(), loginRequest.getPwd());
        return token != null ? JsonData.buildSuccess(token) : JsonData.buildError("登录失败，请重试");
    }

    @GetMapping("find_by_token")
    public JsonData findUserByToken(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("user_id");
        if (userId == null) return JsonData.buildError("查询失败");
        User user = userService.findByUserId(userId);
        return JsonData.buildSuccess(user);
    }

}
