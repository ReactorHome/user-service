package reactor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import reactor.repositories.GroupRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Autowired
    GroupRepository groupRepository;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        // final DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        final ReactorSecurityMethodExpressionHandler expressionHandler = new ReactorSecurityMethodExpressionHandler();
        expressionHandler.setGroupRepository(groupRepository);
        return expressionHandler;
    }
}
