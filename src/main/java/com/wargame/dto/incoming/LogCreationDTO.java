package com.wargame.dto.incoming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogCreationDTO {
    private String header;
    private String log;
}
