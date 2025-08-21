package com.kyj.fmk.common.service;


import com.kyj.fmk.core.model.cmcd.res.ResCommonCdDTO;
import com.kyj.fmk.common.repository.CommonRepository;
import com.kyj.fmk.core.model.CmCdConst;

import com.kyj.fmk.core.redis.RedisKey;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 2025-08-10
 * @author 김용준
 * 애플리케이션 시작시 공통코드,기술스택코드,직무코드를 레디스에 캐싱하기위한 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonCdLoadServiceImpl implements CommonCdLoadService{
    private final RedisTemplate<String, String> redisTemplate;
    private final CommonRepository commonRepository;

    /**
     * 공통코드 로드
     */
    @PostConstruct
    @Override
    public void loadCmCd() {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        //공통코드 리스트
        List<ResCommonCdDTO> list = commonRepository.cmCdList(null);
        log.info("--------공통코드 적재시작----------");
        int cnt = 0;
        //공통 코드별 분기 저장
        for (ResCommonCdDTO dto : list){

            log.info("공통코드적재 값={}",dto.getCmCdVal());
            log.info("공통코드적재 이름={}",dto.getCmCdValNm());

           if(dto.getCmCd().equals(CmCdConst.TIME_CODE)){
               hashOps.put(RedisKey.CM_TIME_CODE,dto.getCmCdVal(),dto.getCmCdValNm());
           } else if (dto.getCmCd().equals(CmCdConst.PARTICLE_CODE)) {
               hashOps.put(RedisKey.CM_PARTICLE_CODE,dto.getCmCdVal(),dto.getCmCdValNm());
           } else if (dto.getCmCd().equals(CmCdConst.SKY_CODE)) {
               hashOps.put(RedisKey.CM_SKY_CODE,dto.getCmCdVal(),dto.getCmCdValNm());
           } else if (dto.getCmCd().equals(CmCdConst.BTL_STATUS_CODE)) {
               hashOps.put(RedisKey.CM_BTL_STATUS_CODE,dto.getCmCdVal(),dto.getCmCdValNm());
           } else if (dto.getCmCd().equals(CmCdConst.OCEAN_CODE)) {
               hashOps.put(RedisKey.CM_OCEAN_CODE,dto.getCmCdVal(),dto.getCmCdValNm());
           } else if (dto.getCmCd().equals(CmCdConst.EVENT_STATUS_CODE)) {
               hashOps.put(RedisKey.CM_EVENT_STATUS_CODE,dto.getCmCdVal(),dto.getCmCdValNm());
           }
                cnt++;
        }

        log.info("--------공통코드 적재종료----------");
        log.info("공통코드 적재건수={}",cnt);
    }

}
