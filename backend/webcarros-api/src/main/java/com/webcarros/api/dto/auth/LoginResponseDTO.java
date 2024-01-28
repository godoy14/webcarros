package com.webcarros.api.dto.auth;

public record LoginResponseDTO(String nome, String email, Long codigo, String token) {

}
