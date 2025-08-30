package com.kyj.fmk.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.exception.custom.KyjBizException;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.member.model.kafka.KafkaLogoutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * 2025-08-30
 * @author 김용준
 * 회원도메인에 대한 카프카 produce 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class KafkaMemPublishServiceImpl implements KafkaMemPublishService{

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    /**
     *  로그아웃에 대한 이벤트 발행
     * @param logoutKafkaDTO
     */
    @Override
    public void puplishMemberLogout(KafkaLogoutDTO logoutKafkaDTO) {

        String data  = null;

        try {

            data = objectMapper.writeValueAsString(logoutKafkaDTO);

        } catch (JsonProcessingException e) {
            throw new KyjSysException(CmErrCode.CM016);
        }

        if(data == null){
            throw new KyjBizException(CmErrCode.CM019);

        }
        kafkaTemplate.send(logoutKafkaDTO.getTopic(),data);
        System.out.println("logoutKafkaDTO = " + logoutKafkaDTO);
    }
}

