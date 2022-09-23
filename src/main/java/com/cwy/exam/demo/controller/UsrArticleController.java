package com.cwy.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cwy.exam.demo.vo.Article;

@Controller
public class UsrArticleController {

	private int lastArticleId;
	private List<Article> articles;

	public UsrArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();
	}

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = lastArticleId + 1;
		Article article = new Article(id, title, body);

		articles.add(article);
		lastArticleId = id;

		return article;
	}
}
