package kr.ge.kwdi.gep.admin.domain.termsUse.service;

import kr.ge.kwdi.gep.admin.domain.account.dto.AccountVO;
import kr.ge.kwdi.gep.admin.domain.termsUse.dao.TermsUseDAO;
import kr.ge.kwdi.gep.admin.domain.termsUse.dto.TermsUseListDTO;
import kr.ge.kwdi.gep.admin.domain.termsUse.dto.TermsUseListVO;
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
public class TermsUseService {
    private final TermsUseDAO termsUseDAO;

    /** 이용약관 목록 조회 */
    public List<TermsUseListDTO.ListResponse> selectTermsUseList(TermsUseListDTO.SearchRequest searchRequest) {
        TermsUseListVO.SearchParam searchParam = TermsUseListVO.SearchParam.builder()
                .startDate(TextUtil.formatDateForm(searchRequest.getStartDate()))
                .endDate(TextUtil.formatDateForm(searchRequest.getEndDate()))
                .build();
        // 조회
        List<TermsUseListVO.ListResult> listResults = termsUseDAO.selectTermsUseList(searchParam);
        // 변환
        List<TermsUseListDTO.ListResponse> listResponses = listResults.stream()
                .map(result -> TermsUseListDTO.ListResponse.builder()
                        .id(result.getId())
                        .startDate(result.getStartDate())
                        .endDate(result.getEndDate())
                        .created(result.getCreated())
                        .build())
                .collect(Collectors.toList());

        return listResponses;
    }

    /** 이용약관 상세 조회 */
    public TermsUseListDTO.DetailResponse selectTermsUseDetail(Long entityId) {
        // 조회
        TermsUseListVO.DetailResult detailResult = termsUseDAO.selectTermsUseDetail(entityId);

        // 변환
        TermsUseListDTO.DetailResponse detailResponse = TermsUseListDTO.DetailResponse.builder()
                .id(detailResult.getId())
                .content(detailResult.getContent())
                .startDate(detailResult.getStartDate())
                .endDate(detailResult.getEndDate())
                .created(detailResult.getCreated())
                .build();

        return detailResponse;
    }

    /** 이전 개인정보처리방침 데이터 조회 */
    public TermsUseListDTO.BeforeDataInfoResponse selectBeforeDataInfo() {
        // 조회
        TermsUseListVO.BeforeDataInfoResult beforeDataInfoResult = termsUseDAO.selectBeforeDataInfo();

        // 변환
        TermsUseListDTO.BeforeDataInfoResponse beforeDataInfoResponse = TermsUseListDTO.BeforeDataInfoResponse.builder()
                .id(beforeDataInfoResult.getId())
                .startDate(beforeDataInfoResult.getStartDate())
                .endDate(beforeDataInfoResult.getEndDate())
                .content(beforeDataInfoResult.getContent())
                .build();

        return beforeDataInfoResponse;
    }

    /** 최신 등록된 이용약관 조회 */
    public TermsUseListDTO.FirstInsertTermsResponse selectFirstInsertTerms() {
        // 조회
        TermsUseListVO.FirstInsertTermsResult firstInsertTermsResult = termsUseDAO.selectFirstInsertTerms();

        // 변환
        TermsUseListDTO.FirstInsertTermsResponse firstInsertTermsResponse = TermsUseListDTO.FirstInsertTermsResponse.builder()
                .id(firstInsertTermsResult.getId())
                .content(firstInsertTermsResult.getContent())
                .build();

        return firstInsertTermsResponse;
    }

