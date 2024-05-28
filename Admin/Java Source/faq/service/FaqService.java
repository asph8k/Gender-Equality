package kr.ge.kwdi.gep.admin.domain.faq.service;

import java.util.List;
import java.util.stream.Collectors;

import kr.ge.kwdi.gep.admin.domain.account.dto.AccountVO;
import kr.ge.kwdi.gep.admin.global.util.TextUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ge.kwdi.gep.admin.domain.faq.dao.FaqDAO;
import kr.ge.kwdi.gep.admin.domain.faq.dto.FaqDTO;
import kr.ge.kwdi.gep.admin.domain.faq.dto.FaqVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaqService {

	private final FaqDAO faqDAO;

	/** Faq 목록 조회 */
	public List<FaqDTO.ListResponse> getFaqList(FaqDTO.SearchRequest searchRequest) {
		FaqVO.SearchParam searchParam = FaqVO.SearchParam.builder()
				.q(searchRequest.getQ())
				.searchType(searchRequest.getSearchType())
				.build();
		// 조회
		List<FaqVO.ListResult> listResults = faqDAO.selectFaqList(searchParam);
		// 변환
		List<FaqDTO.ListResponse> listResponses = listResults.stream()
				.map(result -> FaqDTO.ListResponse.builder()
						.id(result.getId())
						.question(result.getQuestion())
						.answer(result.getAnswer())
						.frstJobObj(result.getFrstJobObj())
						.frstJobDt(result.getFrstJobDt())
						.visible(result.getVisible())
						.build())
				.collect(Collectors.toList());

		return listResponses;
	}

	/** Faq 상세 조회 */
	public FaqDTO.DetailResponse getFaqDetail(Long entityId) {
		// 조회
		FaqVO.DetailResult detailResult = faqDAO.selectFaqDetail(entityId);

		// 변환
		FaqDTO.DetailResponse detailResponse = FaqDTO.DetailResponse.builder()
				.id(detailResult.getId())
				.question(detailResult.getQuestion())
				.answer(detailResult.getAnswer())
				.visible(TextUtil.convertStringToBoolean(detailResult.getVisible()))
				.build();

		return detailResponse;
	}

	/** Faq 리스트 생성 */
	@Transactional
	public int saveFaq(FaqDTO.InsertRequest insertRequest) {
		// 반환값 설정
		int result = 0;

		// 로그인 중인 사용자 정보
		AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 순서 + 1 증가값 조회
		FaqVO.OrdrCountResult ordrCountResult = faqDAO.selectOrdrCount();

		FaqVO.InsertParam insertParam = FaqVO.InsertParam.builder()
				.question(insertRequest.getQuestion())
				.answer(insertRequest.getAnswer())
				.ordr(ordrCountResult.getOrdrCount())
				.frstJobObj(accountVO.getJobObject())
				.visible(TextUtil.convertBooleanToString(insertRequest.getVisible()))
				.build();

		int insertFaqChk = faqDAO.insertFaq(insertParam);

		if(insertFaqChk == 1) {
			result = 1;
		}

		return result;
	}

	/** Faq 리스트 수정 */
	@Transactional
	public int updateFaq(Long entityId, FaqDTO.UpdateRequest updateRequest) {
		// 반환값 설정
		int result = 0;

		// 로그인 중인 사용자 정보
		AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		FaqVO.UpdateParam updateParam = FaqVO.UpdateParam.builder()
				.entityId(entityId)
				.question(updateRequest.getQuestion())
				.answer(updateRequest.getAnswer())
				.visible(TextUtil.convertBooleanToString(updateRequest.getVisible()))
				.lastJobObj(accountVO.getJobObject())
				.build();

		int updateFaqChk = faqDAO.updateFaq(updateParam);

		if(updateFaqChk == 1) {
			result = 1;
		}

		return result;
	}

	/** Faq 리스트 삭제 */
	@Transactional
	public int deleteFaq(FaqDTO.deleteList deleteList) {
		// 반환값 설정
		int result = 0;

		FaqVO.DeleteParam deleteParam = FaqVO.DeleteParam.builder()
				.list(deleteList.getDeleteList())
				.build();

		int deleteFaqChk = faqDAO.deleteFaq(deleteParam);

		if(deleteFaqChk == 1) {
			result = 1;
		}

		return result;
	}
}
