package ryndem.mx.glosanet.phase1.datastageconsumer.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class SpringJPAConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(
                new String[]{
                        "ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model",
                        "org.mx.modelo"
                });

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.show.sql", "false");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        return properties;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(
                /*LOCALHOST*/
                //"jdbc:mysql://localhost:3306/GlosaNet?allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull&allowLoadLocalInfile=true");
                /*GLOSADEV SERVER*/

 //               "jdbc:mysql://172.24.32.19:3306/GlosaNet?allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull&allowLoadLocalInfile=true");
//                "jdbc:mysql://localhost:3306/GlosaNet?allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull&allowLoadLocalInfile=true");

                /*GLOSAPROD SERVER*/

                "jdbc:mysql://172.24.24.10:3306/GlosaNet?allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull&allowLoadLocalInfile=true");

        /*LOCALHOST*/
     //   dataSource.setUsername("root");
     //   dataSource.setPassword("5678");

        /*GLOSADEV SERVER*/
//        dataSource.setUsername("user_dev");
//        dataSource.setPassword("cambiame");

        /*GLOSAPROD SERVER*/
        dataSource.setUsername("glosuser");
        dataSource.setPassword("S]fmZ5>VS[*(aLqC");

        return dataSource;
    }
}
