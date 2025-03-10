package com.shinhan.connector.dto;

import com.shinhan.connector.entity.Member;
import com.shinhan.connector.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequest {
    String id;
    String password;
    String name;
    Integer age;
    String gender;
    String contact;
    String bankCode;
    String accountNumber;

}
