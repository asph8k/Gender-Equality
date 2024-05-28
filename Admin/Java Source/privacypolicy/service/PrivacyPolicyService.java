package kr.ge.kwdi.gep.admin.domain.privacypolicy.service;

import kr.ge.kwdi.gep.admin.domain.account.dto.AccountVO;
import kr.ge.kwdi.gep.admin.domain.privacypolicy.dao.PrivacyPolicyDAO;
import kr.ge.kwdi.gep.admin.domain.privacypolicy.dto.PrivacyPolicyListDTO;
import kr.ge.kwdi.gep.admin.domain.privacypolicy.dto.PrivacyPolicyListVO;
import kr.ge.kwdi.gep.admin.global.common.error.ErrorCode;
import kr.ge.kwdi.gep.admin.global.common.error.GlobalException;
import kr.ge.kwdi.gep.admin.global.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivacyPolicyService {
    private final PrivacyPolicyDAO privacyPolicyDAO;

    /** 개인정보처리방침 리스트 조회 */
    public List<PrivacyPolicyListDTO.ListResponse> selectPrvcPlcyList(PrivacyPolicyListDTO.SearchRequest searchRequest) {
        PrivacyPolicyListVO.SearchParam searchParam = PrivacyPolicyListVO.SearchParam.builder()
                .startDate(TextUtil.formatDateForm(searchRequest.getStartDate()))
                .endDate(TextUtil.formatDateForm(searchRequest.getEndDate()))
                .build();
        // 조회
        List<PrivacyPolicyListVO.ListResult> listResults = privacyPolicyDAO.selectPrvcPlcyList(searchParam);
        // 변환
        List<PrivacyPolicyListDTO.ListResponse> listResponses = listResults.stream()
                .map(result -> PrivacyPolicyListDTO.ListResponse.builder()
                        .id(result.getId())
                        .startDate(result.getStartDate())
                        .endDate(result.getEndDate())
                        .created(result.getCreated())
                        .build())
                .collect(Collectors.toList());

        return listResponses;
    }

    /** 개인정보처리방침 상세 조회 */
    public PrivacyPolicyListDTO.DetailResponse selectPrvcPlcyDetail(Long entityId) {
        // 조회
        PrivacyPolicyListVO.DetailResult detailResult = privacyPolicyDAO.selectPrvcPlcyDetail(entityId);

        // 변환
        PrivacyPolicyListDTO.DetailResponse detailResponse = PrivacyPolicyListDTO.DetailResponse.builder()
                .startDate(detailResult.getStartDate())
                .endDate(detailResult.getEndDate())
                .content(detailResult.getContent())
                .created(detailResult.getCreated())
                .build();

        return detailResponse;
    }

    /** 이전 개인정보처리방침 데이터 조회 */
    public PrivacyPolicyListDTO.BeforeDataInfoResponse selectBeforeDataInfo() {
        // 조회
        PrivacyPolicyListVO.BeforeDataInfoResult beforeDataInfoResult = privacyPolicyDAO.selectBeforeDataInfo();

        // 변환
        PrivacyPolicyListDTO.BeforeDataInfoResponse beforeDataInfoResponse = PrivacyPolicyListDTO.BeforeDataInfoResponse.builder()
                .id(beforeDataInfoResult.getId())
                .startDate(beforeDataInfoResult.getStartDate())
                .endDate(beforeDataInfoResult.getEndDate())
                .content(beforeDataInfoResult.getContent())
                .build();

        return beforeDataInfoResponse;
    }

    /** 최신 등록된 개인정보 조회 */
    public PrivacyPolicyListDTO.FirstInsertPrcyResponse selectFirstInsertPrcy() {
        // 조회
        PrivacyPolicyListVO.FirstInsertPrcyResult firstInsertPrcyResult = privacyPolicyDAO.selectFirstInsertPrcy();

        // 변환
        PrivacyPolicyListDTO.FirstInsertPrcyResponse firstInsertPrcyResponse = PrivacyPolicyListDTO.FirstInsertPrcyResponse.builder()
                .id(firstInsertPrcyResult.getId())
                .content(firstInsertPrcyResult.getContent())
                .build();

        return firstInsertPrcyResponse;
    }


    /** 개인정보처리방침 등록 */
    @Transactional
    public int insertPrvcPlcy(PrivacyPolicyListDTO.InsertRequest insertRequest) {
        // 반환값 설정
        int result = 0;

        // 로그인 중인 사용자 ID
        AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 입력된 내용에서 HTML 태그 및 엔티티 문자를 제거 해주기 위한 로직 처리
        String delHtmlTag = StringEscapeUtils.unescapeHtml(insertRequest.getContent());
        delHtmlTag = delHtmlTag.replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");

        // 개인정보처리방침 데이터 전체 조회
        List<PrivacyPolicyListVO.AllResult> allResult = privacyPolicyDAO.selectAll();

        // 개인정보처리방침 데이터가 하나라도 있을때 작동
        if(!allResult.isEmpty()) {
            // 신규 등록 시 이전 데이터 종료일자 null로 수정을 위해 최근 pk값 가져오기
            PrivacyPolicyListVO.PrvcPlcyPkMaxResult prvcPlcyPkMaxResult = privacyPolicyDAO.selectPrvcPlcyPkMax();
            Long pkMaxResult = prvcPlcyPkMaxResult.getId();

            // 최근 개인정보처리방침 시행일자 조회
            PrivacyPolicyListVO.FirstBeginDtResult firstBeginDtResult = privacyPolicyDAO.selectFirstBeginDt(prvcPlcyPkMaxResult.getId());

            // 최신 등록 데이터 시행일자
            int beginDtMax = Integer.parseInt(firstBeginDtResult.getStartDate());
            // 입력받은 시행일자
            int insertBeginDt = Integer.parseInt(insertRequest.getStartDate());

            // 검증 로직
            if(insertBeginDt == beginDtMax) { // 최근에 등록되어 있는 시행일자와 입력된 시행일자가 같은지 체크
                throw new GlobalException(ErrorCode.SAME_BEGINDATE, "same begindate");
            }

            if(insertBeginDt < beginDtMax) { // 최근에 등록되어 있는 시행일자와 입력된 시행일자가 보다 더 과거인지 체크
                throw new GlobalException(ErrorCode.NOT_INSERT_BEGINDATE, "old begindate");
            }

            PrivacyPolicyListVO.InsertParam insertParam = PrivacyPolicyListVO.InsertParam.builder()
                    .startDate(insertRequest.getStartDate())
                    .content(delHtmlTag)
                    .frstJobObj(accountVO.getJobObject())
                    .build();

            int insertPrivacyChk = privacyPolicyDAO.insertPrvcPlcy(insertParam);

            // 신규 등록 된 시행일자에서 하루 뺀 날짜값 조회 후 이전 종료일자에 수정
            PrivacyPolicyListVO.BeforeDateResult beforeDateResult = privacyPolicyDAO.selectBeforeDate(insertParam.getEntityId());

            PrivacyPolicyListVO.BeforeDateParam beforeDateParam = PrivacyPolicyListVO.BeforeDateParam.builder()
                    .entityId(pkMaxResult)
                    .endDate(beforeDateResult.getEndDate())
                    .build();

            int updateBeforeDtChk = privacyPolicyDAO.updateBeforeDate(beforeDateParam);

            if(insertPrivacyChk == 1 && updateBeforeDtChk == 1) {
                result = 1;
            }
        } else {
            PrivacyPolicyListVO.InsertParam insertParam = PrivacyPolicyListVO.InsertParam.builder()
                    .startDate(insertRequest.getStartDate())
                    .content(delHtmlTag)
                    .frstJobObj(accountVO.getJobObject())
                    .build();

            int insertPrivacyChk = privacyPolicyDAO.insertPrvcPlcy(insertParam);

            // 신규 등록 시 종료일자를 null로 업데이트
            PrivacyPolicyListVO.BeforeDateParam beforeDateParam = PrivacyPolicyListVO.BeforeDateParam.builder()
                    .endDate(null)
                    .build();

            int updateBeforeDtChk = privacyPolicyDAO.updateBeforeDate(beforeDateParam);

            if(insertPrivacyChk == 1 && updateBeforeDtChk == 1) {
                result = 1;
            }
        }

        return result;
    }

    /** 개인정보처리방침 수정 */
    @Transactional
    public int updatePrvcPlcy(Long entityId, PrivacyPolicyListDTO.UpdateRequest updateRequest) {
        // 반환값 설정
        int result = 0;

        // 로그인 중인 사용자 정보
        AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 입력된 내용에서 HTML 태그 및 엔티티 문자를 제거 해주기 위한 로직 처리
        String delHtmlTag = StringEscapeUtils.unescapeHtml(updateRequest.getContent());
        delHtmlTag = delHtmlTag.replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");

        // 최근 등록 된 개인정보의 시행일자를 수정 할 때 이전에 등록 된 시행일자보다 낮은지 검증하기 위한 이전 시행일자 조회
        PrivacyPolicyListVO.PrvsStartDateResult prvsStartDateResult = privacyPolicyDAO.selectPrvsStartDate();

        // 이전 데이터가 없고 최초로 등록 되어 있는 데이터를 수정 했을 때 작동 되는 조건식
        if(ObjectUtils.isEmpty(prvsStartDateResult)) {
            PrivacyPolicyListVO.UpdateParam updateParam = PrivacyPolicyListVO.UpdateParam.builder()
                    .entityId(entityId)
                    .startDate(updateRequest.getStartDate())
                    .content(delHtmlTag)
                    .lastJobObj(accountVO.getJobObject())
                    .build();

            privacyPolicyDAO.updatePrvcPlcy(updateParam);
        } else {
            // 이전 시행일자
            int prvcStartDt = Integer.parseInt(prvsStartDateResult.getStartDate());
            // 입력받은 시행일자
            int updateStartDt = Integer.parseInt(updateRequest.getStartDate());

            // 검증 로직
            if (prvcStartDt == updateStartDt) {
                throw new GlobalException(ErrorCode.SAME_BEGINDATE, "same begindate");
            }

            if (prvcStartDt > updateStartDt) {
                throw new GlobalException(ErrorCode.NOT_UPDATE_BEGINDATE, "too much begindate");
            }

            PrivacyPolicyListVO.UpdateParam updateParam = PrivacyPolicyListVO.UpdateParam.builder()
                    .entityId(entityId)
                    .startDate(updateRequest.getStartDate())
                    .content(delHtmlTag)
                    .lastJobObj(accountVO.getJobObject())
                    .build();

            int updatePrivacyChk = privacyPolicyDAO.updatePrvcPlcy(updateParam);

            // 개인정보처리방침 List에서 두번째 PK 값 조회
            PrivacyPolicyListVO.MinusPKResult minusPKResult = privacyPolicyDAO.selectMinusPK();

            // 수정 된 시행일자에서 하루 뺀 날짜값 조회 후 이전 종료일자에 수정
            PrivacyPolicyListVO.BeforeDateResult beforeDateResult = privacyPolicyDAO.selectBeforeDate(entityId);

            PrivacyPolicyListVO.BeforeDateParam beforeDateParam = PrivacyPolicyListVO.BeforeDateParam.builder()
                    .entityId(minusPKResult.getId())
                    .endDate(beforeDateResult.getEndDate())
                    .build();

            int updateBeforeDtChk = privacyPolicyDAO.updateBeforeDate(beforeDateParam);

            if (updatePrivacyChk == 1 && updateBeforeDtChk == 1) {
                result = 1;
            }
        }

        return result;
    }

    /** 개인정보처리방침 삭제 */
    @Transactional
    public int deletePrvcPlcy(PrivacyPolicyListDTO.deleteList deleteList) {
        // 반환값 설정
        int result = 0;

        // 가장 최근 개인정보처리방침 pk값 가져오기
        PrivacyPolicyListVO.PrvcPlcyPkMaxResult prvcPlcyPkMaxResult = privacyPolicyDAO.selectPrvcPlcyPkMax();
        Long firstPriPlcyNo = prvcPlcyPkMaxResult.getId();

        PrivacyPolicyListVO.DeleteParam deleteParam = PrivacyPolicyListVO.DeleteParam.builder()
                .list(deleteList.getDeleteList())
                .build();

        int deletePrivacyChk = privacyPolicyDAO.deletePrvcPlcy(deleteParam);

        // 신규 개인정보처리방침 삭제 후 목록에서 pk값 조회
        List<PrivacyPolicyListVO.PrvcPlcyPkListResult> prvcPlcyPkListResults = privacyPolicyDAO.selectPrvcPlcyPkList();

        for(PrivacyPolicyListVO.PrvcPlcyPkListResult prvcPlcyPkListResult : prvcPlcyPkListResults) {
            if(ObjectUtils.isEmpty(prvcPlcyPkListResult.getEndDate())) {
                // 만약 최신 데이터가 삭제 되지 않고 중간에 데이터가 삭제 됬을 시 최신 시행일자는 이전 종료일자 + 1 해주기 위한 최신 데이터 이전 Max값 조회
                PrivacyPolicyListVO.NewBeforeDataResult newBeforeDataResult = privacyPolicyDAO.selectNewBeforeData();

                // 종료일자 하루 더하기
                PrivacyPolicyListVO.BeforeDatePlusResult beforeDatePlusResult = privacyPolicyDAO.selectPlusBeforeEndDt(newBeforeDataResult.getId());

                // 신규 데이터 삭제 후 가장 최근 pk값 가져오기
                PrivacyPolicyListVO.PrvcPlcyPkMaxResult lastPlcyPkMaxResult = privacyPolicyDAO.selectPrvcPlcyPkMax();

                PrivacyPolicyListVO.UpdateParam updateParam = PrivacyPolicyListVO.UpdateParam.builder()
                        .entityId(lastPlcyPkMaxResult.getId())
                        .startDate(beforeDatePlusResult.getStartDate())
                        .build();

                privacyPolicyDAO.updateEndDtPlus(updateParam);
            }

            if(firstPriPlcyNo != prvcPlcyPkListResult.getId()) {
                // 신규 데이터 삭제 후 가장 최근 pk값 가져오기
                PrivacyPolicyListVO.PrvcPlcyPkMaxResult lastPlcyPkMaxResult = privacyPolicyDAO.selectPrvcPlcyPkMax();
                Long firstMaxPk = lastPlcyPkMaxResult.getId();

                // 최근 pk값을 가져와 이전 종료일자 null로 수정 처리
                privacyPolicyDAO.updateBeforeEndDtNull(firstMaxPk);
            }
        }

        if(deletePrivacyChk == 1) {
            result = 1;
        }

        return result;
    }
}
