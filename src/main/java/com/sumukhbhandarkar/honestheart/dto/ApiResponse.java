package com.sumukhbhandarkar.honestheart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Standard API response wrapper for all endpoints.
 * Provides a consistent structure: { ok, data, message }.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  private boolean ok;
  private T data;
  private String message;

  public ApiResponse() {}

  public ApiResponse(boolean ok, T data, String message) {
    this.ok = ok;
    this.data = data;
    this.message = message;
  }

  // Convenience factory methods
  public static <T> ApiResponse<T> success(T data, String message) {
    return new ApiResponse<>(true, data, message);
  }

  public static <T> ApiResponse<T> failure(String message) {
    return new ApiResponse<>(false, null, message);
  }

  // Getters & setters
  public boolean isOk() { return ok; }
  public void setOk(boolean ok) { this.ok = ok; }

  public T getData() { return data; }
  public void setData(T data) { this.data = data; }

  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
}
