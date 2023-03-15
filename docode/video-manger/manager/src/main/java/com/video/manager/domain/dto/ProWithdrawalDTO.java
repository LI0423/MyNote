package com.video.manager.domain.dto;

import lombok.Data;

import java.util.List;
@Data
public class ProWithdrawalDTO {
    private Long totalAmount;
    private List<WithdrawalDTO> list;
}
