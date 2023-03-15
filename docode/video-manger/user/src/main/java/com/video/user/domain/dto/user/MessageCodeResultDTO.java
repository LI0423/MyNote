package com.video.user.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageCodeResultDTO {

    private Boolean isSend;

    private String message;
}
