package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
public class JPAConfiguration {
	
	// Método atualizado para uso do Heroku
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Properties additionalProperties){
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");
		
		// Refatorando a criação do dataSource
		factoryBean.setDataSource(dataSource);
		// Refatorando a criação das properties
		factoryBean.setJpaProperties(additionalProperties);
		
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		
		return factoryBean;
		
	}
	
	
	
	/*
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");
		
		// Refatorando a criação do dataSource
		factoryBean.setDataSource(dataSource);
		// Refatorando a criação das properties
		factoryBean.setJpaProperties(aditionalProperties());
		
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		
		return factoryBean;
		
	}
	*/

	// Método configurado para ser executado pelo Spring
	@Bean
	@Profile("dev")
	// Refatoração das properties do hibernate
	private Properties additionalProperties() {
		Properties props = new Properties();
		
		// Uso com banco Oracle
		//props.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		//props.setProperty("hibernate.show_sql", "true");
		//props.setProperty("hibernate.hbm2ddl.auto", "update");
		
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		
		return props;
	}

	// Refatoração da criação do datasource com o profile dev inicial
	@Bean
	@Profile("dev")
	private DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		// Uso com banco Oracle
		//dataSource.setUsername("system");
		//dataSource.setPassword("adminIT");
		//dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		//dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		
		dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo_dev");
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUsername("root");
	    dataSource.setPassword("devrj");
	    
		return dataSource;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf){
		return new JpaTransactionManager(emf);
	}
	

}
