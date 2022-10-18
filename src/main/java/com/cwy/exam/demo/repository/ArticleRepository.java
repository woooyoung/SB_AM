package com.cwy.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cwy.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public void writeArticle(int memberId, int boardId, String title, String body);

	public Article getForPrintArticle(int id);

	@Select("""
			<script>
			SELECT A.*, M.nickname AS
			extra__writerName
			FROM article AS A
			LEFT JOIN `member` AS M
			ON A.memberId
			= M.id WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			ORDER BY A.id DESC
			</script>
				""")
	public List<Article> getArticles(int boardId);

	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();

	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM article AS A
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			</script>
							""")
	public int getArticlesCount(int boardId);

}
