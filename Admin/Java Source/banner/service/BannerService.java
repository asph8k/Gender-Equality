package kr.ge.kwdi.gep.admin.domain.banner.service;

import egovframework.rte.fdl.property.impl.EgovPropertyServiceImpl;
import kr.ge.kwdi.gep.admin.domain.account.dto.AccountVO;
import kr.ge.kwdi.gep.admin.domain.banner.dao.BannerDAO;
import kr.ge.kwdi.gep.admin.domain.banner.dto.BannerDTO;
import kr.ge.kwdi.gep.admin.domain.banner.dto.BannerVO;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerService extends EgovPropertyServiceImpl {

    private final BannerDAO bannerDAO;

    @Value("${file.path}")
    private String filePath;

    // 배너 관리 목록 조회
    public List<BannerDTO.BannerListResponse> selectBannerList(BannerDTO.SearchRequest searchRequest) {
        BannerVO.SearchParam searchParam = BannerVO.SearchParam.builder()
                .searchType(searchRequest.getSearchType())
                .build();

        // 조회
        List<BannerVO.BannerListResult> results = bannerDAO.selectBannerList(searchParam);

        // 변환
        List<BannerDTO.BannerListResponse> bannerListResponses = results.stream()
                .map(result -> BannerDTO.BannerListResponse.builder()
                        .id(result.getId())
                        .name(result.getName())
                        .positionName(result.getPositionName())
                        .imageName(result.getImgOriginalName())
                        .image(ConstantValue.IMAGE_LOAD_API + result.getImgStorageName())
                        .visible(result.getVisible())
                        .bnnrDate(result.getBnnrDate())
                        .ordr(result.getOrdr())
                        .build())
                .collect(Collectors.toList());

        return bannerListResponses;
    }

    // 배너 관리 상세 조회
    public BannerDTO.BannerDetailResponse selectBannerDetail(Long entityId) {
        // 조회
        BannerVO.BannerDetailResult bannerDetailResult = bannerDAO.selectBannerDetail(entityId);

        // 변환
        BannerDTO.BannerDetailResponse bannerDetailResponse = BannerDTO.BannerDetailResponse.builder()
                .id(bannerDetailResult.getId())
                .name(bannerDetailResult.getName())
                .positionCode(bannerDetailResult.getPositionCode())
                .positionName(bannerDetailResult.getPositionName())
                .startDate(bannerDetailResult.getStartDate())
                .startTime(bannerDetailResult.getStartTime())
                .endDate(bannerDetailResult.getEndDate())
                .endTime(bannerDetailResult.getEndTime())
                .visible(TextUtil.convertStringToBoolean(bannerDetailResult.getVisible()))
                .ordr(bannerDetailResult.getOrdr())
                .ordrCount(bannerDetailResult.getOrdrCount())
                .url(bannerDetailResult.getUrl())
                .target(bannerDetailResult.getTarget())
                .targetName(bannerDetailResult.getTargetName())
                .imageName(bannerDetailResult.getImgOriginalName())
                .imgSize(bannerDetailResult.getImgSize())
                .width(bannerDetailResult.getWidth())
                .height(bannerDetailResult.getHeight())
                .content(bannerDetailResult.getContent())
                .rmrk(bannerDetailResult.getRmrk())
                .build();

        return bannerDetailResponse;
    }

    // 배너 위치 공통코드 조회
    public List<BannerDTO.BannerPositionCodeResponse> selectBannerPositionCode() {
        // 조회
        List<BannerVO.BannerPositionCodeResult> bannerPositionCodeResults = bannerDAO.selectBannerPositionCode();

        // 변환
        List<BannerDTO.BannerPositionCodeResponse> bannerPositionCodeResponses = bannerPositionCodeResults.stream()
                .map(result -> BannerDTO.BannerPositionCodeResponse.builder()
                        .id(result.getId())
                        .name(result.getName())
                        .build())
                .collect(Collectors.toList());

        return bannerPositionCodeResponses;
    }

    // 배너 타겟 공통코드 조회
    public List<BannerDTO.BannerTargetCodeResponse> selectBannerTargetCode() {
        // 조회
        List<BannerVO.BannerTargetCodeResult> bannerTargetCodeResults = bannerDAO.selectBannerTargetCode();

        // 변환
        List<BannerDTO.BannerTargetCodeResponse> bannerTargetCodeResponses = bannerTargetCodeResults.stream()
                .map(result -> BannerDTO.BannerTargetCodeResponse.builder()
                        .id(result.getId())
                        .name(result.getName())
                        .build())
                .collect(Collectors.toList());

        return bannerTargetCodeResponses;
    }

    // 배너 관리 등록
    @Transactional
    public int insertBannerAndImg(BannerDTO.InsertRequest insertRequest, MultipartFile newFile) throws IOException {
        // 반환값 설정
        int result = 0;

        // 로그인 중인 사용자 정보
        AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // url 등록시 https:// 로 시작하는 문자열이 아닌지 검증
        String urlCheck = insertRequest.getUrl();
        String check = urlCheck.substring(0, 8);

        // https:// 뒤에 특수문자 검증
        String regex = "^https://[a-zA-Z0-9./:?!=]*$";
        Pattern pattern = Pattern.compile(regex);

        boolean isMatch = pattern.matcher(urlCheck).matches();

        String imgFileActlStrgNm = uploadImgFile(newFile);

        // https://와 다르다면 작동
        if(!check.equals("https://")) {
            throw new GlobalException(ErrorCode.BAD_URL_INSERT, "bad url");
        }

        // https:// 뒤에 특수문자가 들어가 있다면 작동
        if(!isMatch) {
            throw new GlobalException(ErrorCode.NOT_SPECIAL_SYMBOL, "do not special symbol");
        }

        // 등록 시 자동으로 우선 순위 1씩 증가한 값 조회
        BannerVO.BannerOrdrCountResult bannerOrdrCountResult = bannerDAO.selectBannerOrdrCount(insertRequest.getPositionCode());

        // 시작일자, 시작시간, 종료일자, 종료시간이 null일때 작동 (화면에서 무제한으로 등록 했을 때)
        if(insertRequest.getStartDate() == null && insertRequest.getStartTime() == null && insertRequest.getEndTime() == null && insertRequest.getEndDate() == null) {
            BannerVO.InsertParam insertParam = BannerVO.InsertParam.builder()
                    .name(insertRequest.getName())
                    .content(insertRequest.getContent())
                    .positionCode(insertRequest.getPositionCode())
                    .visible(TextUtil.convertBooleanToString(insertRequest.getVisible()))
                    .ordr(bannerOrdrCountResult.getOrdrCount())
                    .url(insertRequest.getUrl())
                    .target(insertRequest.getTarget())
                    .imgStorageName(imgFileActlStrgNm)
                    .imgOriginalName(newFile.getOriginalFilename())
                    .imgSize(newFile.getSize())
                    .width(insertRequest.getWidth())
                    .height(insertRequest.getHeight())
                    .rmrk(insertRequest.getRmrk())
                    .frstJobObj(accountVO.getJobObject())
                    .build();

            int insertBannerChk = bannerDAO.insertBannerStartDtAndEndDtNull(insertParam);

            if(insertBannerChk == 1) {
                result = 1;
            }

            return result;
        } else { // 무제한이 아니라면 작동
            // 현재 시간초를 구해서 기간 날짜에 데이터 포맷팅
            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ss");
            String formatedNow = now.format(formatter);

            String startDate = insertRequest.getStartDate() + " " + insertRequest.getStartTime() + ":" + formatedNow;
            String endDate = insertRequest.getEndDate() + " " + insertRequest.getEndTime() + ":" + formatedNow;

            BannerVO.InsertParam insertParam = BannerVO.InsertParam.builder()
                    .name(insertRequest.getName())
                    .content(insertRequest.getContent())
                    .positionCode(insertRequest.getPositionCode())
                    .startDate(Timestamp.valueOf(startDate))
                    .endDate(Timestamp.valueOf(endDate))
                    .visible(TextUtil.convertBooleanToString(insertRequest.getVisible()))
                    .ordr(bannerOrdrCountResult.getOrdrCount())
                    .url(insertRequest.getUrl())
                    .target(insertRequest.getTarget())
                    .imgStorageName(imgFileActlStrgNm)
                    .imgOriginalName(newFile.getOriginalFilename())
                    .imgSize(newFile.getSize())
                    .width(insertRequest.getWidth())
                    .height(insertRequest.getHeight())
                    .rmrk(insertRequest.getRmrk())
                    .frstJobObj(accountVO.getJobObject())
                    .build();

            int insertBannerChk = bannerDAO.insertBannerAndImg(insertParam);

            if(insertBannerChk == 1) {
                result = 1;
            }

            return result;
        }
    }

    // 배너 관리 수정
    @Transactional
    public int updateBannerAndImg(Long entityId, BannerDTO.UpdateRequest updateRequest, MultipartFile newFile) throws IOException {
        // 반환값 설정
        int result = 0;

        // 로그인 중인 사용자 정보
        AccountVO accountVO = (AccountVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // url 등록시 https:// 로 시작하는 문자열이 아닌지 검증
        String urlCheck = updateRequest.getUrl();
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

        // 현재 시간초를 구해서 기간 날짜에 데이터 포맷팅
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ss");
        String formatedNow = now.format(formatter);

        String startDate = updateRequest.getStartDate() + " " + updateRequest.getStartTime() + ":" + formatedNow;
        String endDate = updateRequest.getEndDate() + " " + updateRequest.getEndTime() + ":" + formatedNow;

        // 순위 업데이트 시 기존에 가지고 있는 순위 값을 변경할 순위 값에 넣어주기 위해 상세 조회 재호출
        BannerVO.BannerDetailResult bannerDetailResult = bannerDAO.selectBannerDetail(entityId);
        int ordr = bannerDetailResult.getOrdr();

        if(newFile == null) {
            // 시작일자, 시작시간, 종료일자, 종료시간이 null일때 작동 (화면에서 무제한으로 수정 했을 때), 배너 이미지가 없을 때
            if(updateRequest.getStartDate() == null || updateRequest.getStartTime() == null || updateRequest.getEndTime() == null || updateRequest.getEndDate() == null) {
                // 우선순위 업데이트
                if(updateRequest.getOrdr() > ordr) {
                    BannerVO.OrdrParam ordrParam = BannerVO.OrdrParam.builder()
                            .entityId(bannerDetailResult.getPositionCode())
                            .exstnOrdr(ordr)
                            .changeOrdr(updateRequest.getOrdr())
                            .build();

                    bannerDAO.updateOrdrMinus(ordrParam);
                }

                if(updateRequest.getOrdr() < ordr) {
                    BannerVO.OrdrParam ordrParam = BannerVO.OrdrParam.builder()
                            .entityId(bannerDetailResult.getPositionCode())
                            .exstnOrdr(ordr)
                            .changeOrdr(updateRequest.getOrdr())
                            .build();

                    bannerDAO.updateOrdrPlus(ordrParam);
                }

                BannerVO.UpdateParam updateParam = BannerVO.UpdateParam.builder()
                        .entityId(entityId)
                        .name(updateRequest.getName())
                        .startDate(null)
                        .endDate(null)
                        .content(updateRequest.getContent())
                        .visible(TextUtil.convertBooleanToString(updateRequest.getVisible()))
                        .ordr(updateRequest.getOrdr())
                        .url(updateRequest.getUrl())
                        .target(updateRequest.getTarget())
                        .rmrk(updateRequest.getRmrk())
                        .lastJobObj(accountVO.getJobObject())
                        .build();

                int updateBannerChk = bannerDAO.updateBanner(updateParam);

                if(updateBannerChk == 1) {
                    result = 1;
                }
            } else { // 무제한이 아니라면 작동, 배너 이미지가 없을 때
                // 우선순위 업데이트
                if(updateRequest.getOrdr() > ordr) {
                    BannerVO.OrdrParam ordrParam = BannerVO.OrdrParam.builder()
                            .entityId(bannerDetailResult.getPositionCode())
                            .exstnOrdr(ordr)
                            .changeOrdr(updateRequest.getOrdr())
                            .build();

                    bannerDAO.updateOrdrMinus(ordrParam);
                }

                if(updateRequest.getOrdr() < ordr) {
                    BannerVO.OrdrParam ordrParam = BannerVO.OrdrParam.builder()
                            .entityId(bannerDetailResult.getPositionCode())
                            .exstnOrdr(ordr)
                            .changeOrdr(updateRequest.getOrdr())
                            .build();

                    bannerDAO.updateOrdrPlus(ordrParam);
                }

                BannerVO.UpdateParam updateParam = BannerVO.UpdateParam.builder()
                        .entityId(entityId)
                        .name(updateRequest.getName())
                        .startDate(Timestamp.valueOf(startDate))
                        .endDate(Timestamp.valueOf(endDate))
                        .content(updateRequest.getContent())
                        .visible(TextUtil.convertBooleanToString(updateRequest.getVisible()))
                        .ordr(updateRequest.getOrdr())
                        .url(updateRequest.getUrl())
                        .target(updateRequest.getTarget())
                        .rmrk(updateRequest.getRmrk())
                        .lastJobObj(accountVO.getJobObject())
                        .build();

                int updateBannerChk = bannerDAO.updateBanner(updateParam);

                if(updateBannerChk == 1) {
                    result = 1;
                }
            }
        } else { // 배너 이미지가 있을 때
            // 시작일자, 시작시간, 종료일자, 종료시간이 null일때 작동 (화면에서 무제한으로 수정 했을 때), 배너 이미지가 있을 때
            if(updateRequest.getStartDate() == null || updateRequest.getStartTime() == null || updateRequest.getEndTime() == null || updateRequest.getEndDate() == null) {
                BannerVO.BannerImgFileResult bannerImgFileResult = bannerDAO.selectBannerImgFile(entityId);
                String deleteResult = deleteImgFile(bannerImgFileResult.getImgStorageName());
                log.info("deleteFileResult === {}", deleteResult);

                String imgFileActlStrgNm = uploadImgFile(newFile);

                // 우선순위 업데이트
                if(updateRequest.getOrdr() > ordr) {
                    BannerVO.OrdrParam ordrParam = BannerVO.OrdrParam.builder()
                            .entityId(bannerDetailResult.getPositionCode())
                            .exstnOrdr(ordr)
                            .changeOrdr(updateRequest.getOrdr())
                            .build();

                    bannerDAO.updateOrdrMinus(ordrParam);
                }

                if(updateRequest.getOrdr() < ordr) {
                    BannerVO.OrdrParam ordrParam = BannerVO.OrdrParam.builder()
                            .entityId(bannerDetailResult.getPositionCode())
                            .exstnOrdr(ordr)
                            .changeOrdr(updateRequest.getOrdr())
                            .build();

                    bannerDAO.updateOrdrPlus(ordrParam);
                }

                BannerVO.UpdateParam updateParam = BannerVO.UpdateParam.builder()
                        .entityId(entityId)
                        .name(updateRequest.getName())
                        .startDate(null)
                        .endDate(null)
                        .content(updateRequest.getContent())
                        .visible(TextUtil.convertBooleanToString(updateRequest.getVisible()))
                        .ordr(updateRequest.getOrdr())
                        .url(updateRequest.getUrl())
                        .target(updateRequest.getTarget())
                        .imgOriginalName(newFile.getOriginalFilename())
                        .imgStorageName(imgFileActlStrgNm)
                        .imgSize(newFile.getSize())
                        .rmrk(updateRequest.getRmrk())
                        .width(updateRequest.getWidth())
                        .height(updateRequest.getHeight())
                        .lastJobObj(accountVO.getJobObject())
                        .build();

                int updateBannerAndImgChk = bannerDAO.updateBannerAndImg(updateParam);

                if(updateBannerAndImgChk == 1) {
                    result = 1;
                }
            } else { // 무제한이 아니라면 작동, 배너 이미지가 있을 때
                BannerVO.BannerImgFileResult bannerImgFileResult = bannerDAO.selectBannerImgFile(entityId);
                String deleteResult = deleteImgFile(bannerImgFileResult.getImgStorageName());
                log.info("deleteFileResult === {}", deleteResult);

                String imgFileActlStrgNm = uploadImgFile(newFile);

                // 우선순위 업데이트
                if(updateRequest.getOrdr() > ordr) {
                    BannerVO.OrdrParam ordrParam = BannerVO.OrdrParam.builder()
                            .entityId(bannerDetailResult.getPositionCode())
                            .exstnOrdr(ordr)
                            .changeOrdr(updateRequest.getOrdr())
                            .build();

                    bannerDAO.updateOrdrMinus(ordrParam);
                }

                if(updateRequest.getOrdr() < ordr) {
                    BannerVO.OrdrParam ordrParam = BannerVO.OrdrParam.builder()
                            .entityId(bannerDetailResult.getPositionCode())
                            .exstnOrdr(ordr)
                            .changeOrdr(updateRequest.getOrdr())
                            .build();

                    bannerDAO.updateOrdrPlus(ordrParam);
                }

                BannerVO.UpdateParam updateParam = BannerVO.UpdateParam.builder()
                        .entityId(entityId)
                        .name(updateRequest.getName())
                        .content(updateRequest.getContent())
                        .startDate(Timestamp.valueOf(startDate))
                        .endDate(Timestamp.valueOf(endDate))
                        .visible(TextUtil.convertBooleanToString(updateRequest.getVisible()))
                        .ordr(updateRequest.getOrdr())
                        .url(updateRequest.getUrl())
                        .target(updateRequest.getTarget())
                        .imgOriginalName(newFile.getOriginalFilename())
                        .imgStorageName(imgFileActlStrgNm)
                        .imgSize(newFile.getSize())
                        .rmrk(updateRequest.getRmrk())
                        .width(updateRequest.getWidth())
                        .height(updateRequest.getHeight())
                        .lastJobObj(accountVO.getJobObject())
                        .build();

                int updateBannerAndImgChk = bannerDAO.updateBannerAndImg(updateParam);

                if(updateBannerAndImgChk == 1) {
                    result = 1;
                }
            }
        }

        return result;
    }

    // 배너 관리 삭제
    @Transactional
    public int updateBannerDelYn(BannerDTO.deleteList deleteList) {
        // 반환값 설정
        int result = 0;

        BannerVO.DeleteParam delParam = BannerVO.DeleteParam.builder()
                .list(deleteList.getDeleteList())
                .build();

        List<BannerVO.BannerImgFileResult> bannerImgFileResults = bannerDAO.selectBannerImgFileList(delParam);

        for(BannerVO.BannerImgFileResult bannerImgFileResult : bannerImgFileResults) {
            String deleteResult = deleteImgFile(bannerImgFileResult.getImgStorageName());
            log.info("deleteFileResult === {}", deleteResult);
        }

        BannerVO.DeleteParam deleteParam = BannerVO.DeleteParam.builder()
                .list(deleteList.getDeleteList())
                .build();

        int deleteBannerChk = bannerDAO.updateBannerDelYn(deleteParam);

        if(deleteBannerChk == 1) {
            result = 1;
        }

        return result;
    }

    public String uploadImgFile(MultipartFile file) throws IOException {
        LocalDate localDate = LocalDate.now();
        String date = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString();
        String saveName = "banner/" + uuid + "_" + date;
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
