package entity;

import com.id0304.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author WuZhengHua
 * @Description TODO 身份实体类
 * @Date 2019/7/14 13:32
 */
@Getter
@Setter
public class RoleEntity extends BaseEntity{
    private String name;
    private String description;
}
