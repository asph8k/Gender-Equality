package kr.ge.kwdi.gep.admin.domain.relatedInst.service;

import kr.ge.kwdi.gep.admin.domain.account.dto.AccountVO;
import kr.ge.kwdi.gep.admin.domain.relatedInst.dao.RelatedInstDAO;
import kr.ge.kwdi.gep.admin.domain.relatedInst.dto.RelatedInstDTO;
import kr.ge.kwdi.gep.admin.domain.relatedInst.dto.RelatedInstVO;
import kr.ge.kwdi.gep.admin.global.common.error.ErrorCode;
import kr.ge.kwdi.gep.admin.global.common.error.GlobalException;
import kr.ge.kwdi.gep.admin.global.constant.ConstantValue;
import kr.ge.kwdi.gep.admin.global.util.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelatedInstService {

    private final RelatedInstDAO relatedInstDAO;

    @Value("${file.path}")
    private String filePath;

    // 유관기관 목록 조회
    public List<RelatedInstDTO.RltdInstListResponse> selectRltdInstList(RelatedInstDTO.SearchRequest searchRequest) {
        RelatedInstVO.SearchParam searchParam = RelatedInstVO.SearchParam.builder()
                .q(searchRequest.getQ())
                .searchType(searchRequest.getSearchType())
                .build();
        // 조회
        List<RelatedInstVO.RltdInstListResult> results = relatedInstDAO.selectRltdInstList(searchParam);
        // 변환
        List<RelatedInstDTO.RltdInstListResponse> rltdInstListResponses = results.stream()
                .map(result -> RelatedInstDTO.RltdInstListResponse.builder()
                        .id(result.getId())
                        .name(result.getName())
                        .fileOriginal(result.getFileOriginal())
                        .fileStorage(ConstantValue.IMAGE_LOAD_API + result.getFileStorage())
                        .url(result.getUrl())
                        .visible(TextUtil.convertStringToBoolean(result.getVisible()))
                        .created(result.getCreated())
                        .build())
                .collect(Collectors.toList());

        return rltdInstListResponses;
    }

    // 유관기관 상세 조회
    public RelatedInstDTO.RltdInstDetailResponse selectRltdInstDtl(Long entityId) {
        // 조회
        RelatedInstVO.RltdInstDetailResult rltdInstDetailResult = relatedInstDAO.selectRltdInstDtl(entityId);

        // 변환
        RelatedInstDTO.RltdInstDetailResponse detailResponse = RelatedInstDTO.RltdInstDetailResponse.builder()
                .id(rltdInstDetailResult.getId())
                .name(rltdInstDetailResult.getName())
                .url(rltdInstDetailResult.getUrl())
                .fileOriginal(rltdInstDetailResult.getFileOriginal())
                .fileSize(rltdInstDetailResult.getFileSize())
                .visible(TextUtil.convertStringToBoolean(rltdInstDetailResult.getVisible()))
                .rmrk(rltdInstDetailResult.getRmrk())
                .created(rltdInstDetailResult.getCreated())
                .build();

        return detailResponse;
    }

    // 유관기관 등록
    @Transactional
    public int insertRltdInst(RelatedInstDTO.RltdInstInsertRequest rltdInstInsertRequest, MultipartFile newFiles) throws IOException {
        // 반환값 설정
        int result = 0;

        // 로그인 중인 사용자 ID
        AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // url 등록시 https:// 로 시작하는 문자열이 아닌지 검증
        String urlCheck = rltdInstInsertRequest.getUrl();
        String check = urlCheck.substring(0, 8);

        // https:// 뒤에 특수문자 검증
        String regex = "^https://[a-zA-Z0-9./:?!=]*$";
        Pattern pattern = Pattern.compile(regex);

        boolean isMatch = pattern.matcher(urlCheck).matches();

        // https://와 다르다면 작동
        if(!check.equals("https://")) {
            throw new GlobalException(ErrorCode.BAD_URL_INSERT, "bad url");
        }

        // https:// 뒤에 특수문자가 들어가 있다면 작동
        if(!isMatch) {
            throw new GlobalException(ErrorCode.NOT_SPECIAL_SYMBOL, "do not special symbol");
        }

        String imgFileActlStrgNm = uploadImgFile(newFiles);

        RelatedInstVO.RltdInstInsertParam rltdInstInsertParam = RelatedInstVO.RltdInstInsertParam.builder()
                .name(rltdInstInsertRequest.getName())
                .url(rltdInstInsertRequest.getUrl())
                .visible(TextUtil.convertBooleanToString(rltdInstInsertRequest.getVisible()))
                .rmrk(rltdInstInsertRequest.getRmrk())
                .originalName(newFiles.getOriginalFilename())
                .fileSize(newFiles.getSize())
                .storageName(imgFileActlStrgNm)
                .frstJobObj(accountVO.getJobObject())
                .build();

        int rltdInstInsertCheck = relatedInstDAO.insertRltdInst(rltdInstInsertParam);

        if(rltdInstInsertCheck == 1) {
            result = 1;
        }

        return result;
    }

    // 유관기관 수정
    @Transactional
    public int updateRltdInst(Long entityId, RelatedInstDTO.RltdInstUpdateRequest rltdInstUpdateRequest, MultipartFile newFiles) throws IOException {
        // 반환값 설정
        int result = 0;

        // 로그인 중인 사용자 ID
        AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // url 등록시 https:// 로 시작하는 문자열이 아닌지 검증
        String urlCheck = rltdInstUpdateRequest.getUrl();
        String check = urlCheck.substring(0, 8);

        // https:// 뒤에 특수문자 검증
        String regex = "^https://[a-zA-Z0-9./:?!=]*$";
        Pattern pattern = Pattern.compile(regex);

        boolean isMatch = pattern.matcher(urlCheck).matches();

        // https://와 다르다면 작동
        if(!check.equals("https://")) {
            throw new GlobalException(ErrorCode.BAD_URL_INSERT, "bad url");
        }

        // https:// 뒤에 특수문자가 들어가 있다면 작동
        if(!isMatch) {
            throw new GlobalException(ErrorCode.NOT_SPECIAL_SYMBOL, "do not special symbol");
        }

        if(newFiles == null) {
            RelatedInstVO.RltdInstUpdateParam rltdInstUpdateParam = RelatedInstVO.RltdInstUpdateParam.builder()
                    .entityId(entityId)
                    .name(rltdInstUpdateRequest.getName())
                    .url(rltdInstUpdateRequest.getUrl())
                    .visible(TextUtil.convertBooleanToString(rltdInstUpdateRequest.getVisible()))
                    .rmrk(rltdInstUpdateRequest.getRmrk())
                    .lastJobObj(accountVO.getJobObject())
                    .build();

            int rltdInstUpdateCheck = relatedInstDAO.updateRltdInst(rltdInstUpdateParam);

            if(rltdInstUpdateCheck == 1) {
                result = 1;
            }
        } else {
            RelatedInstVO.ImgFileDtlResult imgFileDtlResult = relatedInstDAO.selectImgFileDtl(entityId);
            String deleteResult = deleteImgFile(imgFileDtlResult.getStorageName());
            log.info("deleteFileResult === {}", deleteResult);

            String imgFileActlStrgNm = uploadImgFile(newFiles);

            RelatedInstVO.RltdInstUpdateParam rltdInstUpdateParam = RelatedInstVO.RltdInstUpdateParam.builder()
                    .entityId(entityId)
                    .name(rltdInstUpdateRequest.getName())
                    .url(rltdInstUpdateRequest.getUrl())
                    .visible(TextUtil.convertBooleanToString(rltdInstUpdateRequest.getVisible()))
                    .rmrk(rltdInstUpdateRequest.getRmrk())
                    .originalName(newFiles.getOriginalFilename())
                    .storageName(imgFileActlStrgNm)
                    .fileSize(newFiles.getSize())
                    .lastJobObj(accountVO.getJobObject())
                    .build();

            int rltdInstUpdateCheck = relatedInstDAO.updateRltdImg(rltdInstUpdateParam);

            if(rltdInstUpdateCheck == 1) {
                result = 1;
            }
        }

        return result;
    }

    // 유관기관 삭제
    @Transactional
    public int deleteRltdInst(RelatedInstDTO.deleteList deleteList) {
        // 반환값 설정
        int result = 0;

        RelatedInstVO.RltdInstDeleteParam rltdInstDeleteParam = RelatedInstVO.RltdInstDeleteParam.builder()
                .list(deleteList.getDeleteList())
                .build();

        int rltdInstDeleteCheck = relatedInstDAO.deleteRltdInst(rltdInstDeleteParam);

        if(rltdInstDeleteCheck == 1) {
            result = 1;
        }

        return result;
    }

    public String uploadImgFile(MultipartFile file) throws IOException {
        LocalDate localDate = LocalDate.now();
        String date = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString();
        String saveName = "relatedinst/" + uuid + "_" + date;
        String savePath = filePath + saveName;
        System.out.println("savePath = " + savePath);

        isDirectoryExists(savePath);

        file.transferTo(new File(savePath));

        return saveName;
    }

    public String deleteImgFile(String savePath) {
        File file = new File(filePath + savePath);
        if (file.exists()) {
            if (file.delete()) {
                return "삭제 완료";
            } else {
                return "삭제 실패";
            }
        } else {
            return "파일 없음";
        }
    }

    public void isDirectoryExists(String path) throws IOException {
        Path directory = Paths.get(path);

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
    }
}
