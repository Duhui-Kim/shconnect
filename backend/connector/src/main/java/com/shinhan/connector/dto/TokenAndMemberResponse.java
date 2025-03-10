package com.shinhan.connector.dto;

import com.shinhan.connector.config.jwt.Token;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenAndMemberResponse {
    Token token;
    SignInResponse signInResponse;
}
