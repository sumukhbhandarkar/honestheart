package com.sumukhbhandarkar.honestheart.domain;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "bgv_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BgvRequest {
    @Id @UuidGenerator
    private UUID id;

    @Column(name = "target_email", nullable = false)
    private String targetEmail;

    @Column(name = "target_name")
    private String targetName;

    @Column(name = "requester_email")
    private String requesterEmail;

    @Lob
    private String message;

    @Column(nullable = false)
    private String status; // pending, sent, accepted, revoked, expired

    @Column(nullable = false, unique = true, length = 64)
    private String token;  // used in email consent link

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;
}
