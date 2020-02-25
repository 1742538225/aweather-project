import com.id0304.utils.ReflectionUtils;
import entity.UserEntity;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author WuZhengHua
 * @Description TODO
 * @Date 2019/8/15 22:23
 */
public class Demo {

    public static void main(String[] args) {
        UserEntity userEntity = new UserEntity();
        String oldSetString = ReflectionUtils.fatherAndSonFieldAndValue(userEntity);    //包含修改created属性值,需要去除
        String replaceString = oldSetString.substring(oldSetString.indexOf(",created="),oldSetString.indexOf(",updated=")); //获取 ,created=? 这段字符串
        String setString = oldSetString.replace(replaceString, ""); //将 ,created=? 这段字符串用空格代替掉即可取消修改这个属性
        String whereString = ReflectionUtils.fatherMajorFieldAndValue(userEntity);
        SQL sql = new SQL() {
            {
                UPDATE("user");
                SET(setString);
                WHERE(whereString);
            }
        };
        String sqlString = sql.toString();
        System.out.println(sqlString);
    }
}
