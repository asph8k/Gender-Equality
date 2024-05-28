package kr.ge.kwdi.gep.admin.domain.banner.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.admin.domain.banner.dto.BannerDTO;
import kr.ge.kwdi.gep.admin.domain.banner.service.BannerService;
import kr.ge.kwdi.gep.admin.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = "배너 관리 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/banners", produces = MediaType.APPLICATION_JSON_VALUE)
public class BannerController {

    private final BannerService bannerService;

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'banners', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
    @ApiOperation(value = "배너 관리 목록 조회 API")
    @GetMapping("")
    public ApiResult<List<BannerDTO.BannerListResponse>> selectBannerList(BannerDTO.SearchRequest searchRequest) {
        List<BannerDTO.BannerListResponse> bannerListResponses = bannerService.selectBannerList(searchRequest);

        return ApiResult.success(bannerListResponses);
    }

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'banners', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_READ)")
    @ApiOperation(value = "배너 관리 상세 조회 API")
    @GetMapping("/{entityId}")
    public ApiResult<BannerDTO.BannerDetailResponse> selectBannerDetail(@PathVariable Long entityId) {
        BannerDTO.BannerDetailResponse bannerDetailResponse = bannerService.selectBannerDetail(entityId);

        return ApiResult.success(bannerDetailResponse);
    }

    @ApiOperation(value = "배너 위치 공통코드 조회 API")
    @GetMapping("/position")
    public ApiResult<List<BannerDTO.BannerPositionCodeResponse>> selectBannerPositionCode() {
        List<BannerDTO.BannerPositionCodeResponse> bannerPositionCodeResponses = bannerService.selectBannerPositionCode();

        return ApiResult.success(bannerPositionCodeResponses);
    }

    @ApiOperation(value = "배너 타겟 공통코드 조회 API")
    @GetMapping("/target")
    private ApiResult<List<BannerDTO.BannerTargetCodeResponse>> selectBannerTargetCode() {
        List<BannerDTO.BannerTargetCodeResponse> bannerTargetCodeResponses = bannerService.selectBannerTargetCode();

        return ApiResult.success(bannerTargetCodeResponses);
    }

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'banners', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_CREATE)")
    @ApiOperation(value = "배너 관리 등록 API")
    @PostMapping("")
    public ApiResult<Integer> insertBannerAndImg(BannerDTO.InsertRequest insertRequest,
                                                 @RequestPart(value = "newFile", required = false) MultipartFile newFile) throws IOException {
        int insertBannerResult = bannerService.insertBannerAndImg(insertRequest, newFile);

        return ApiResult.success(insertBannerResult);
    }

    //@PreAuthorize("@userAccessAuthorization.authorization(principal, 'banners', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_UPDATE)")
    @ApiOperation(value = "배너 관리 수정 API")
    @PutMapping("/{entityId}")
    public ApiResult<Integer> updateBannerAndImg(@PathVariable Long entityId, BannerDTO.UpdateRequest updateRequest,
                                                 @RequestPart(value = "newFile", required = false) MultipartFile newFile) throws IOException {
        int updateBannerResult = bannerService.updateBannerAndImg(entityId, updateRequest, newFile);

        return ApiResult.success(updateBannerResult);
    }

    @PreAuthorize("@userAccessAuthorization.authorization(principal, 'banners', T(kr.ge.kwdi.gep.admin.global.constant.ConstantValue).ACTIVE_DELETE)")
    @ApiOperation(value = "배너 관리 삭제 API")
    @DeleteMapping("")
    public ApiResult<Integer> updateBannerDelYn(@RequestBody BannerDTO.deleteList deleteList) {
        int deleteBannerResult = bannerService.updateBannerDelYn(deleteList);

        return ApiResult.success(deleteBannerResult);
    }
}
