package reactor.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.Collections;
import java.util.List;

@Configuration
public class ReactorResourceServerConfiguration {

//    private static final String RESOURCE_ID = "users";
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(RESOURCE_ID).stateless(false);
//    }
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        //super.configure(http);
//        //.antMatchers("/user").permitAll()
//        http
//                .authorizeRequests()
//                .antMatchers("/users/register").permitAll()
//                .antMatchers("/oauth/token").permitAll()
//                .antMatchers(HttpMethod.GET, "/alerts/{id}").authenticated()
//                .antMatchers(HttpMethod.GET, "/events/{id}").authenticated()
//                .antMatchers("/notifications/**").authenticated()
//                .antMatchers("/groups/**").authenticated();
//
//        http.csrf().disable();
//    }

    @Bean
    protected ResourceServerConfiguration userResources() {
        ResourceServerConfiguration resource = new ResourceServerConfiguration() {
            public void setConfigurers(List<ResourceServerConfigurer> configurers) {
                super.setConfigurers(configurers);
            }
        };
        resource.setConfigurers(Collections.singletonList(new ResourceServerConfigurerAdapter() {
            private static final String RESOURCE_ID = "users";

            @Override
            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
                resources.resourceId(RESOURCE_ID).stateless(false);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers("/users/register").permitAll()
                    .antMatchers("/oauth/token").permitAll()
                    .antMatchers(HttpMethod.GET, "/alerts/{id}").authenticated()
                    .antMatchers(HttpMethod.GET, "/events/{id}").authenticated()
                    .antMatchers("/notifications/**").authenticated()
                    .antMatchers("/groups/**").authenticated()
                    .antMatchers("/cloud/**").permitAll();

                http.csrf().disable();
            }
        }));
        resource.setOrder(4);
        return resource;
    }

    @Bean
    protected ResourceServerConfiguration serviceResources() {
        ResourceServerConfiguration resource = new ResourceServerConfiguration() {
            public void setConfigurers(List<ResourceServerConfigurer> configurers) {
                super.setConfigurers(configurers);
            }
        };
        resource.setConfigurers(Collections.singletonList(new ResourceServerConfigurerAdapter() {
            private static final String RESOURCE_ID = "service";

            @Override
            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
                resources.resourceId(RESOURCE_ID).stateless(false);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http.antMatcher("/service/**")
                        .authorizeRequests()
                        .antMatchers(HttpMethod.POST, "/alerts/{id}").authenticated()
                        .antMatchers(HttpMethod.POST, "/events/{id}").authenticated()
                        .antMatchers(HttpMethod.GET, "/alerts/test").authenticated()
                        .antMatchers(HttpMethod.POST, "/faces/{id}").authenticated();

                http.csrf().disable();
            }
        }));
        resource.setOrder(5);
        return resource;
    }


//    @Bean
//    protected AuthenticationEntryPoint getCustomerEntryPoint() {
//        return new OAuth2AuthenticationEntryPoint();
//    }

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
