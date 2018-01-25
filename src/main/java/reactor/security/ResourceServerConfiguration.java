package reactor.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "users";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //.antMatchers("/user").permitAll()
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/users/register").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/**").authenticated();

        http.csrf().disable();
    }


    @Bean
    protected AuthenticationEntryPoint getCustomerEntryPoint() {
        return new OAuth2AuthenticationEntryPoint();
    }

//    @Configuration
//    public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//        @Override
//        public void configure(WebSecurity web) throws Exception {
//            web.ignoring().antMatchers("/users/register");
//        }
//
//    }


}
