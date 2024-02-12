package com.wargame.dto.outgoing;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoggedInUserIdDTO {
    private Long userId;
    private String userName;

    public LoggedInUserIdDTO(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
