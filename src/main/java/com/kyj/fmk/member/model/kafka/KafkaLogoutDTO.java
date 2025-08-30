package com.kyj.fmk.member.model.kafka;

import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.core.model.dto.BaseKafkaDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaLogoutDTO extends BaseKafkaDTO {

    private String usrSeqId;

    public KafkaLogoutDTO(String usrSeqId){
        this.usrSeqId = usrSeqId;
        super.setFrom("MEMBER");
        super.setTopic(KafkaTopic.MEMBER_LOGOUT);
    }

}