    /** 이용약관 등록 */
    @Transactional
    public int insertTermsUse(TermsUseListDTO.InsertRequest insertRequest) {
        // 반환값 설정
        int result = 0;

        // 로그인 중인 사용자 정보
        AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 데이터가 0건인지 아닌지 확인을 위해 전체 조회
        List<TermsUseListVO.AllResult> allResult = termsUseDAO.selectAll();

        // 입력된 내용에서 HTML 태그 및 엔티티 문자를 제거 해주기 위한 로직 처리
        String delHtmlTag = StringEscapeUtils.unescapeHtml(insertRequest.getContent());
        delHtmlTag = delHtmlTag.replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");

        // 1건이라도 있다면 작동
        if(!allResult.isEmpty()) {
            // 신규 등록 시 이전 데이터 종료일자 null로 수정을 위해 최근 pk값 가져오기
            TermsUseListVO.MngNoResult mngNoResult = termsUseDAO.selectUtztnMngNo();
            Long pkMaxResult = mngNoResult.getId();

            // 최근 이용약관 시행일자 조회
            TermsUseListVO.FirstBeginDtResult firstBeginDtResult = termsUseDAO.selectFirstBeginDt(mngNoResult.getId());

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

            TermsUseListVO.InsertParam insertParam = TermsUseListVO.InsertParam.builder()
                    .content(delHtmlTag)
                    .frstJobObj(accountVO.getJobObject())
                    .startDate(insertRequest.getStartDate())
                    .build();

            int termUseInsertCheck = termsUseDAO.insertTermsUse(insertParam);

            // 신규 등록 된 시행일자에서 하루 뺀 날짜값 조회 후 이전 종료일자에 수정
            TermsUseListVO.BeforeDateResult beforeDateResult = termsUseDAO.selectBeforeDate(insertParam.getEntityId());

            TermsUseListVO.BeforeDateParam beforeDateParam = TermsUseListVO.BeforeDateParam.builder()
                    .entityId(pkMaxResult)
                    .endDate(beforeDateResult.getEndDate())
                    .build();

            int updateBeforeDtChk = termsUseDAO.updateBeforeDate(beforeDateParam);

            if(termUseInsertCheck == 1 && updateBeforeDtChk == 1) {
                result = 1;
            }
        } else {
            TermsUseListVO.InsertParam insertParam = TermsUseListVO.InsertParam.builder()
                    .content(delHtmlTag)
                    .frstJobObj(accountVO.getJobObject())
                    .startDate(insertRequest.getStartDate())
                    .build();

            int termUseInsertCheck = termsUseDAO.insertTermsUse(insertParam);

            // 신규 등록 시 종료일자를 null로 업데이트
            TermsUseListVO.BeforeDateParam beforeDateParam = TermsUseListVO.BeforeDateParam.builder()
                    .endDate(null)
                    .build();

            int updateBeforeDtChk = termsUseDAO.updateBeforeDate(beforeDateParam);

            if(termUseInsertCheck == 1 && updateBeforeDtChk == 1) {
                result = 1;
            }
        }

        return result;
    }

    /** 이용약관 수정 */
    @Transactional
    public int updateTermsUse(Long entityId, TermsUseListDTO.UpdateRequest updateRequest) {
        // 반환값 설정
        int result = 0;

        // 로그인 중인 사용자 정보
        AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 입력된 내용에서 HTML 태그 및 엔티티 문자를 제거 해주기 위한 로직 처리
        String delHtmlTag = StringEscapeUtils.unescapeHtml(updateRequest.getContent());
        delHtmlTag = delHtmlTag.replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");

        // 최근 등록 된 이용약관의 시행일자를 수정 할 때 이전에 등록 된 시행일자보다 낮은지 검증하기 위한 이전 시행일자 조회
        TermsUseListVO.PrvsStartDateResult prvsStartDateResult = termsUseDAO.selectPrvsStartDate();

        // 이전 데이터가 없고 최초로 등록 되어 있는 데이터를 수정 했을 때 작동 되는 조건식
        if(ObjectUtils.isEmpty(prvsStartDateResult)) {
            TermsUseListVO.UpdateParam updateParam = TermsUseListVO.UpdateParam.builder()
                    .entityId(entityId)
                    .startDate(updateRequest.getStartDate())
                    .content(delHtmlTag)
                    .lastJobObj(accountVO.getJobObject())
                    .build();

            termsUseDAO.updateTermsUse(updateParam);
        } else {
            // 이전 시행일자
            int prvcStartDt = Integer.parseInt(prvsStartDateResult.getStartDate());
            // 입력받은 시행일자
            int updateStartDt = Integer.parseInt(updateRequest.getStartDate());

            // 검증 로직
            if(prvcStartDt == updateStartDt) {
                throw new GlobalException(ErrorCode.SAME_BEGINDATE, "same begindate");
            }

            if(prvcStartDt > updateStartDt) {
                throw new GlobalException(ErrorCode.NOT_UPDATE_BEGINDATE, "too much begindate");
            }

            TermsUseListVO.UpdateParam updateParam = TermsUseListVO.UpdateParam.builder()
                    .entityId(entityId)
                    .startDate(updateRequest.getStartDate())
                    .content(delHtmlTag)
                    .lastJobObj(accountVO.getJobObject())
                    .build();

            int updateTermCheck = termsUseDAO.updateTermsUse(updateParam);

            // 이용약관 List에서 두번째 PK 값 조회
            TermsUseListVO.MinusPKResult minusPKResult = termsUseDAO.selectMinusPK();

            // 수정 된 시행일자에서 하루 뺀 날짜값 조회 후 이전 종료일자에 수정
            TermsUseListVO.BeforeDateResult beforeDateResult = termsUseDAO.selectBeforeDate(entityId);

            TermsUseListVO.BeforeDateParam beforeDateParam = TermsUseListVO.BeforeDateParam.builder()
                    .entityId(minusPKResult.getId())
                    .endDate(beforeDateResult.getEndDate())
                    .build();

            int updateBeforeDtChk = termsUseDAO.updateBeforeDate(beforeDateParam);

            if(updateTermCheck == 1 && updateBeforeDtChk == 1) {
                result = 1;
            }
        }

        return result;
    }

