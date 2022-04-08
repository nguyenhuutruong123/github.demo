package demo.test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class DefaultFilter implements Filter {  

    private RequestDispatcher defaultRequestDispatcher;  

    @Override  
    public void destroy() {}  

    @Override  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)   
          throws IOException, ServletException {  
        defaultRequestDispatcher.forward(request, response);  
    }  

    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {  
        this.defaultRequestDispatcher =   
            filterConfig.getServletContext().getNamedDispatcher("default");  
    }  
}
