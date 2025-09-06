package com.kyj.fmk.queue;


import com.kyj.fmk.member.model.kafka.KafkaLogoutDTO;

/**
 * 2025-08-30
 * @author 김용준
 * 회원도메인에 대한 카프카 produce 서비스
 */
public interface KafkaMemPublishService {

    public void puplishMemberLogout(KafkaLogoutDTO logoutKafkaDTO);
}
