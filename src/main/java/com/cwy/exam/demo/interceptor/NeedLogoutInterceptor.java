package com.cwy.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cwy.exam.demo.vo.Rq;

@Component
public class NeedLogoutInterceptor implements HandlerInterceptor {
	@Autowired
	private Rq rq;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

		if (rq.isLogined()) {
			rq.printHistoryBackJs("로그아웃 후 이용해주세요");
			return false;
		}

		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}

}
