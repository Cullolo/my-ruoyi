

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.customer.domain.es.ESErrorResponse;
import com.ruoyi.customer.domain.es.ESInitData;
import com.ruoyi.customer.domain.es.ESResponse;
import com.ruoyi.customer.domain.es.Hits;
import com.ruoyi.customer.util.ESClientUtil;
import com.ruoyi.customer.util.HttpClientUtil;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author xianpei.qin
 * @date 2021/02/20 16:22
 */
public class ESTestDemo {

    //初始化client
    public static final RequestOptions COMMON_OPTIONS;
    public static RestHighLevelClient client;
    public static String CONTENT_TYPE = "application/json;charset=UTF-8";
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

    @Test
    public void testIndexRequest() throws IOException {

        RestHighLevelClient client1 = ESClientUtil.getClient();

        SearchRequestBuilder builder = new SearchRequestBuilder((ElasticsearchClient) client1,null);
        builder.setIndices("criminal_info");
        SearchRequest request = new SearchRequest();
        request.indices("criminal_info");

        RestHighLevelClient client = ESClientUtil.getClient();
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit documentFields : hits1) {
            String sourceAsString = documentFields.getSourceAsString();
            System.out.println(sourceAsString);
        }

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
     * 通过httpGet查询ES，安装sql插件
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

    /**
     * 简单伴随分析第一步
     * @throws IOException
     */
    @Test
    public void testCompanion1() throws IOException {
        String ESUrl = "http://192.168.78.100:9200/criminal_info/_search";
        String queryStr = "{\"query\":{\"match\":{\"name\":\"tom1\"}}}";
        String result = HttpClientUtil.doGet(ESUrl, queryStr, "application/json;charset=UTF-8");
        ESResponse esResponse = JSONObject.parseObject(result, ESResponse.class);
        System.out.println(esResponse.toString());
        if(null!=esResponse.getTook()){
            List<Hits.HitContent> hits  = esResponse.getHits().getHits();
            StringBuilder siteBuilder = new StringBuilder();
            for (Hits.HitContent hit : hits) {
                Object source = hit.get_source();
                JSONObject jsonObject = JSONObject.parseObject(source.toString());
                siteBuilder.append(jsonObject.get("site"));
            }
        }else {
            ESErrorResponse esErrorResponse = JSONObject.parseObject(result, ESErrorResponse.class);
            ESErrorResponse.ErrorMsg.RootCause rootCause = esErrorResponse.getError().getRoot_cause().get(0);
            System.out.println("查询发生异常:"+rootCause.getType()+"--"+rootCause.getReason());
        }
    }

    /**
     * 简单伴随分析第二步
     * @throws IOException
     */
    @Test
    public void testCompanion2() throws IOException {
        String ESUrl = "http://192.168.78.100:9200/criminal_info/_search";
        String queryStr2 = "{\"size\":0,\"query\":{\"bool\":{\"must\":{\"match\":{\"site\":\"A1 B1 C1 D1\"}},\"must_not\":{\"match\":{\"name\":\"tom1\"}}}},\"aggs\":{\"result\":{\"terms\":{\"field\":\"name.keyword\",\"size\":10}}}}";
        String result2 = HttpClientUtil.doGet(ESUrl, queryStr2, "application/json;charset=UTF-8");
        ESResponse esResponse1 = JSONObject.parseObject(result2, ESResponse.class);
        Object aggregations = esResponse1.getAggregations();
        System.out.println(aggregations.toString());

    }

