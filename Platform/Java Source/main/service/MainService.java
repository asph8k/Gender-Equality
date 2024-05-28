package kr.ge.kwdi.gep.platform.domain.main.service;

import com.google.common.reflect.TypeToken;
import kr.ge.kwdi.gep.platform.domain.main.dao.MainDAO;
import kr.ge.kwdi.gep.platform.domain.main.dto.*;
import kr.ge.kwdi.gep.platform.global.common.CommonCodeManager;
import kr.ge.kwdi.gep.platform.global.common.YoutubeThumbnailModule;
import kr.ge.kwdi.gep.platform.global.constant.ConstantValue;
import kr.ge.kwdi.gep.platform.global.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {
	private final MainDAO mainDAO;
	private final ModelMapper modelMapper;
	private final CommonCodeManager commonCodeManager;


	public List<MultimediaDTO.Response> getMuiltimediaData() {
		List<MultimediaVO.Result> multimediaResVO = mainDAO.selectCardImage();
		List<MultimediaVO.Result> multimediaResVO2 = mainDAO.selectPhotoImage();
		List<MultimediaVO.Result> multimediaResVO3 = mainDAO.selectWebtoonImage();

		multimediaResVO.addAll(multimediaResVO2);
		multimediaResVO.addAll(multimediaResVO3);
 		List<MultimediaDTO.Response> multimediaResDTO = modelMapper.map(multimediaResVO, new TypeToken<List<MultimediaDTO.Response>>() {}.getType());

		return multimediaResDTO;
	}

	public List<MultimediaDTO.Response> getCardImage() {
		List<MultimediaVO.Result> multimediaDTOS = mainDAO.selectCardImage();
		multimediaDTOS.stream().forEach(result -> result.setImgUrlAddr("/gep/api/v1/file/image/" + (result.getImgUrlAddr().split(",")[0])));

		List<MultimediaDTO.Response> multimediaResDTO = modelMapper.map(multimediaDTOS, new TypeToken<List<MultimediaDTO.Response>>() {}.getType());

		return multimediaResDTO;
	}

	public List<MultimediaDTO.Response> getPhotoImage() {
		List<MultimediaVO.Result> multimediaDTOS = mainDAO.selectPhotoImage();
		multimediaDTOS.stream().forEach(result -> result.setImgUrlAddr("/gep/api/v1/file/image/" + result.getImgUrlAddr().split(",")[0]));
		List<MultimediaDTO.Response> multimediaResDTO = modelMapper.map(multimediaDTOS, new TypeToken<List<MultimediaDTO.Response>>() {}.getType());

		return multimediaResDTO;
	}

	public List<MainVdoDTO.Response> getMainVdoData() {
		YoutubeThumbnailModule youtubeThumbnailModule = new YoutubeThumbnailModule();
		List<MainVdoVO.Result> vdoResVO = mainDAO.selectMainVdo();

		// vdoResVO.forEach(result -> result.setVdoUrlAddr("http://img.youtube.com/vi/" + extractYoutubeId(result.getVdoUrlAddr()) + "/mqdefault.jpg"));
		vdoResVO.forEach(result -> result.setImgUrlAddr(youtubeThumbnailModule.extractYoutubeThumbnail(result.getImgUrlAddr())));

		List<MainVdoDTO.Response> vdoResDTO = modelMapper.map(vdoResVO, new TypeToken<List<MainVdoDTO.Response>>() {}.getType());
		return vdoResDTO;
	}

	public List<BySubjectDTO.Response> getDataBySubject(BySubjectDTO.Request bySubjectReqDTO) {
		BySubjectVO.Param bySubjectParamVO = modelMapper.map(bySubjectReqDTO, BySubjectVO.Param.class);

		List<BySubjectVO.Result> results = mainDAO.selectDataBySubject(bySubjectParamVO);

		List<BySubjectDTO.Response> bySubjectResDTO = modelMapper.map(results, new TypeToken<List<BySubjectDTO.Response>>() {}.getType());
		for (BySubjectDTO.Response response : bySubjectResDTO) {
			response.setUrl(
				TextUtil.dataTypeCodeByDetailUrl(response.getDataTypeCd(), response.getGndrEqltDataNo().longValue(),
					response.getOrgnAddr()));
			response.setDataTypeCd(TextUtil.convertDataTypeCodeToName(response.getDataTypeCd()));
		}
		return bySubjectResDTO;
	}

	public List<MainNewsDTO.Response> getMainNewsData() {
		List<MainNewsVO.Result> mainNewsResVO = mainDAO.selectMainNews();
		List<MainNewsDTO.Response> mainNewsResDTO = modelMapper.map(mainNewsResVO, new TypeToken<List<MainNewsDTO.Response>>() {
		}.getType());

		return mainNewsResDTO;
	}

	public List<MainNewsConverageDTO.Response> getMainNewsConverage() {
		List<MainNewsConverageVO.Result> mainNewsConverageResVO = mainDAO.selectMainNewsConverage();

		List<MainNewsConverageDTO.Response> mainNewsConverageResDTO = modelMapper.map(mainNewsConverageResVO, new TypeToken<List<MainNewsConverageDTO.Response>>() {
		}.getType());

		return mainNewsConverageResDTO;
	}

	public List<BySubjectDTO.SubjectsResponse> getSubjects() {
		Map<String, String> commonCodeMap = commonCodeManager.getCodeMap("DCS");
		return bySubjectDTOSubjectsResponseBuilder(commonCodeMap);
	}

	public static List<BySubjectDTO.SubjectsResponse> bySubjectDTOSubjectsResponseBuilder(Map<String, String> commonCodeMap) {
		return commonCodeMap.entrySet().stream()
			.map(entry -> BySubjectDTO.SubjectsResponse.builder()
				.code(entry.getKey())
				.subject(entry.getValue())
				.build())
				.filter(subjectsResponse -> subjectsResponse.getCode().length() == 6).collect(
				Collectors.toList());
	}

	// 공지사항 목록 조회
	public List<MainNoticeDTO.Response> selectMainNotice() {
		// 조회
		List<MainNoticeVO.Result> results = mainDAO.selectMainNotice();

		// 변환
		List<MainNoticeDTO.Response> responses = results.stream()
				.map(result -> MainNoticeDTO.Response.builder()
						.title(result.getTitle())
						.created(result.getCreated())
						.url(ConstantValue.MAIN_NOTICE_DETAIL_URL + result.getId())
						.build())
				.collect(Collectors.toList());

		return responses;
	}
}