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

		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReactionRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanMakeReactionRd.getMsg());
		}

		ResultData addGoodReactionPointRd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		return rq.jsReplace(addGoodReactionPointRd.getMsg(), replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReactionRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanMakeReactionRd.getMsg());
		}

		ResultData addBadReactionPointRd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		return rq.jsReplace(addBadReactionPointRd.getMsg(), replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doCancelGoodReaction")
	@ResponseBody
	public String doCancelGoodReaction(String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReactionRd.isSuccess()) {
			return rq.jsHistoryBackOnView(actorCanMakeReactionRd.getMsg());
		}

		ResultData deleteGoodReactionPointRd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		return rq.jsReplace(deleteGoodReactionPointRd.getMsg(), replaceUri);
	}

	@RequestMapping("/usr/reactionPoint/doCancelBadReaction")
	@ResponseBody
	public String doCancelBadReaction(String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReactionRd.isSuccess()) {
			return rq.jsHistoryBackOnView(actorCanMakeReactionRd.getMsg());
		}

		ResultData deleteBadReactionPointRd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		return rq.jsReplace(deleteBadReactionPointRd.getMsg(), replaceUri);
	}

}