    /** 이용약관 삭제 */
    @Transactional
    public int deleteTermsUse(TermsUseListDTO.deleteList deleteList) {
        // 반환값 설정
        int result = 0;

        // 가장 최근 개인정보처리방침 pk값 가져오기
        TermsUseListVO.MngNoResult mngNoResult = termsUseDAO.selectUtztnMngNo();
        Long firstTermsNo = mngNoResult.getId();

        TermsUseListVO.DeleteParam deleteParam = TermsUseListVO.DeleteParam.builder()
                .list(deleteList.getDeleteList())
                .build();

        int delTermsUseChk = termsUseDAO.deleteTermsUse(deleteParam);

        // 신규 이용약관 삭제 후 목록에서 pk값 조회
        List<TermsUseListVO.TermsPkListResult> termsPkListResults = termsUseDAO.selectTermsPkList();

        for(TermsUseListVO.TermsPkListResult termsPkListResult : termsPkListResults) {
            if(ObjectUtils.isEmpty(termsPkListResult.getEndDate())) {
                // 만약 최신 데이터가 삭제 되지 않고 중간에 데이터가 삭제 됬을 시 최신 시행일자는 이전 종료일자 + 1 해주기 위한 최신 데이터 이전 Max값 조회
                TermsUseListVO.NewBeforeDataResult newBeforeDataResult = termsUseDAO.selectNewBeforeData();

                // 종료일자 하루 더하기
                TermsUseListVO.BeforeDatePlusResult beforeDatePlusResult = termsUseDAO.selectPlusBeforeEndDt(newBeforeDataResult.getId());

                // 신규 데이터 삭제 후 가장 최근 pk값 가져오기
                TermsUseListVO.MngNoResult lastTermsMaxResult = termsUseDAO.selectUtztnMngNo();

                TermsUseListVO.UpdateParam updateParam = TermsUseListVO.UpdateParam.builder()
                        .entityId(lastTermsMaxResult.getId())
                        .startDate(beforeDatePlusResult.getStartDate())
                        .build();

                termsUseDAO.updateEndDtPlus(updateParam);
            }

            if(firstTermsNo != termsPkListResult.getId()) {
                // 신규 데이터 삭제 후 가장 최근 pk값 가져오기
                TermsUseListVO.MngNoResult lastTermsMaxResult = termsUseDAO.selectUtztnMngNo();
                Long firstMaxPk = lastTermsMaxResult.getId();

                // 최근 pk값을 가져와 이전 종료일자 null로 수정 처리
                termsUseDAO.updateBeforeEndDtNull(firstMaxPk);
            }
        }

        if(delTermsUseChk == 1) {
            result = 1;
        }

        return result;
    }
}
