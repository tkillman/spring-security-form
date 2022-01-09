package com.example.springsecurityform.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class LoggingFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start(((HttpServletRequest)servletRequest).getRequestURI());
        // 이걸 해줘야 다음 filter들이 동작한다.
        filterChain.doFilter(servletRequest,servletResponse);

        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());
    }
}
