package admin_client.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-04-28
 */
public class CommonTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("201912");
        list.add("201908");
        list.add("202001");
        list.add("202012");
        list = list.stream().sorted().collect(Collectors.toList());
        list.forEach(str -> System.out.println(str));
    }
}
