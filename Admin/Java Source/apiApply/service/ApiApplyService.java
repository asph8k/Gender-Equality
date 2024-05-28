package kr.ge.kwdi.gep.admin.domain.apiApply.service;

import kr.ge.kwdi.gep.admin.domain.account.dto.AccountVO;
import kr.ge.kwdi.gep.admin.domain.apiApply.dao.ApiApplyDAO;
import kr.ge.kwdi.gep.admin.domain.apiApply.dto.ApiApplyDTO;
import kr.ge.kwdi.gep.admin.domain.apiApply.dto.ApiApplyVO;
import kr.ge.kwdi.gep.admin.global.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiApplyService {

    private final ApiApplyDAO apiApplyDAO;

    /** API 신청 목록 조회 */
    public List<ApiApplyDTO.ListResponse> selectApiApplyList(ApiApplyDTO.SearchRequest searchRequest) {
        ApiApplyVO.SearchParam searchParam = ApiApplyVO.SearchParam.builder()
                .q(searchRequest.getQ())
                .searchType(searchRequest.getSearchType())
                .build();
        // 조회
        List<ApiApplyVO.ListResult> listResults = apiApplyDAO.selectApiApplyList(searchParam);
        // 변환
        List<ApiApplyDTO.ListResponse> listResponses = listResults.stream()
                .map(result -> ApiApplyDTO.ListResponse.builder()
                        .id(result.getId())
                        .name(result.getName())
                        .telNumber(result.getTelNumber())
                        .instName(result.getInstName())
                        .email(result.getEmail())
                        .serviceName(result.getServiceName())
                        .purpose(result.getPurpose())
                        .visible(result.getVisible())
                        .build())
                .collect(Collectors.toList());

        return listResponses;
    }

    /** API 신청 상세 조회 */
    public ApiApplyDTO.DetailResponse selectApiApplyDetail(Long entityId) {
        // 조회
        ApiApplyVO.DetailResult detailResult = apiApplyDAO.selectApiApplyDetail(entityId);

        // 변환
        ApiApplyDTO.DetailResponse detailResponse = ApiApplyDTO.DetailResponse.builder()
                .id(detailResult.getId())
                .name(detailResult.getName())
                .telNumber(detailResult.getTelNumber())
                .instName(detailResult.getInstName())
                .email(detailResult.getEmail())
                .serviceName(detailResult.getServiceName())
                .purpose(detailResult.getPurpose())
                .apiKey(detailResult.getApiKey())
                .visible(detailResult.getVisible())
                .build();

        return detailResponse;
    }

    /** 반려 조회 */
    public ApiApplyDTO.RjctResponse selectRjct(Long entityId) {
        // 조회
        ApiApplyVO.RjctResult rjctResult = apiApplyDAO.selectRjct(entityId);

        // 변환
        ApiApplyDTO.RjctResponse rjctResponse = ApiApplyDTO.RjctResponse.builder()
                .id(rjctResult.getId())
                .name(rjctResult.getName())
                .content(rjctResult.getContent())
                .created(rjctResult.getCreated())
                .build();

        return rjctResponse;
    }

    /** API 신청 수정 */
    @Transactional
    public int updateApiApplySttsCd(Long entityId, ApiApplyDTO.UpdateRequest updateRequest) {
        // 반환값 설정
        int result = 0;

        // 로그인 중인 사용자 정보
        AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ApiApplyVO.UpdateParam updateParam = ApiApplyVO.UpdateParam.builder()
                .entityId(entityId)
                .visible(TextUtil.convertBooleanToString(updateRequest.getVisible()))
                .lastJobObj(accountVO.getJobObject())
                .build();

        int updateApplyChk = apiApplyDAO.updateApiApplySttsCd(updateParam);

        if(updateApplyChk == 1) {
            result = 1;
        }

        return result;
    }

    /** 반려 등록 */
    @Transactional
    public int updateRjct(Long entityId, ApiApplyDTO.Content content) {
        // 반환값 설정
        int result = 0;

        // 로그인 중인 사용자 정보
        AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ApiApplyVO.RjctParam rjctParam = ApiApplyVO.RjctParam.builder()
                .entityId(entityId)
                .name(accountVO.getUsername())
                .content(content.getContent())
                .lastJobObj(accountVO.getJobObject())
                .build();

        int updateRjctChk = apiApplyDAO.updateRjct(rjctParam);

        if(updateRjctChk == 1) {
            result = 1;
        }

        return result;
    }

    /** API 신청 삭제 */
    @Transactional
    public int updateApiApplyDelYn(ApiApplyDTO.deleteList deleteList) {
        // 반환값 설정
        int result = 0;

        ApiApplyVO.DeleteParam deleteParam = ApiApplyVO.DeleteParam.builder()
                .list(deleteList.getDeleteList())
                .build();

        int deleteApplyChk = apiApplyDAO.updateApiApplyDelYn(deleteParam);

        if(deleteApplyChk == 1) {
            result = 1;
        }

        return result;
    }

    /** 승인 등록 */
    @Transactional
    public int updateAprv(ApiApplyDTO.AprvRequest aprvRequest) {
        // 반환값 설정
        int result = 0;

        int updateAprvChk = apiApplyDAO.updateAprv(aprvRequest.getEntityId());

        if(updateAprvChk == 1) {
            result = 1;
        }

        return result;
    }
}
