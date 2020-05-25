package com.ctt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilePreviewApplicationTests {

    @Resource
    public com.alibaba.druid.pool.DruidDataSource dataSource;

    @Test
    public void contextLoads() throws InterruptedException, IOException {

        Enumeration<URL> resources = ClassLoader.getSystemResources("META-INF/spring-autoconfigure-metadata.properties");
        int i = 0;
        while (resources.hasMoreElements()) {
            i++;
        }
        System.out.println(i);


//		Config config = new Config();
//		RedissonClient client = Redisson.create(config);
//		client.getLock("");
//		System.out.println(dataSource.getClass());


//		Config config = new Config();
//		config.setLockWatchdogTimeout(10000);
//		config.setNettyThreads(3);
//		config.setExecutor(new ThreadPoolExecutor(10, 200, 2, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(10)));
//		config.useSingleServer().setAddress("redis://192.168.1.229:6379")
//				.setConnectionPoolSize(128)
//				.setPassword("123456").setConnectTimeout(5000).setTimeout(3000);
//		RedissonClient client = Redisson.create(config);
//		RLock lock = client.getLock("1234");
//		lock.tryLock(1,TimeUnit.MILLISECONDS);

//		DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
//		TransactionFactory transactionFactory = new JdbcTransactionFactory();
//		Environment environment = new Environment("development", transactionFactory, dataSource);
//		Configuration configuration = new Configuration(environment);
//		configuration.addMapper(TestMapper.class);
//		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
//		try (SqlSession session = sqlSessionFactory.openSession()) {
//			TestMapper mapper = session.getMapper(TestMapper.class);
//			mapper.getTest("123");
//		}

    }

    public static void main(String[] args) throws IOException {
        Enumeration<URL> resources = ClassLoader.getSystemResources("META-INF/spring-autoconfigure-metadata.properties");
        int i = 0;
        while (resources.hasMoreElements()) {
            resources.nextElement();
            i++;
        }
        System.out.println(i);
    }

}
