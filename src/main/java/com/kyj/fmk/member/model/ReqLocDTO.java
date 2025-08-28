package com.kyj.fmk.member.model;

import lombok.Getter;
import lombok.Setter;
/**
 * 2025-08-28
 * @author 김용준
 * 회원도메인- 위치저장관련 DTO
 */
@Getter
@Setter
public class ReqLocDTO {

    private String usrSeqId;
    private double lat;
    private double lot;
}
