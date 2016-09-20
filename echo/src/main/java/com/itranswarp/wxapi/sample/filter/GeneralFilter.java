package com.itranswarp.wxapi.sample.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class GeneralFilter implements Filter {

	Log log = LogFactory.getLog(getClass());

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		response.setCharacterEncoding("UTF-8");
		logRequest(request);
		chain.doFilter(req, resp);
	}

	void logRequest(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder(512);
		sb.append(request.getMethod()).append(": ").append(request.getRequestURI()).append('\n');
		sb.append("Content-Type: ").append(request.getContentType()).append('\n');
		sb.append("Content-Length: ").append(request.getContentLength()).append('\n');
		Map<String, String[]> params = request.getParameterMap();
		sb.append("Request Parameters: ").append(params.size()).append('\n');
		for (String key : params.keySet()) {
			String value = params.get(key)[0];
			sb.append("  ").append(key).append('=').append(value).append('\n');
		}
		log.info(sb.toString());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
