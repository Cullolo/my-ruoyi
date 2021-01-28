import com.ruoyi.common.utils.AddressUtils;
import org.testng.annotations.Test;

/**
 * @Description TODO
 * @Author xianpei.qin
 * @date 2021/01/28 14:33
 */
public class ModuleTest {

    @Test
    public void test1(){
        String realAddressByIP = AddressUtils.getRealAddressByIP("10.5.45.4");
        System.out.println(realAddressByIP);

    }
}
