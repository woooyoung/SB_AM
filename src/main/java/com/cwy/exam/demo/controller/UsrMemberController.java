package com.cwy.exam.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.cwy.exam.demo.service.GenFileService;
import com.cwy.exam.demo.service.MemberService;
import com.cwy.exam.demo.util.Ut;
import com.cwy.exam.demo.vo.Member;
import com.cwy.exam.demo.vo.ResultData;
import com.cwy.exam.demo.vo.Rq;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;
	@Autowired
	private GenFileService genFileService;

	@RequestMapping("usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {

		if (Ut.empty(loginId)) {
			return ResultData.from("F-A1", "아이디를 입력해주세요");
		}

		Member oldMember = memberService.getMemberByLoginId(loginId);

		if (oldMember != null) {
			return ResultData.from("F-A2", "해당 아이디는 이미 사용중입니다", "logindId", loginId);
		}

		return ResultData.from("S-1", "사용 가능한 아이디입니다", "logindId", loginId);
	}

	@RequestMapping("usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email, @RequestParam(defaultValue = "/") String afterLoginUri, MultipartRequest multipartRequest) {

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return rq.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}

		int newMemberId = (int) joinRd.getBody().get("id");

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);

			if (multipartFile.isEmpty() == false) {
				genFileService.save(multipartFile, newMemberId);
			}
		}

		String afterJoinUri = "../member/login?afterLoginUri=" + Ut.getUriEncoded(afterLoginUri);
		return rq.jsReplace("회원가입이 완료되었습니다. 로그인 후 이용해주세요", afterJoinUri);
	}

	@RequestMapping("usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}

	@RequestMapping("usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}

	@RequestMapping("usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {

		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("아이디를 입력해주세요");
		}

		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("아이디를 잘못 입력했습니다");
		}

		if (member.getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return Ut.jsHistoryBack("비밀번호가 일치하지 않습니다");
		}

		if (member.isDelStatus() == true) {
			return Ut.jsReplace("사용 정지 된 계정입니다", "/");
		}

		rq.login(member);

		return Ut.jsReplace(Ut.f("%s님 환영합니다", member.getNickname()), afterLoginUri);
	}

	@RequestMapping("usr/member/findLoginId")
	public String showFindLoginId() {
		return "usr/member/findLoginId";
	}

	@RequestMapping("usr/member/doFindLoginId")
	@ResponseBody
	public String doFindLoginId(String name, String email,
			@RequestParam(defaultValue = "/") String afterFindLoginIdUri) {

		Member member = memberService.getMemberByNameAndEmail(name, email);

		if (member == null) {
			return Ut.jsHistoryBack("이름과 이메일을 확인해주세요");
		}

		return Ut.jsReplace(Ut.f("회원님의 아이디는 [ %s ] 입니다", member.getLoginId()), afterFindLoginIdUri);
	}

	@RequestMapping("usr/member/findLoginPw")
	public String showFindLoginPw() {
		return "usr/member/findLoginPw";
	}

	@RequestMapping("usr/member/doFindLoginPw")
	@ResponseBody
	public String doFindLoginPw(String loginId, String email,
			@RequestParam(defaultValue = "/") String afterFindLoginPwUri) {

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("일치하는 회원이 없습니다");
		}

		if (member.getEmail().equals(email) == false) {
			return Ut.jsHistoryBack("이메일이 일치하지 않습니다");
		}

		ResultData notifyTempLoginPwByEmailRd = memberService.notifyTempLoginPwByEmailRd(member);

		return Ut.jsReplace(notifyTempLoginPwByEmailRd.getMsg(), afterFindLoginPwUri);
	}

	@RequestMapping("usr/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {

		rq.logout();

		return Ut.jsReplace("로그아웃 되었습니다", afterLogoutUri);
	}

	@RequestMapping("usr/member/myPage")
	public String showMyPage() {

		return "usr/member/myPage";
	}

	@RequestMapping("/usr/member/checkPassword")
	public String showcheckPassword() {

		return "usr/member/checkPassword";
	}

	@RequestMapping("/usr/member/doCheckPassword")
	@ResponseBody
	public String doCheckPassword(String loginPw, String replaceUri) {
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("비밀번호를 입력해주세요");
		}

		if (rq.getLoginedMember().getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return rq.jsHistoryBack("비밀번호가 일치하지 않습니다");
		}

		if (replaceUri.equals("../member/modify")) {
			String memberModifyAuthKey = memberService.genMemberModifyAuthKey(rq.getLoginedMemberId());

			replaceUri += "?memberModifyAuthKey=" + memberModifyAuthKey;
		}

		return rq.jsReplace("", replaceUri);
	}

	@RequestMapping("/usr/member/modify")
	public String showModify(String memberModifyAuthKey) {
		if (Ut.empty(memberModifyAuthKey)) {
			return rq.jsHistoryBackOnView("회원 수정 인증코드가 필요합니다");
		}

		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(),
				memberModifyAuthKey);

		if (checkMemberModifyAuthKeyRd.isFail()) {
			return rq.jsHistoryBackOnView(checkMemberModifyAuthKeyRd.getMsg());
		}

		return "usr/member/modify";
	}

	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(String memberModifyAuthKey, String loginPw, String name, String nickname,
			String cellphoneNum, String email, MultipartRequest multipartRequest) {
		if (Ut.empty(memberModifyAuthKey)) {
			return rq.jsHistoryBack("회원 수정 인증코드가 필요합니다");
		}

		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(),
				memberModifyAuthKey);

		if (checkMemberModifyAuthKeyRd.isFail()) {
			return rq.jsHistoryBack(checkMemberModifyAuthKeyRd.getMsg());
		}

		if (Ut.empty(loginPw)) {
			loginPw = null;
		}

		ResultData modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, email,
				cellphoneNum);

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);

			if (multipartFile.isEmpty() == false) {
				genFileService.save(multipartFile, rq.getLoginedMemberId());
			}
		}

		return rq.jsReplace(modifyRd.getMsg(), "/usr/member/myPage");

	}

}
