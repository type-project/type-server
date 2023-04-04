package io.sookmyung.type.controller.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NoteSaveRequestDto {
    private String name;
    private String slideUrl;
}
