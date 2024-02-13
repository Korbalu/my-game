package com.wargame.dto.outgoing;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuildingsDTO {
    private String name;
    private Integer production;
    private Integer cost;
}
