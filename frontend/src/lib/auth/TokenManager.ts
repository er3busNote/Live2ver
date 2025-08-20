import { AuthService } from "@/services/AuthService";
import { TokenData } from "@/types/auth";

export class TokenManager {
  private token: TokenData | null = null;

  constructor(initialToken?: TokenData) {
    if (initialToken) this.token = initialToken;
  }

  setTokens(tokens: TokenData) {
    this.token = tokens;
  }

  getAccessToken(): string | null {
    return this.token?.accessToken ?? null;
  }

  getRefreshToken(): string | null {
    return this.token?.refreshToken ?? null;
  }

  /**
   * Access Token 만료 여부 확인
   */
  isAccessTokenExpired(): boolean {
    if (!this.token?.accessToken) return true;
    try {
      const [, payload] = this.token.accessToken.split(".");
      const decoded = JSON.parse(Buffer.from(payload, "base64").toString());
      const now = Math.floor(Date.now() / 1000);
      return decoded.exp ? decoded.exp < now : true;
    } catch (error) {
      console.error("Failed to decode access token", error);
      return true;
    }
  }

  /**
   * Access Token 만료 시 Refresh Token으로 갱신
   */
  async getValidAccessToken(): Promise<string> {
    if (!this.token) throw new Error("No token available");

    if (!this.isAccessTokenExpired()) {
      return this.token.accessToken;
    }

    if (!this.token.refreshToken) {
      throw new Error("No refresh token available");
    }

    // refresh 호출
    const data = await AuthService.refreshToken(this.token.refreshToken);
    this.setTokens(data);
    return data.accessToken;
  }
}
