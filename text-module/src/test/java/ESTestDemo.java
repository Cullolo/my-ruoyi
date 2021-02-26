

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.customer.util.ESClientUtil;
import com.ruoyi.customer.util.HttpClientUtil;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @Description TODO
 * @Author xianpei.qin
 * @date 2021/02/20 16:22
 */
public class ESTestDemo {

    //初始化client
    public static final RequestOptions COMMON_OPTIONS;
    public static RestHighLevelClient client;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
        client = ESClientUtil.getClient();
    }

    /**
     * 判断索引是否存在
     * @throws IOException
     */
    @Test
    public void testExists() throws IOException {
        //1 准备request对象
        GetIndexRequest request = new GetIndexRequest();
        GetIndexRequest player_info = request.indices("player_info");
        // 2 获取client去检查
        RestHighLevelClient client = ESClientUtil.getClient();
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 通过RestHighLevelClient简单查询ES数据
     * @throws IOException
     */
    @Test
    public void testGet() throws IOException {
        //创建请求
        GetRequest request = new GetRequest();
        //设置查询参数
        request.index("player_info").id("1");
        //执行查询
        GetResponse documentFields = client.get(request, COMMON_OPTIONS);
        Map<String, Object> source = documentFields.getSource();
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    /**
     * 通过httpPost查询ES
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void testMatch() throws IOException, URISyntaxException {

        String searchUrl = "http://192.168.78.100:9200/_search";
        String jsonString = "{\"query\":{\"constant_score\":{\"filter\":{\"term\":{\"salary\":4000}}}}}";

        String result = HttpClientUtil.doPost(searchUrl, jsonString, "application/json;charset=UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(result);
        String hits = jsonObject.getString("hits");
        String _score = jsonObject.getString("_score");
        System.out.println(result);
        System.out.println(hits);
        System.out.println(_score);

    }

    /**
     * 通过httpGet查询ES
     * @throws IOException
     */
    @Test
    public void testSql() throws IOException {
        String sqlUrl = "http://192.168.78.100:9200/_sql";
        String jsonStr2  = "{\"sql\":\"select team.keyword,avg(salary) from player_info group by team.keyword\"}";
        String result = HttpClientUtil.doGet(sqlUrl, jsonStr2, "application/json;charset=UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println(result);
        System.out.println(jsonObject.get("aggregations"));

    }

}
