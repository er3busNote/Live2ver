import { api } from "@/lib/Api";
import { LoginData, TokenData } from "@/types/auth";

export const AuthService = {
  login: (credentials: LoginData) =>
    api.post<LoginData>("/auth/login", credentials),
  refreshToken: (refreshToken: string) =>
    api.post<TokenData>("/auth/refresh", { refreshToken }),
  authorize: async (credentials?: LoginData) => {
    if (!credentials) return null;
    try {
      const { data } = await api.post<LoginData>("/auth/login", credentials);
      return { id: credentials.username, ...data }; // user 객체 리턴
    } catch (error) {
      console.error("Login failed", error);
      return null;
    }
  },
};
