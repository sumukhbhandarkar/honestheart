package com.sumukhbhandarkar.honestheart.web;

import com.sumukhbhandarkar.honestheart.domain.BgvRequest;
import com.sumukhbhandarkar.honestheart.service.BgvRequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public/requests")
@RequiredArgsConstructor
public class RequestController {

  private final BgvRequestService service;

  // Base URL for your app; override in application.yml for prod
  @Value("${app.public-base-url:https://localhost:8080}")
  private String publicBaseUrl;

  public record CreateRequest(
      @Email @NotBlank String targetEmail,
      String targetName,
      @Email String requesterEmail,
      String message
  ) {}

  @PostMapping
  public ApiResponse<Map<String, Object>> create(@Valid @RequestBody CreateRequest body) {
    BgvRequest saved = service.create(body.targetEmail(), body.targetName(), body.requesterEmail(), body.message());

    // Build a consent link (you'll create a page/controller to handle this route later)
    String consentLink = publicBaseUrl + "/consent?token=" + saved.getToken();

    return new ApiResponse<>(
      true,
      Map.of(
        "id", saved.getId(),
        "status", saved.getStatus(),
        "token", saved.getToken(),      // return only if you control who calls this endpoint
        "consentLink", consentLink      // for now useful in testing; in production send via email
      ),
      "request accepted; send invite to partner to review & consent"
    );
  }
}
