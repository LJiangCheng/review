package com.ljc.review.search.config;

import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

import static org.apache.ibatis.session.AutoMappingBehavior.PARTIAL;
import static org.apache.ibatis.session.ExecutorType.REUSE;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.ljc.review.search.repository"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.driver}")
    private String driverClassName;

    /**
     * 注册 datasourceOne
     */
    @Bean(name = "datasource")
    @Primary
    public DataSource dataSource() {
        DataSourceBuilder<HikariDataSource> dataSourceBuilder = DataSourceBuilder.create().type(HikariDataSource.class);
        return dataSourceBuilder.password(password).url(url).username(username).driverClassName(driverClassName).build();
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return createSqlSessionFactory(dataSource(), "classpath*:mapper/**/*.xml");
    }

    @Bean("transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource, String mapperLocations) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        //开启驼峰命名
        configuration.setMapUnderscoreToCamelCase(true);
        //全局映射器启用缓存
        configuration.setCacheEnabled(true);
        //查询时,关闭关联对象及时加载以提高性能
        configuration.setLazyLoadingEnabled(false);
        //设置关联对象加载的形态,此处为按需加载字段(加载字段由SQL指定),不会加载关联表的所有字段,以提高性能
        configuration.setAggressiveLazyLoading(false);
        //对于位置的SQL查询,允许返回不同的结果集以达到通用的效果
        configuration.setMultipleResultSetsEnabled(true);
        //允许使用自定义的主键值(比如由程序生成的UUID 32位编码作为键值), 数据表的pk生成策略将被覆盖
        configuration.setUseGeneratedKeys(true);
        //给予被嵌套的resultMap以字段-属性的映射支持
        configuration.setAutoMappingBehavior(PARTIAL);
        //允许使用列标签代替列明
        configuration.setUseColumnLabel(true);
        //对于批量更新操作缓存SQL以提高性能
        configuration.setDefaultExecutorType(REUSE);
        // 数据库超过30秒仍未响应则超时
        configuration.setDefaultStatementTimeout(30);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setDataSource(dataSource);
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties props = new Properties();
        // 分页合理化参数,如果 pageSize=0 或者 RowBounds.limit = 0 就会查询出全部的结果
        props.setProperty("reasonable", "true");
        // 支持通过 Mapper 接口参数来传递分页参数,
        props.setProperty("supportMethodsArguments", "true");
        // 添加插件
        pageInterceptor.setProperties(props);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
        return sqlSessionFactoryBean.getObject();
    }

}
