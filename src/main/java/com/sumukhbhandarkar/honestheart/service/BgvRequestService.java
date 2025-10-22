package com.sumukhbhandarkar.honestheart.service;

import com.sumukhbhandarkar.honestheart.domain.BgvRequest;
import com.sumukhbhandarkar.honestheart.repo.BgvRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BgvRequestService {

  private final BgvRequestRepository repo;
  private static final SecureRandom RNG = new SecureRandom();

  /** Simple URL-safe token (32 bytes -> ~43 chars base64url) */
  private String newToken() {
    byte[] buf = new byte[32];
    RNG.nextBytes(buf);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
  }

  public BgvRequest create(String targetEmail, String targetName, String requesterEmail, String message) {
    BgvRequest entity = BgvRequest.builder()
      .id(UUID.randomUUID())
      .targetEmail(targetEmail)
      .targetName(targetName)
      .requesterEmail(requesterEmail)
      .message(message)
      .status("pending")
      .token(newToken())
      .createdAt(OffsetDateTime.now())
      .expiresAt(OffsetDateTime.now().plus(14, ChronoUnit.DAYS)) // optional 14-day expiry
      .build();

    return repo.save(entity);
  }

  // You can add methods like accept(token), revoke(token) later.
}
