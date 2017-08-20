package marrero.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
//        return new Class[] {WebSecurityConfig.class, SpringConfig.class };
//        return new Class[] {SpringConfig.class };
        return new Class[] {WebSecurityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/*"};
    }

}
