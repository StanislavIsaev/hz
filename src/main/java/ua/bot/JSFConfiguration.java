package ua.bot;

import com.sun.faces.config.FacesInitializer;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class JSFConfiguration implements ServletContextInitializer {

    @Bean
    public ServletRegistrationBean facesServletRegistration() {

        ServletRegistrationBean servletRegistrationBean = new JsfServletRegistrationBean();

        return servletRegistrationBean;
    }

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        //sc.setInitParameter("primefaces.THEME", "Knight");
        sc.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
        sc.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
        sc.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    }

    @SuppressWarnings("serial")
    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer csc = new CustomScopeConfigurer();
        csc.setScopes(new HashMap<String, Object>() {{
            put("view", new ViewScope());
        }});
        return csc;
    }

    @Bean
    public MimeMappings mimeMappings() {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("woff", "application/x-font-woff");
        mappings.add("woff2", "application/x-font-woff2");
        mappings.add("eot", "application/vnd.ms-fontobject");
        mappings.add("otf", "font/opentype");
        mappings.add("ttf", "application/x-font-ttf");
        mappings.add("svg", "image/svg+xml");
        mappings.add("ico", "image/x-icon");
        return mappings;
    }

    public class JsfServletRegistrationBean extends ServletRegistrationBean {

        public JsfServletRegistrationBean() {
            super();
        }

        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {

            FacesInitializer facesInitializer = new FacesInitializer();

            Set<Class<?>> clazz = new HashSet<Class<?>>();
            clazz.add(JSFConfiguration.class);
            facesInitializer.onStartup(clazz, servletContext);
        }
    }

}