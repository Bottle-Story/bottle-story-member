package com.kyj.fmk.test.controller.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.exception.custom.KyjBizException;
import com.kyj.fmk.core.file.FileService;
import com.kyj.fmk.core.model.dto.ResApiDTO;
import com.kyj.fmk.core.model.enm.CmErrCode;

import com.kyj.fmk.core.model.wheather.ReqWheatherApiDTO;
import com.kyj.fmk.core.model.wheather.ResWheatherApiDTO;
import com.kyj.fmk.core.redis.RedisKey;
import com.kyj.fmk.core.service.eai.WheatherApiService;
import com.kyj.fmk.sec.annotation.PublicEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberTestController {

    private final ObjectMapper objectMapper;
    private final WheatherApiService wheatherApiService;
    private final RedisTemplate<String,Object> redisTemplate;
    private final RestTemplate restTemplate = new RestTemplate();
    private  String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?";
    private final KafkaTemplate<String,String> kafkaTemplate;

    @RequestMapping("/testk")
    @PublicEndpoint
    public String test(){
        for(int i=0; i<1000; i++ ){
            String test ="wheather"+i;

            kafkaTemplate.send("wheather-bgm.wheather-update",test) ;
        }

        return "ok";
    }

    @PublicEndpoint
    @GetMapping("/test")
    public String test23(){

        return "MEMBER";
    }

    @PublicEndpoint
    @GetMapping("/devtest2")
    public ResponseEntity<Object> test2(){
        ResApiDTO resApiDTO = new ResApiDTO(null);
        return ResponseEntity.ok(resApiDTO);
    }


    @PublicEndpoint
    @GetMapping("/devtest3")
    public String test3(){
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        Map<String, String> grpStCdMap = hashOps.entries(RedisKey.CM_EVENT_STATUS_CODE);
        List<String> keys = new ArrayList<>(grpStCdMap.keySet());

        for(String key2 : keys){
            System.out.println("key2 = " + key2);

        }
        return null;
    }

    @PublicEndpoint
    @GetMapping("/devtest4")
    public ResponseEntity<Object> test4(){
        throw new KyjBizException(CmErrCode.SEC010);

    }

    @PublicEndpoint
    @GetMapping("/devtest5")
    public ResponseEntity<Object> test5(int i){
       return null;

    }

    @GetMapping("/devtest10")
    public ResponseEntity<Object> test7(){
        return ResponseEntity.ok("test1123");

    }

    @GetMapping("/devtest11")
    @PublicEndpoint
    public ResponseEntity<Object> test11() throws JsonProcessingException {
         double lat = 37.65866863366281;
         double lot = 126.90675341118721;

        ReqWheatherApiDTO reqWheatherApiDTO = new ReqWheatherApiDTO();
        reqWheatherApiDTO.setLat(lat);
        reqWheatherApiDTO.setLot(lot);
         List<ResWheatherApiDTO> list = wheatherApiService.loadWheather(reqWheatherApiDTO);

        return ResponseEntity.ok(list);

    }
}
