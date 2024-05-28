package kr.ge.kwdi.gep.admin.domain.notice.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import kr.ge.kwdi.gep.admin.domain.account.dto.AccountVO;
import kr.ge.kwdi.gep.admin.global.common.CommonCodeManager;
import kr.ge.kwdi.gep.admin.global.util.TextUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.ge.kwdi.gep.admin.domain.notice.dao.NoticeListDAO;
import kr.ge.kwdi.gep.admin.domain.notice.dto.NoticeListDTO;
import kr.ge.kwdi.gep.admin.domain.notice.dto.NoticeListVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeListService {

	private final NoticeListDAO noticeListDAO;
	private final CommonCodeManager commonCodeManager;

	@Value("${file.path}")
	private String filePath;

	/** 공지사항 목록 조회 */
	public List<NoticeListDTO.ListResponse> getNoticeList(NoticeListDTO.SearchRequest searchRequest) {
		// 로그인 중인 사용자 ID
		AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		NoticeListVO.SearchParam searchParam = NoticeListVO.SearchParam.builder()
				.q(searchRequest.getQ())
				.searchType(searchRequest.getSearchType())
				.build();
		// 조회
		List<NoticeListVO.ListResult> listResults = noticeListDAO.selectNoticeList(searchParam);
		// 변환
		List<NoticeListDTO.ListResponse> listResponses = listResults.stream()
				.map(result -> NoticeListDTO.ListResponse.builder()
						.id(result.getId())
						.title(result.getTitle())
						.createdAt(result.getCreatedAt())
						.visible(TextUtil.convertStringToBoolean(result.getVisible()))
						.frstJobObj(accountVO.getJobObject())
						.build())
				.collect(Collectors.toList());

		return listResponses;
	}

	/** 공지사항 상세 조회 */
	public NoticeListDTO.DetailResponse getNoticeDetail(Long entityId){
		// 조회
		NoticeListVO.DetailResult detailResult = noticeListDAO.selectNoticeDetail(entityId);

		// 변환
		NoticeListDTO.DetailResponse detailResponse = NoticeListDTO.DetailResponse.builder()
				.id(detailResult.getId())
				.title(detailResult.getTitle())
				.visible(TextUtil.convertStringToBoolean(detailResult.getVisible()))
				.content(detailResult.getContent())
				.createdAt(detailResult.getCreatedAt())
				.files(getNoticeFlies(entityId))
				.build();

		return detailResponse;
	}

	/** 공지사항 리스트 생성 */
	@Transactional
	public int saveNoticeList(NoticeListDTO.InsertRequest insertRequest, List<MultipartFile> newFiles) throws IOException {
		// 반환값 설정
		int result = 0;
		int fileUploadCheck = 0;

		// 로그인 중인 사용자 ID
		AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 입력된 내용에서 HTML 태그 및 엔티티 문자를 제거 해주기 위한 로직 처리
		String delHtmlTag = StringEscapeUtils.unescapeHtml(insertRequest.getContent());
		delHtmlTag = delHtmlTag.replaceAll("<(/)?([a-zA-Z]*)([0-9]?)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");

		NoticeListVO.InsertParam insertParam = NoticeListVO.InsertParam.builder()
				.title(insertRequest.getTitle())
				.visible(TextUtil.convertBooleanToString(insertRequest.getVisible()))
				.content(delHtmlTag)
				.frstJobObj(accountVO.getJobObject())
				.existFile(checkExistFile(newFiles) ? "Y" : "N")
				.fileCount(countFiles(newFiles))
				.build();

		int insertCheck = noticeListDAO.insertNoticeList(insertParam);

		//공지사항 번호 가져와 첨부파일에 insert
		int noticeInsertCheck = uploadFile(newFiles, "NOTICE", insertParam.getEntityId());

		if(checkExistFile(newFiles)) {
			if(insertCheck == 1 && noticeInsertCheck == 1 && fileUploadCheck == 1) {
				uploadFile(newFiles, "NOTICE", insertParam.getEntityId());
				result = 1;
			}
		} else {
			if(insertCheck == 1 && noticeInsertCheck == 1) {
				result = 1;
			}
		}

		return result;
	}

	/** 공지사항 리스트 수정 */
	@Transactional
	public int updateNoticeList(Long entityId, NoticeListDTO.UpdateRequest updateRequest, @RequestPart(value = "newFiles", required = false) List<MultipartFile> newFiles) throws IOException {
		// 반환값 설정
		int result = 0;
		int updateFiles = 0;

		// 로그인 중인 사용자 정보
		AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 입력된 내용에서 HTML 태그 및 엔티티 문자를 제거 해주기 위한 로직 처리
		String delHtmlTag = StringEscapeUtils.unescapeHtml(updateRequest.getContent());
		delHtmlTag = delHtmlTag.replaceAll("<(/)?([a-zA-Z]*)([0-9]?)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", "");

		NoticeListVO.UpdateParam updateParam = NoticeListVO.UpdateParam.builder()
				.entityId(entityId)
				.title(updateRequest.getTitle())
				.visible(TextUtil.convertBooleanToString(updateRequest.getVisible()))
				.content(delHtmlTag)
				.lastJobObj(accountVO.getJobObject())
				.build();

		if(!ObjectUtils.isEmpty(newFiles) || !ObjectUtils.isEmpty(updateRequest.getDeleteFiles())) {
			updateFiles = updateFile(newFiles, updateRequest.getDeleteFiles(), "NOTICE", entityId);
		}

		int updateNoticeCheck = noticeListDAO.updateNoticeList(updateParam);

		if(updateNoticeCheck == 1 && updateFiles == 1) {
			result = 1;
		}


		return result;
	}

	/** 공지사항 리스트 삭제 */
	@Transactional
	public int deleteNoticeList(NoticeListDTO.deleteList deleteList) {
		// 반환값 설정
		int result = 0;

		NoticeListVO.DeleteParam deleteParam = NoticeListVO.DeleteParam.builder()
				.list(deleteList.getDeleteList())
				.build();

		result = noticeListDAO.deleteNoticeList(deleteParam);

		return result;
	}

	// 첨부파일 조회
	public List<NoticeListDTO.NoticeFileResponse> getNoticeFlies(Long entityId) {
		List<NoticeListVO.FileResult> fileResults = noticeListDAO.selectNoticeFlies(entityId);
		return fileResults.stream()
				.map(result -> NoticeListDTO.NoticeFileResponse.builder()
						.id(result.getId())
						.name(result.getOriginalName()
								+ "."
								+ commonCodeManager.convertToString(result.getCode()))
						.uuid(result.getStorageName())
						.size(result.getFileSize())
						.order(result.getOrdr())
						.build()).collect(Collectors.toList());
	}

	// 파일 저장 및 DB Insert
	public int uploadFile(List<MultipartFile> files, String dataTypeCode, Long entityId) throws IOException {
		int result = 0;
		for (MultipartFile file : files) {
			LocalDate localDate = LocalDate.now();

			String date = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

			String uuid = UUID.randomUUID().toString();
			String dataTypeCodePath = detectFileDir(dataTypeCode);
			String uuidFileNamePath = dataTypeCodePath + uuid + "_" + date;
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1).toLowerCase();
			String extensionCode = commonCodeManager.convertToExtensionCode(extension);

			String savePath = filePath + dataTypeCodePath;

			String originalFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.'));

			NoticeListVO.FileInsertParam fileInsertParam = new NoticeListVO.FileInsertParam();
			fileInsertParam.setEntityId(entityId);
			fileInsertParam.setFileOrgnlNm(originalFileName);
			fileInsertParam.setFileActlStrgNm(uuidFileNamePath);
			fileInsertParam.setFileClsfCd(extensionCode);
			fileInsertParam.setFileSize(file.getSize());

			isDirectoryExists(savePath);

			file.transferTo(new File(filePath + uuidFileNamePath));

			result = noticeListDAO.insertNoticeFile(fileInsertParam);
			log.info("INSERT FILE CHECK === {}", result);
		}
		return result;
	}

	// 첨부파일 수정
	public int updateFile(List<MultipartFile> newFiles, List<Long> deleteFileList, String dataTypeCode, Long entityId) throws IOException {
		int newFileUploadCheck = 0;
		int deleteFileCheck = 0;
		int result = 0;
		if (!ObjectUtils.isEmpty(newFiles)) {
			newFileUploadCheck = uploadFile(newFiles, dataTypeCode, entityId);
		}
		if (!ObjectUtils.isEmpty(deleteFileList)) deleteFileCheck = noticeListDAO.deleteNoticeFlie(deleteFileList);

		if (newFileUploadCheck > 0 || deleteFileCheck > 0) {
			result = 1;
		}
		return result;
	}

	// 첨부파일 여부 체크 (UPLOAD): TRUE -> 첨부파일 X / FALSE -> 첨부파일 O
	public boolean checkExistFile(List<MultipartFile> files) {
		boolean check = ObjectUtils.isEmpty(files);
		return !check;
	}

	// 첨부파일 개수 체크
	public int countFiles(List<MultipartFile> files) {
		return ObjectUtils.isEmpty(files) ? 0 : files.size();
	}

	public String detectFileDir(String dataTypeCode) {
		switch (dataTypeCode) {
			case "NOTICE":
				return "notice/";              // 공지사항
			default:
				return null;
		}
	}

	// 해당 디렉토리 존재 여부
	public void isDirectoryExists(String path) throws IOException {
		Path directory = Paths.get(path);

		if (!Files.exists(directory)) {
			Files.createDirectories(directory);
		}
	}
}
