package kr.ge.kwdi.gep.admin.domain.apiApply.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.admin.domain.apiApply.dto.ApiApplyDTO;
import kr.ge.kwdi.gep.admin.domain.apiApply.service.ApiApplyService;
import kr.ge.kwdi.gep.admin.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Api신청 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/applies", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiApplyController {

    private final ApiApplyService apiApplyService;

    @ApiOperation(value = "Api 신청 목록 조회 API")
    @GetMapping("")
    public ApiResult<List<ApiApplyDTO.ListResponse>> selectApiApplyList(ApiApplyDTO.SearchRequest searchRequest) {
        List<ApiApplyDTO.ListResponse> listResponses = apiApplyService.selectApiApplyList(searchRequest);

        return ApiResult.success(listResponses);
    }

    @ApiOperation(value = "Api 신청 상세 조회 API")
    @GetMapping("/{entityId}")
    public ApiResult<ApiApplyDTO.DetailResponse> selectApiApplyDetail(@PathVariable Long entityId) {
        ApiApplyDTO.DetailResponse detailResponse = apiApplyService.selectApiApplyDetail(entityId);

        return ApiResult.success(detailResponse);
    }

    @ApiOperation(value = "반려 조회 API")
    @GetMapping("/rejects/{entityId}")
    public ApiResult<ApiApplyDTO.RjctResponse> selectRjct(@PathVariable Long entityId) {
        ApiApplyDTO.RjctResponse rjctResponse = apiApplyService.selectRjct(entityId);

        return ApiResult.success(rjctResponse);
    }

    @ApiOperation(value = "Api 신청 수정 API")
    @PutMapping("/{entityId}")
    public ApiResult<Integer> updateApiApplySttsCd(@PathVariable Long entityId, @RequestBody ApiApplyDTO.UpdateRequest updateRequest) {
        int updateApplyResult = apiApplyService.updateApiApplySttsCd(entityId, updateRequest);

        return ApiResult.success(updateApplyResult);
    }

    @ApiOperation(value = "반려 등록 API")
    @PutMapping("/rejects/{entityId}")
    public ApiResult<Integer> updateRjct(@PathVariable Long entityId, @RequestBody ApiApplyDTO.Content content) {
        int updateRjctResult = apiApplyService.updateRjct(entityId, content);

        return ApiResult.success(updateRjctResult);
    }

    @ApiOperation(value = "승인 수정 API")
    @PutMapping("")
    public ApiResult<Integer> updateAprv(@RequestBody ApiApplyDTO.AprvRequest aprvRequest) {
        int updateAprvResult = apiApplyService.updateAprv(aprvRequest);

        return ApiResult.success(updateAprvResult);
    }

    @ApiOperation(value = "Api 신청 삭제 API")
    @DeleteMapping("")
    public ApiResult<Integer> updateApiApplyDelYn(@RequestBody ApiApplyDTO.deleteList deleteList) {
        int deleteApplyResult = apiApplyService.updateApiApplyDelYn(deleteList);

        return ApiResult.success(deleteApplyResult);
    }
}
