package com.cwy.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cwy.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public void writeArticle(int memberId, int boardId, String title, String body);

	@Select("""
		<script>
			SELECT A.*, M.nickname AS extra__writerName,
			IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
			IFNULL(SUM(IF(RP.point &gt; 0, RP.point, 0)),0) AS extra__goodReactionPoint,
			IFNULL(SUM(IF(RP.point &lt; 0, RP.point, 0)),0) AS extra__badReactionPoint
			FROM article AS A
			LEFT JOIN `member` AS M
			ON A.memberId = M.id
			LEFT JOIN reactionPoint AS RP
			ON RP.relTypeCode = 'article'
			AND A.id = RP.relId
			WHERE A.id = #{id}
			GROUP BY A.id
		</script>
										""")
	public Article getForPrintArticle(int id);

	@Select("""
		<script>
			SELECT A.*,
			IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
			IFNULL(SUM(IF(RP.point &gt; 0, RP.point, 0)),0) AS extra__goodReactionPoint,
			IFNULL(SUM(IF(RP.point &lt; 0, RP.point, 0)),0) AS extra__badReactionPoint
			FROM (
				SELECT A.*, M.nickname AS
				extra__writerName
				FROM article AS A
				LEFT JOIN `member` AS M
				ON A.memberId = M.id WHERE 1
				<if test="boardId != 0">
					AND A.boardId = #{boardId}
				</if>
				<if test="searchKeyword != ''">
					<choose>
						<when test="searchKeywordTypeCode == 'title'">
							AND A.title LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<when test="searchKeywordTypeCode == 'body'">
							AND A.body LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<otherwise>
							AND (
							A.title LIKE CONCAT('%', #{searchKeyword}, '%')
							OR A.body LIKE CONCAT('%', #{searchKeyword}, '%')
							)
						</otherwise>
					</choose>
				</if>
				ORDER BY A.id DESC
				<if test="limitTake != -1">
					LIMIT #{limitStart}, #{limitTake}
				</if>
						) AS A
			LEFT JOIN reactionPoint AS RP
			ON RP.relTypeCode = 'article'
			AND A.id = RP.relId
			GROUP BY A.id
		</script>
							""")
	public List<Article> getForPrintArticles(int boardId, String searchKeywordTypeCode, String searchKeyword,
			int limitStart, int limitTake);

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
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'">
						AND A.title LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'body'">
						AND A.body LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							A.title LIKE CONCAT('%', #{searchKeyword}, '%')
							OR A.body LIKE CONCAT('%', #{searchKeyword}, '%')
							)
					</otherwise>
				</choose>
			</if>
			</script>
							""")
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);

	@Update("""
			<script>
			UPDATE article
			SET hitCount = hitCount + 1
			WHERE id = #{id}
			</script>
				""")
	public int increaseHitCount(int id);

	@Select("""
			<script>
			SELECT hitCount
			FROM article
			WHERE id = #{id}
			</script>
				""")
	public int getArticleHitCount(int id);

}
