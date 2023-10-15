package com.flightapp.security.token;

import com.flightapp.enums.Role;
import lombok.Data;

@Data
public final class TokenDetails {

    private final String id;
    private final String email;
    private final Role role;
}