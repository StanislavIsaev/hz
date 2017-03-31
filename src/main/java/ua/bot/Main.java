package ua.bot;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.faces.webapp.FacesServlet;
import java.util.HashMap;

/**
 * Created by SEED on 30.03.2017.
 */
@SpringBootApplication
public class Main extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.jsf");
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
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

}
