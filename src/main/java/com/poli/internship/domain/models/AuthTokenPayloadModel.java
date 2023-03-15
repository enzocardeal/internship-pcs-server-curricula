package com.poli.internship.domain.models;

public class AuthTokenPayloadModel {
    public static record AuthTokenPayload(String userId, String email, int expiresIn) {}
}