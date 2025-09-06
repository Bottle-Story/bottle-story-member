package com.kyj.fmk.queue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

/**
 * 2025-08-28
 * @author 김용준
 * 회원도메인에 대한 카프카 consume 서비스
 */
public interface KafkaMemConsumeService {
    public void consumeMemberLocation(ConsumerRecord<String, String> record, Acknowledgment ack);
}
