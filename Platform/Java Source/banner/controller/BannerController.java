package kr.ge.kwdi.gep.platform.domain.banner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.ge.kwdi.gep.platform.domain.banner.dto.BannerDTO;
import kr.ge.kwdi.gep.platform.domain.banner.service.BannerService;
import kr.ge.kwdi.gep.platform.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "배너 API")
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/banners", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BannerController {

	@Value("${banner.path}")
	private String bannerPath;

	private final BannerService bannerService;

	@ApiOperation(value = "배너 목록 조회 API", notes = "임시")
	@GetMapping("")
	public ApiResult<List<BannerDTO.Response>> getBannerList() {
		List<BannerDTO.Response> resDTO = bannerService.getBannerList();
		return ApiResult.success(resDTO);
	}
}
