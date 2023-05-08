package com.example.baeldunginheritance.collection;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="jwtToken")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class JWTToken {

    @Id
    private String id;

    private String token;

    @Enumerated(value = EnumType.STRING)
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;

    private User user;
}
