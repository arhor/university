package by.arhor.university.web.security;

public final class JwtResponse {

  private String accessToken;
  private String tokenType = "Bearer";

  public static JwtResponse withToken(String token) {
    return new JwtResponse(token);
  }

  private JwtResponse(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }
}
