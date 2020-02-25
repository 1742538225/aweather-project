package entity;

import com.id0304.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author WuZhengHua
 * @Description TODO 用户订阅城市实体类
 * @Date 2019/8/14 22:20
 */
@Getter
@Setter
@ToString
public class SubscriptionEntity extends BaseEntity {
    private Long uId;
    private String provinceZh;
    private String cityId;
    private String cityZh;
    private Integer ifEmail;  //是否启动邮箱订阅  1为订阅,默认为0未订阅
}
