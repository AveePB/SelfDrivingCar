package dev.bpeeva.bookllegro.security.jwt;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class JWT {

    private final String rawForm;

}
