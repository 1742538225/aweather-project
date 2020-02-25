package entity;

import com.id0304.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class UserEntity extends BaseEntity {
    private String name;
    private String password;
    private String email;
    private String role;
    private Timestamp vip_start;
    private Timestamp vip_stop;
}
