package com.kyj.fmk.member.controller;

import com.kyj.fmk.member.model.kafka.KafkaLogoutDTO;
import com.kyj.fmk.queue.KafkaMemPublishService;
import com.kyj.fmk.sec.dto.oauth2.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2025-08-10
 * @author 김용준
 * 회원정보를 관리하고 쓰는 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/member/")
@RequiredArgsConstructor
public class MemberController {

    private final KafkaMemPublishService kafkaMemPublishService;

    /**
     * 회원 로그아웃 전 선행 처리될 api
     * @param customOAuth2User
     */
    @PutMapping("/pre/logout")
    public void preLogout(@AuthenticationPrincipal CustomOAuth2User customOAuth2User){
        KafkaLogoutDTO logoutKafkaDTO = new KafkaLogoutDTO(String.valueOf(customOAuth2User.getUsrSeqId()));

        kafkaMemPublishService.puplishMemberLogout(logoutKafkaDTO);

    }



}
