package api;

import com.id0304.entity.PaymentInfo;
import entity.UserEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Map;

@RequestMapping("/user")
public interface UserService {
    @PostMapping("/regist")
    Map<String, Object> regist(@RequestBody UserEntity userEntity);

    @PostMapping("/login")
    Map<String, Object> login(@RequestBody UserEntity userEntity);

    @GetMapping("/logout")
    Map<String, Object> logout(@RequestParam("token") String token);

    @GetMapping("/getUserByToken")
    Map<String, Object> getUserByToken(@RequestParam("token") String token);

    @PostMapping("/updateUser")
    Map<String, Object> updateUser(@RequestBody UserEntity userEntity);

    @GetMapping("/getUserByEmail")
    Map<String, Object> getUserByEmail(@RequestParam("email") String email);

    @GetMapping("/getAllRole")
    Map<String, Object> getAllRole();

    /**
     * @Author WuZhengHua
     * @Description TODO 完成支付之后修改用户vip到期时间
     * @Date 19:55 2019/8/10
     **/
    @PostMapping("/updateUserVipStop")
    Map<String, Object> updateUserVipStop(@RequestBody PaymentInfo paymentInfo) throws ParseException;

    /**
     * @Author WuZhengHua
     * @Description TODO 修改用户邮箱和姓名
     * @Date 19:15 2019/8/17
     **/
    @PostMapping("/updateEmailAndName")
    Map<String,Object> updateEmailAndName(@RequestBody UserEntity userEntity);

}
