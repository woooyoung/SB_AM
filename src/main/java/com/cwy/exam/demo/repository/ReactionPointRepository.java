package com.cwy.exam.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {

	@Select("""
			<script>
				SELECT IFNULL(SUM(RP.point),0) AS s
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = #{relTypeCode}
				AND RP.relId = #{relId}
				AND RP.memberId = #{actorId}
			</script>
						""")
	int getSumReactionPointByMemberId(int actorId, String relTypeCode, int relId);

	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				memberId = #{actorId},
				`point` = 1
			</script>
						""")
	void addGoodReactionPoint(int actorId, String relTypeCode, int relId);

	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				memberId = #{actorId},
				`point` = -1
			</script>
						""")
	void addBadReactionPoint(int actorId, String relTypeCode, int relId);

	@Delete("""
			<script>
				DELETE FROM reactionPoint
				WHERE relTypeCode = #{relTypeCode}
				AND relId = #{relId}
				AND memberId = #{actorId}
			</script>
						""")
	void deleteGoodReactionPoint(int actorId, String relTypeCode, int relId);

	@Delete("""
			<script>
				DELETE FROM reactionPoint
				WHERE relTypeCode = #{relTypeCode}
				AND relId = #{relId}
				AND memberId = #{actorId}
			</script>
						""")
	void deleteBadReactionPoint(int actorId, String relTypeCode, int relId);

}
