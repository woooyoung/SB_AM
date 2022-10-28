package com.cwy.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cwy.exam.demo.service.ReactionPointService;
import com.cwy.exam.demo.vo.ResultData;
import com.cwy.exam.demo.vo.Rq;

@Controller
public class UsrReactionPointContoller {

	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private Rq rq;

	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {

		boolean actorCanMakeReaction = reactionPointService
				.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId).isSuccess();

		if (actorCanMakeReaction == false) {
			return rq.jsHistoryBackOnView("이미 처리되었습니다.");
		}

		reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		return rq.jsReplace("좋아요!", replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {

		boolean actorCanMakeReaction = reactionPointService
				.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId).isSuccess();

		if (actorCanMakeReaction == false) {
			return rq.jsHistoryBackOnView("이미 처리되었습니다.");
		}

		reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		return rq.jsReplace("싫어요!", replaceUri);
	}

}