    @Test
    public void testESSQL() throws IOException {
        String url = "http://192.168.78.100:9200/criminal_info/_search";
        String paramStr = "{\"query\":{\"match\":{\"name\":\"tom1\"}}}";
        String result = HttpClientUtil.doPost(url,paramStr,"application/json;charset=UTF-8");
        System.out.println(result);


    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void testReadFile() throws IOException {
       /* FileInputStream fis=new FileInputStream("D:\\PT_4GZM202103020602.log");
        InputStreamReader isr=new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);*/
        //简写如下
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\log\\PT_4GZM202103050941.log"), "UTF-8"));
        String line="";
        String[] arrs=null;
        Map<String, Object> jsonMap = new HashMap<>();
        int i = 1856;
        while ((line=br.readLine())!=null) {
            arrs=line.split(",");
           jsonMap.put("info",arrs[0]);
           jsonMap.put("ueTime",arrs[1]);
           jsonMap.put("siteId",arrs[2]);
           jsonMap.put("siteName",arrs[3]);
           jsonMap.put("deviceId",arrs[4]);
           jsonMap.put("deviceName",arrs[5]);
           jsonMap.put("imsi",arrs[6]);
           jsonMap.put("imei",arrs[7]);
           jsonMap.put("mac",arrs[8].equals("\\N")?"":arrs[8]);
           jsonMap.put("longitude",arrs[9]);
           jsonMap.put("latitude",arrs[10]);
           jsonMap.put("mobileType",arrs[11]);
           jsonMap.put("mobileNumber",arrs[12].equals("\\N")?"":arrs[12]);
           jsonMap.put("province",arrs[13].equals("\\N")?"":arrs[13]);
           jsonMap.put("city",arrs[14].equals("\\N")?"":arrs[14]);
           jsonMap.put("postCode",arrs[15].equals("\\N")?"":arrs[15]);
           jsonMap.put("rss",arrs[16]);
           IndexRequest request = new IndexRequest("person_info","person",i+"").source(jsonMap);
           client.index(request,COMMON_OPTIONS);
           i++;
        }
        br.close();
    }


    /**
     * 碰撞分析
     * @throws IOException
     */
    @Test
    public void testCollision1() throws IOException {
        //结果集
        List<ESInitData> resultList = new ArrayList<>();

        //第一个条件：站点：P0819TDD40N49。时间：2021-03-05 09-37-00   2021-03-05 09-37-59
        String url = "http://192.168.78.100:9200/person_info/_search";

        String startDate = "20210-05093700";
        String endDate = "20210305093759";
        String siteId = "P0819TDD40N49";
        String paramStr = "{\"query\":{\"bool\":{\"filter\":{\"range\":{\"ueTime\":{\"gt\":\""+startDate+"\",\"lt\":\""+endDate+"\"}}},\"must\":{\"match\":{\"siteId\":\""+siteId+"\"}}}},\"from\":0,\"size\":10000,\"sort\":{\"ueTime.keyword\":{\"order\":\"desc\"}}}";
        StringBuffer buffer = new StringBuffer();



        String result1 = HttpClientUtil.doGet(url, paramStr, CONTENT_TYPE);
        ESResponse esResponse = JSONObject.parseObject(result1, ESResponse.class);
        List<Hits.HitContent> hits = esResponse.getHits().getHits();
        System.out.println(hits.size());
        for (Hits.HitContent hit : hits) {
            Object source = hit.get_source();
            ESInitData esInitData = JSONObject.parseObject(source.toString(), ESInitData.class);
            resultList.add(esInitData);
        }
        //第二个条件：站点：P0817FDD03N53。时间：2021-03-05 09-39-00  2021-03-05 09-41-59
        startDate = "20210305093900";
        endDate = "20210305094159";
        siteId = "P0817FDD03N53";
        String paramStr2 = "{\"query\":{\"bool\":{\"filter\":{\"range\":{\"ueTime\":{\"gt\":\""+startDate+"\",\"lt\":\""+endDate+"\"}}},\"must\":{\"match\":{\"siteId\":\""+siteId+"\"}}}},\"from\":0,\"size\":10000,\"sort\":{\"ueTime.keyword\":{\"order\":\"desc\"}}}";
        String result2 = HttpClientUtil.doGet(url, paramStr2, CONTENT_TYPE);
        ESResponse esResponse1 = JSONObject.parseObject(result2, ESResponse.class);
        List<Hits.HitContent> hits1 = esResponse1.getHits().getHits();
        System.out.println(hits1.size());
        for (Hits.HitContent hitContent : hits1) {
            Object source = hitContent.get_source();
            ESInitData esInitData = JSONObject.parseObject(source.toString(), ESInitData.class);
            resultList.add(esInitData);
        }

        //取出重复出现的碰撞字段-imse
        List<String> collect = resultList.stream().collect(Collectors.groupingBy(result -> result.getImsi(), Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() > 1)
                .map(entry -> entry.getKey()).collect(Collectors.toList());
        collect.stream().forEach(c-> System.out.println("重复出现的imsi==="+c));
        //根据imsi筛选重复的数据
        List<ESInitData> dataList  = new ArrayList<>();;
        for (String imsi : collect) {
            for (ESInitData esInitData : resultList) {
                if(esInitData.getImsi().equals(imsi)){
                    dataList.add(esInitData);
                }
            }
        }
        dataList.stream().forEach(d-> System.out.println(d));

    }

    /**
     * 用户轨迹-返回结果按时间降序排序
     */
    @Test
    public void trajectory() throws IOException {
        String startDate = "20210201000000";
        String endDate = "20210305235959";
        String imsi = "460111171873932";
        String url = "http://192.168.78.100:9200/person_info/_search";
        String queryStr = "{\"query\":{\"bool\":{\"filter\":{\"range\":{\"ueTime\":{\"gt\":\""+startDate+"\",\"lt\":\""+endDate+"\"}}},\"must\":{\"match\":{\"imsi\":\""+imsi+"\"}}}},\"from\":0,\"size\":10000,\"sort\":{\"ueTime.keyword\":{\"order\":\"desc\"}}}";
        String result = HttpClientUtil.doGet(url, queryStr, CONTENT_TYPE);
        ESResponse esResponse = JSONObject.parseObject(result, ESResponse.class);
        List<Hits.HitContent> hits = esResponse.getHits().getHits();
        List<Map<String,String>> resulList = new ArrayList<>();
        for (Hits.HitContent hit : hits) {
            Object source = hit.get_source();
            ESInitData esInitData = JSONObject.parseObject(source.toString(), ESInitData.class);
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("deviceName",esInitData.getDeviceName());
            resultMap.put("longitude",esInitData.getLongitude());
            resultMap.put("latitude",esInitData.getLatitude());
            resultMap.put("ueTime",esInitData.getUeTime());
            resulList.add(resultMap);
        }
        resulList.stream().forEach(c-> System.out.println(c));
    }


    /**
     * 区域统计：
     */
    @Test
    public void regionalStatistics() throws IOException {

        String url = "http://192.168.78.100:9200/_sql";
        String startDate = "20210201000000";
        String endDate = "20210305235959";
        String cityArr ="'深圳','温州','阜阳'";
        String queryStr = "";
        if(cityArr!=null){
            queryStr = "{\"sql\":\"select * from person_info where ueTime between '"+startDate+"' and '"+endDate+"' and city.keyword in("+cityArr+") group by city.keyword \"}";
        }else {
            queryStr = "{\"sql\":\"select * from person_info where ueTime between '"+startDate+"' and '"+endDate+"' group by city.keyword \"}";
        }
        String result = HttpClientUtil.doGet(url, queryStr, CONTENT_TYPE);
        ESResponse esResponse = JSONObject.parseObject(result, ESResponse.class);
        Object aggregations = esResponse.getAggregations();
        JSONObject jsonObject = JSONObject.parseObject(aggregations.toString());
        Object string = jsonObject.get("city.keyword");
        JSONObject jsonObject1 = JSONObject.parseObject(string.toString());
        Object buckets = jsonObject1.get("buckets");
        JSONArray objects = JSONObject.parseArray(buckets.toString());
        List<Object> resultList = new ArrayList<>();
        for (Object object : objects) {
            resultList.add(object);
        }
        resultList.stream().forEach(c-> System.out.println(c));


    }

}
