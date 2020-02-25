package entity;

import com.id0304.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author WuZhengHua
 * @Description TODO 订单实体类
 * @Date 2019/7/15 20:41
 */
@Getter
@Setter
public class OrderEntity extends BaseEntity {
    private Long userid;
    private Long orderid;
}
