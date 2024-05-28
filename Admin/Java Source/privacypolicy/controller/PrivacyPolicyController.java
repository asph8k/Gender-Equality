package kr.ge.kwdi.gep.admin.domain.privacypolicy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.admin.domain.privacypolicy.dto.PrivacyPolicyListDTO;
import kr.ge.kwdi.gep.admin.domain.privacypolicy.service.PrivacyPolicyService;
import kr.ge.kwdi.gep.admin.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "개인정보처리방침 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/privacyPolicies", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrivacyPolicyController {
    private final PrivacyPolicyService privacyPolicyService;

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'privacy-policies', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
    @ApiOperation(value = "개인정보처리방침 목록 조회 API")
    @GetMapping("")
    public ApiResult<List<PrivacyPolicyListDTO.ListResponse>> selectPrvcPlcyList(PrivacyPolicyListDTO.SearchRequest searchRequest) {
        List<PrivacyPolicyListDTO.ListResponse> listResponses = privacyPolicyService.selectPrvcPlcyList(searchRequest);

        return ApiResult.success(listResponses);
    }

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'privacy-policies', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
    @ApiOperation(value = "개인정보처리방침 상세 조회 API")
    @GetMapping("/{entityId}")
    public ApiResult<PrivacyPolicyListDTO.DetailResponse> selectPrvcPlcyDetail(@PathVariable Long entityId) {
        PrivacyPolicyListDTO.DetailResponse detailResponse = privacyPolicyService.selectPrvcPlcyDetail(entityId);

        return ApiResult.success(detailResponse);
    }

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'privacy-policies', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
    @ApiOperation(value = "이전 개인정보처리방침 데이터 조회 API")
    @GetMapping("/Before")
    public ApiResult<PrivacyPolicyListDTO.BeforeDataInfoResponse> selectBeforeDataInfo() {
        PrivacyPolicyListDTO.BeforeDataInfoResponse beforeDataInfoResponse = privacyPolicyService.selectBeforeDataInfo();

        return ApiResult.success(beforeDataInfoResponse);
    }

    @ApiOperation(value = "최신 등록된 개인정보 조회 API")
    @GetMapping("/First")
    public ApiResult<PrivacyPolicyListDTO.FirstInsertPrcyResponse> selectFirstInsertPrcy() {
        PrivacyPolicyListDTO.FirstInsertPrcyResponse firstInsertPrcyResponse = privacyPolicyService.selectFirstInsertPrcy();

        return ApiResult.success(firstInsertPrcyResponse);
    }

    @PreAuthorize("@userAccessAuthorization.authorization(principal, 'privacy-policies', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_CREATE)")
    @ApiOperation(value = "개인정보처리방침 등록 API")
    @PostMapping("")
    public ApiResult<Integer> insertPrvcPlcy(@RequestBody PrivacyPolicyListDTO.InsertRequest insertRequest) {
        int insertPrivacyResult = privacyPolicyService.insertPrvcPlcy(insertRequest);

        return ApiResult.success(insertPrivacyResult);
    }

    @PreAuthorize("@userAccessAuthorization.authorization(principal, 'privacy-policies', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_UPDATE)")
    @ApiOperation(value = "개인정보처리방침 수정 API")
    @PutMapping("/{entityId}")
    public ApiResult<Integer> updatePrvcPlcy(@PathVariable Long entityId, @RequestBody PrivacyPolicyListDTO.UpdateRequest updateRequest) {
        int updatePrivacyResult = privacyPolicyService.updatePrvcPlcy(entityId, updateRequest);

        return ApiResult.success(updatePrivacyResult);
    }

    @PreAuthorize("@userAccessAuthorization.authorization(principal, 'privacy-policies', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_DELETE)")
    @ApiOperation(value = "개인정보처리방침 삭제 API")
    @DeleteMapping("")
    public ApiResult<Integer> deletePrvcPlcy(@RequestBody PrivacyPolicyListDTO.deleteList deleteList) {
        int deletePrivacyResult = privacyPolicyService.deletePrvcPlcy(deleteList);

        return ApiResult.success(deletePrivacyResult);
    }
}
