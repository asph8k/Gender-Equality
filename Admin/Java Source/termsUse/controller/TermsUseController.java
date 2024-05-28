package kr.ge.kwdi.gep.admin.domain.termsUse.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.admin.domain.termsUse.dto.TermsUseListDTO;
import kr.ge.kwdi.gep.admin.domain.termsUse.service.TermsUseService;
import kr.ge.kwdi.gep.admin.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "이용약관 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/termsUses", produces = MediaType.APPLICATION_JSON_VALUE)
public class TermsUseController {
    private final TermsUseService termsUseService;

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'terms', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
    @ApiOperation(value = "이용약관 목록 조회 API")
    @GetMapping("")
    public ApiResult<List<TermsUseListDTO.ListResponse>> selectTermsUseList(TermsUseListDTO.SearchRequest searchRequest) {
        List<TermsUseListDTO.ListResponse> listResponses = termsUseService.selectTermsUseList(searchRequest);

        return ApiResult.success(listResponses);
    }

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'terms', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
    @ApiOperation(value = "이용약관 상세 조회 API")
    @GetMapping("/{entityId}")
    public ApiResult<TermsUseListDTO.DetailResponse> selectTermsUseDetail(@PathVariable Long entityId) {
        TermsUseListDTO.DetailResponse detailResponse = termsUseService.selectTermsUseDetail(entityId);

        return ApiResult.success(detailResponse);
    }

    @ApiOperation(value = "이전 개인정보처리방침 데이터 조회 API")
    @GetMapping("/Before")
    public ApiResult<TermsUseListDTO.BeforeDataInfoResponse> selectBeforeDataInfo() {
        TermsUseListDTO.BeforeDataInfoResponse beforeDataInfoResponse = termsUseService.selectBeforeDataInfo();

        return ApiResult.success(beforeDataInfoResponse);
    }

    @ApiOperation(value = "최신 등록된 이용약관 조회 API")
    @GetMapping("/First")
    public ApiResult<TermsUseListDTO.FirstInsertTermsResponse> selectFirstInsertTerms() {
        TermsUseListDTO.FirstInsertTermsResponse firstInsertTermsResponse = termsUseService.selectFirstInsertTerms();

        return ApiResult.success(firstInsertTermsResponse);
    }

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'terms', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_CREATE)")
    @ApiOperation(value = "이용약관 등록 API")
    @PostMapping("")
    public ApiResult<Integer> insertTermsUse(@RequestBody TermsUseListDTO.InsertRequest insertRequest) {
        int insertTrmsResult = termsUseService.insertTermsUse(insertRequest);

        return ApiResult.success(insertTrmsResult);
    }

    @PreAuthorize("@userAccessAuthorization.authorization(principal, 'terms', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_UPDATE)")
    @ApiOperation(value = "이용약관 수정 API")
    @PutMapping("/{entityId}")
    public ApiResult<Integer> updateTermsUse(@PathVariable Long entityId, @RequestBody TermsUseListDTO.UpdateRequest updateRequest) {
        int updateTrmsResult = termsUseService.updateTermsUse(entityId, updateRequest);

        return ApiResult.success(updateTrmsResult);
    }

    @PreAuthorize("@userAccessAuthorization.authorization(principal, 'terms', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_DELETE)")
    @ApiOperation(value = "이용약관 삭제 API")
    @DeleteMapping("")
    public ApiResult<Integer> deleteTermsUse(@RequestBody TermsUseListDTO.deleteList deleteList) {
        int deleteTrmsResult = termsUseService.deleteTermsUse(deleteList);

        return ApiResult.success(deleteTrmsResult);
    }
}
