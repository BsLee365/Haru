package kr.co.teamA.Haru.DTO;

import lombok.*;

public interface StressDataDTO {

    String getDiaryData(); // 일기 데이터
    String getFaceData(); // 얼굴 데이터
    String getStressCdate(); // 날짜 데이터
    Double getStressScore(); // 스트레스 수치 데이터
    String getUserId(); // 유저 이름

}
