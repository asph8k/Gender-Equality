package kr.ge.kwdi.gep.platform.domain.banner.service;

import egovframework.rte.fdl.property.impl.EgovPropertyServiceImpl;
import kr.ge.kwdi.gep.platform.domain.banner.dao.BannerDAO;
import kr.ge.kwdi.gep.platform.domain.banner.dto.BannerDTO;
import kr.ge.kwdi.gep.platform.domain.banner.dto.BannerVO;
import kr.ge.kwdi.gep.platform.global.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerService extends EgovPropertyServiceImpl {

	private final ModelMapper modelMapper;
	private final BannerDAO bannerDAO;

	// 메인 배너 목록 조회
	/*public List<BannerDTO.Response> getBannerList() {
		BannerVO.param param = new BannerVO.param();

		List<BannerVO.result> result = bannerDAO.getBannerList(param);
		List<BannerDTO.Response> resDTO = modelMapper.map(result, new TypeToken<List<BannerDTO.Response>>() {}.getType());

		return resDTO;
	}*/
	public List<BannerDTO.Response> getBannerList() {
		List<BannerVO.result> results = bannerDAO.getBannerList();

		List<BannerDTO.Response> responses = results.stream()
				.map(result -> BannerDTO.Response.builder()
						.bnnrNm(result.getName())
						.bnnrCn(result.getContent())
						.bnntUrlAddr(TextUtil.interfaceCodeByImageUrl(result.getUrl()))
						.bnnrLink(result.getLink())
						.build())
				.collect(Collectors.toList());

		return responses;
	}
}
