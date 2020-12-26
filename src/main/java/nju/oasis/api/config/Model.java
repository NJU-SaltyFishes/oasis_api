package nju.oasis.api.config;

import java.util.regex.Pattern;

//公共常量
public class Model {

    public final static String NO_ARTICLE_EXCEPTION = "No ArticleESVO Present!";

    public final static String IEEE = "IEEE";

    public final static String ACM = "ACM";

    public final static String ELASTIC_URI = "http://39.105.94.200:9200";

    public final static String ELASTIC_USERNAME = "elastic";

    public final static String SEARCH_ARTICLE = "article";
    public final static String SEARCH_PEOPLE = "people";

    public final static String ELASTIC_PASSWORD = "elastic.org147258369";

    public final static int MAX_ABSTRACT_LENGTH = 700;

    public final static int MAX_ES_QUERY_COUNT = 10000;

    //默认客户端连接KEEP_ALIVE时间为20分钟
    public final static long CLIENT_KEEP_ALIVE_TIME = 20*60*1000;

    private final static String PATTERN_ITEM = "((article=\"([^\\\"\\,\\&]+)\"){1}|(author=\"([^\\\"\\,\\&]+)\"){1}|(affiliation=\"([^\\\"\\,\\&]+)\"){1}|(direction=\"([^\\\"\\,\\&]+)\"){1}|[\\,\\&]{1})+";

    public final static Pattern PATTERN = Pattern.compile(PATTERN_ITEM);

    public final static int ARTICLE_POS = 3;

    public final static int AUTHOR_POS = 5;

    public final static int AFFILIATION_POS = 7;

    public final static int DIRECTION_POS = 9;
}
