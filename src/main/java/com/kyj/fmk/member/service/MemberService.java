package com.kyj.fmk.member.service;

import com.kyj.fmk.member.model.kafka.consume.ConsumeMemLocDTO;

/**
 * 2025-08-28
 * @author 김용준
 * 회원도메인에 관한 서비스
 */
public interface MemberService {

    public void saveMemberLoc(ConsumeMemLocDTO reqLocDTO);
}
