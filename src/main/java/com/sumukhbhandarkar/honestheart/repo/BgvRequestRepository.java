package com.sumukhbhandarkar.honestheart.repo;

import com.sumukhbhandarkar.honestheart.domain.BgvRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BgvRequestRepository extends JpaRepository<BgvRequest, UUID> {
  Optional<BgvRequest> findByToken(String token);
}