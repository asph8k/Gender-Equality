package kr.ge.kwdi.gep.admin.domain.relatedInst.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.admin.domain.relatedInst.dto.RelatedInstDTO;
import kr.ge.kwdi.gep.admin.domain.relatedInst.service.RelatedInstService;
import kr.ge.kwdi.gep.admin.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = "유관기관관리 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/relatedinsts", produces = MediaType.APPLICATION_JSON_VALUE)
public class RelatedInstController {

    private final RelatedInstService relatedInstService;

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'relatedinstses', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
    @ApiOperation(value = "유관기관 목록 조회 API")
    @GetMapping("")
    public ApiResult<List<RelatedInstDTO.RltdInstListResponse>> selectRltdInstList(RelatedInstDTO.SearchRequest searchRequest) {
        List<RelatedInstDTO.RltdInstListResponse> rltdInstListResponses = relatedInstService.selectRltdInstList(searchRequest);

        return ApiResult.success(rltdInstListResponses);
    }

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'relatedinstses', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
    @ApiOperation(value = "유관기관 상세 조회 API")
    @GetMapping("/{entityId}")
    public ApiResult<RelatedInstDTO.RltdInstDetailResponse> selectRltdInstDtl(@PathVariable Long entityId) {
        RelatedInstDTO.RltdInstDetailResponse rltdInstDetailResponse = relatedInstService.selectRltdInstDtl(entityId);

        return ApiResult.success(rltdInstDetailResponse);
    }

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'relatedinstses', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_CREATE)")
    @ApiOperation(value = "유관기관 등록 API")
    @PostMapping("")
    public ApiResult<Integer> insertRltdInst(RelatedInstDTO.RltdInstInsertRequest rltdInstInsertRequest,
                                             @RequestPart(value = "newFiles", required = false) MultipartFile newFiles) throws IOException {
        int insertRltdInstResult = relatedInstService.insertRltdInst(rltdInstInsertRequest, newFiles);

        return ApiResult.success(insertRltdInstResult);
    }

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'relatedinstses', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_UPDATE)")
    @ApiOperation(value = "유관기관 수정 API")
    @PutMapping("/{entityId}")
    public ApiResult<Integer> updateRltdInst(@PathVariable Long entityId, RelatedInstDTO.RltdInstUpdateRequest rltdInstUpdateRequest,
                                             @RequestPart(value = "newFiles", required = false) MultipartFile newFiles) throws IOException {
        int updateRltdInstResult = relatedInstService.updateRltdInst(entityId, rltdInstUpdateRequest, newFiles);

        return ApiResult.success(updateRltdInstResult);
    }

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'relatedinstses', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_DELETE)")
    @ApiOperation(value = "유관기관 삭제 API")
    @DeleteMapping("")
    public ApiResult<Integer> deleteRltdInst(@RequestBody RelatedInstDTO.deleteList deleteList) {
        int deleteRltdInstResult = relatedInstService.deleteRltdInst(deleteList);

        return ApiResult.success(deleteRltdInstResult);
    }
}
