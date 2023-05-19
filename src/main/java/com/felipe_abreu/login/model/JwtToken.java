package com.felipe_abreu.login.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {

    @JsonProperty("token")
    private String token;
    @JsonProperty("expired_token")
    private Long expiredToken;

}
