package com.kyj.fmk.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.member.model.kafka.consume.ConsumeMemLocDTO;
import com.kyj.fmk.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

/**
 * 2025-08-28
 * @author 김용준
 * 회원도메인에 대한 카프카 consume 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class KafkaMemConsumeServiceImpl implements KafkaMemConsumeService{

    private final ObjectMapper objectMapper;
    private final MemberService memberService;

    /**
     * REALTIMESERVICE에서 위치정보를 받아 레디스의 회원위치정보를 저장 하는 Consumer
     * @param record
     * @param ack
     */
    @Override
    @KafkaListener(
            topics = KafkaTopic.REALTIME_MEMBER_LOCATION,
            groupId = "#{ 'member.consume.' + T(com.kyj.fmk.core.model.KafkaTopic).REALTIME_MEMBER_LOCATION }"
    )
    public void consumeMemberLocation(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String json =  record.value();
        ConsumeMemLocDTO reqLocDTO = null;
        try {
            reqLocDTO = objectMapper.readValue(json, ConsumeMemLocDTO.class);

        } catch (JsonProcessingException e) {
            throw new KyjSysException(CmErrCode.CM016);
        }

        memberService.saveMemberLoc(reqLocDTO);
        ack.acknowledge();

    }
}
