package com.security.oauth.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
public class ResponseMessage {
    private Date responseTime;
    private Object data;

    @Builder
    public ResponseMessage(Date responseTime, Object data) {
        this.responseTime = responseTime;
        this.data = data;
    }
}
