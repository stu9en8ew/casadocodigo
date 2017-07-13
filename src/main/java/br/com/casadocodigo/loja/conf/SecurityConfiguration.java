package br.com.casadocodigo.loja.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.casadocodigo.loja.daos.UsuarioDAO;

//Classe responsável por armazenar as configurações de segurança

@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioDAO usuarioDao;

	// Método para configurar a liberação e bloqueio das requisições
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/produtos/form").hasRole("ADMIN")
		.antMatchers("/carrinho/**").permitAll()
		.antMatchers("/pagamento/**").permitAll()
		.antMatchers(HttpMethod.POST,"/produtos").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/produtos").hasRole("ADMIN")
		.antMatchers("/produtos/**").permitAll()
		.antMatchers("/resources/**").permitAll()
		.antMatchers("/").permitAll()
		
		// Acréscimo da url mágica maluca
		.antMatchers("/url-magica-maluca-oajksfbvad6584i57j54f9684nvi658efnoewfmnvowefnoeijn").permitAll()
				
		.anyRequest().authenticated()
		 //Linha para personalizar a página de login do spring security
		.and().formLogin().loginPage("/login").permitAll()
		 //Linha para definir a opçào de logout
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDao)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

}
