import com.ruoyi.common.utils.AddressUtils;
import org.apache.commons.collections.CollectionUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author xianpei.qin
 * @date 2021/01/28 14:33
 */
public class ModuleTest {

    @Test
    public void test1() {
        String realAddressByIP = AddressUtils.getRealAddressByIP("10.5.45.4");
        System.out.println(realAddressByIP);

    }

    @Test
    public void testCollect() {

        /*List<String> listA = new ArrayList<String>();
        listA.add("a");
        listA.add("b");
        listA.add("b");
        listA.add("c");
        List<String> listB = new ArrayList<String>();
        listB.add("a");
        listB.add("e");
        listB.add("f");
        listB.add("c");*/

        List<String> list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("2222");
        list1.add("3333");

        List<String> list2 = new ArrayList();
        list2.add("3333");
        list2.add("4444");
        list2.add("5555");


        // 交集
        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());
        System.out.println("---得到交集 intersection---");
        intersection.parallelStream().forEach(System.out::println);

        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(Collectors.toList());
        System.out.println("---得到差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out::println);

        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(Collectors.toList());
        System.out.println("---得到差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out::println);

        // 并集
        List<String> listAll = list1.parallelStream().collect(Collectors.toList());
        List<String> listAll2 = list2.parallelStream().collect(Collectors.toList());
        listAll.addAll(listAll2);
        System.out.println("---得到并集 listAll---");
        listAll.parallelStream().forEach(System.out::println);

        // 去重并集
        List<String> listAllDistinct = listAll.stream().distinct().collect(Collectors.toList());
        System.out.println("---得到去重并集 listAllDistinct---");
        listAllDistinct.parallelStream().forEach(System.out::println);

        //补集
        List<String> disjunction = (List<String>) CollectionUtils.disjunction(list1, list2);
        System.out.println("补集：" + disjunction);

    }

    @Test
    public void testSubList() {

        List<String> esIdList = new ArrayList<>();

        esIdList.add("1");
        esIdList.add("2");
        esIdList.add("3");
        esIdList.add("4");
        esIdList.add("5");
        esIdList.add("6");
        esIdList.add("7");
        esIdList.add("8");
        esIdList.add("9");
        esIdList.add("10");
        esIdList.add("11");
        esIdList.add("12");
        esIdList.add("13");
        esIdList.add("14");
        esIdList.add("15");

        for (int i = 0; i < esIdList.size(); i += 5) {
            List<String> sub;
            if (i + 5 > esIdList.size()) {
                sub = esIdList.subList(i, esIdList.size());
            } else {
                sub = esIdList.subList(i, i + 5);
            }

            System.out.println(sub.toString());
        }

    }

    @Test
    public void testSubString(){
        String str = "http://139.9.94.180:29000/pisp-dev/7af6d9a0ec3d4459833410a380803ac4_P0825FDD03N53_D38637060_20210602175729461_FACE_SNAP?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=fj56745eyhgregt43trsada21e21qsa4%2F20210604%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210604T081715Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=c7d80849ac166849ef7e5dd3ac7a000c8f86f6b30fff690afa2678bd58195443";
        String substring = str.substring(str.lastIndexOf("/")+1, str.indexOf("?"));
        System.out.println(substring);


    }


}
