package com.wargame.dto.outgoing;

import com.wargame.domain.CustomUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {
    private String token;

    public TokenDTO(CustomUser user) {
        this.token = user.getToken();
    }
}
