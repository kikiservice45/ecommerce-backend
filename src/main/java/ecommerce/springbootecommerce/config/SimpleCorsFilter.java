package ecommerce.springbootecommerce.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {



    @Value("${app.client.url}")
    private  String  clientAppUrl="";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }



    public SimpleCorsFilter() {
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response= (HttpServletResponse) res;
        HttpServletRequest  request= (HttpServletRequest) req;
        Map<String,String> map= new HashMap<>();
        // Add CORS headers to allow cross-origin requests
        String originHeader=request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);  // Allows all origins. Customize as needed.
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        //response.setHeader("Access-Control-Allow-Credentials", "true");  // Allow credentials (cookies, etc.)
        response.setHeader("Access-Control-Max-Age", "3600");  // Cache preflight requests for 1 hour

        // If it's an OPTIONS request, respond with 200 OK directly
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

        }else
            chain.doFilter(req,res);
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
