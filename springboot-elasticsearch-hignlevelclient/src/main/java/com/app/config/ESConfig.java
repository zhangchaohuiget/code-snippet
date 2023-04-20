package com.app.config;

import com.app.config.annotation.Document;
import com.app.utils.ClassHelper;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;

@Configuration
public class ESConfig implements FactoryBean<ESClient>, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ESConfig.class);

    private ESClient client;

    @Value("${spring.elasticsearch.jest.uris}")
    private String uris;
    @Value("${spring.elasticsearch.jest.username}")
    private String username;
    @Value("${spring.elasticsearch.jest.password}")
    private String password;
    @Value("${spring.elasticsearch.base.package}")
    private String basePackage;

    @Override
    public void destroy() throws Exception {
        try {
            logger.info("Closing elasticSearch client");
            if (client != null) {
                client.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    @Override
    public ESClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<ESClient> getObjectType() {
        return ESClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    private void buildClient() throws Exception {
        if (StringUtils.isEmpty(uris.replaceAll(" ", ""))) {
            throw new NullPointerException("spring.elasticsearch.jest.uris must not null");
        }
        String[] _uris = uris.split(",");
        HttpHost[] httpHosts = new HttpHost[_uris.length];
        for (int i = 0; i < _uris.length; i++) {
            HttpHost httpHost = HttpHost.create(_uris[i]);
            httpHosts[i] = httpHost;
        }
        RestClientBuilder clientBuilder = RestClient.builder(httpHosts);

        clientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @SneakyThrows
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
                    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
                    credentialsProvider.setCredentials(AuthScope.ANY, credentials);
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }

                IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                        .setIoThreadCount(100)
                        .setConnectTimeout(10000)
                        .setSoTimeout(10000)
                        .build();

                httpClientBuilder.setDefaultIOReactorConfig(ioReactorConfig);
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    @Override
                    public boolean isTrusted(java.security.cert.X509Certificate[] arg0, String arg1) {
                        return true;
                    }
                }).build();

                httpClientBuilder.setSSLContext(sslContext);
                httpClientBuilder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);

                return httpClientBuilder;
            }
        });

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(clientBuilder);
        RequestOptions options = RequestOptions.DEFAULT;
        client = new ESClient(restHighLevelClient, options);

        if (StringUtils.isEmpty(basePackage)) {
            throw new NullPointerException("spring.elasticsearch.base.package property is not set");
        }
        //初始化索引
        Set<Class<?>> clzFromPkg = ClassHelper.getClzFromPkg(basePackage);
        beansToIndices(clzFromPkg);
    }

    private void beansToIndices(Set<Class<?>> classes) throws IOException {
        for (Class clazz : classes) {
            Document document = (Document) clazz.getAnnotation(Document.class);
            if (null == document) {
                continue;
            }
            logger.info("{}", clazz.getName());
            if (!document.createIndex()) {
                continue;
            }
            String index = document.indexName();
            if (StringUtils.isEmpty(index)) {
                throw new NullPointerException("indexName not null");
            }

            GetIndexRequest getIndexRequest = new GetIndexRequest(index);
            boolean exists = client.exists(getIndexRequest);
            XContentBuilder builder = clazzToMapping(clazz);
            if (exists) {
                putMapping(index, builder);
            } else {
                createMapping(index, document.shards(), document.replicas(), builder);
            }
        }
    }

    private XContentBuilder clazzToMapping(Class clazz) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .startObject("properties");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            com.app.config.annotation.Field fieldAnnotation = field.getAnnotation(com.app.config.annotation.Field.class);
            if (null == fieldAnnotation) {
                continue;
            }
            String type = fieldAnnotation.type().toString().toLowerCase();
            builder.startObject(field.getName()).field("type", type).endObject();
        }
        builder.endObject()
                .endObject();
        return builder;
    }

    private void putMapping(String index, XContentBuilder builder) throws IOException {
        PutMappingRequest putMappingRequest = new PutMappingRequest(index);
        putMappingRequest.source(builder);
        AcknowledgedResponse acknowledgedResponse = client.putMapping(putMappingRequest);
        if (acknowledgedResponse.isAcknowledged()) {
            logger.info("{}存在,更新成功", index);
        }
    }

    private void createMapping(String index, short shards, short replicas, XContentBuilder builder) throws IOException {
        CreateIndexRequest createRequest = new CreateIndexRequest(index);
        createRequest.settings(Settings.builder()
                .put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas)
        );

        createRequest.mapping(builder);
        AcknowledgedResponse response = client.create(createRequest);
        boolean acknowledged = response.isAcknowledged();
        if (acknowledged) {
            logger.info("{}创建成功", index);
        } else {
            throw new RuntimeException(index + "创建失败");
        }
    }

}

