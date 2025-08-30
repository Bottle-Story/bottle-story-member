package com.kyj.fmk.member.service;

import com.kyj.fmk.core.redis.RedisKey;
import com.kyj.fmk.member.model.kafka.consume.ConsumeMemLocDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 2025-08-28
 * @author 김용준
 * 회원도메인에 관한 서비스
 */
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final RedisTemplate<String,Object> redisTemplate;

    /**
     * 회원의 위치 레디스에 저장
     * @param reqLocDTO
     */
    @Override
    public void saveMemberLoc(ConsumeMemLocDTO reqLocDTO) {

        redisTemplate.opsForGeo().add(
                RedisKey.GEO_MEMBER,
                new org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation<>(
                        reqLocDTO.getUsrSeqId(), // memberId
                        new org.springframework.data.geo.Point(
                                reqLocDTO.getLot(),  // lng
                                reqLocDTO.getLat()    // lat
                        )
                )
        );

    }
}
