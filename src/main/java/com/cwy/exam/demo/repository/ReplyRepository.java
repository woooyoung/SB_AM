package com.cwy.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cwy.exam.demo.vo.Reply;

@Mapper
public interface ReplyRepository {
	@Insert("""
			<script>
				INSERT INTO reply
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{memberId},
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				`body` = #{body}

			</script>
			""")
	void writeReply(int memberId, String relTypeCode, int relId, String body);

	@Select("""
			<script>
				SELECT LAST_INSERT_ID()
			</script>
			""")
	int getLastInsertId();

	@Select("""
			<script>
				SELECT R.*, M.nickname AS extra__writerName
				FROM reply AS R
				LEFT JOIN `member` AS M
				ON R.memberId = M.id
				WHERE R.relTypeCode = #{relTypeCode}
				AND R.relId = #{relId}
				ORDER BY R.id DESC
			</script>
			""")
	List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId);

}
