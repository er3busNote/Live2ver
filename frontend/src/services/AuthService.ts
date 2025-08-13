import { api } from "@/lib/Api";
import { LoginData, TokenData } from "@/types/auth";
import { ResponseData } from "@/types/response";

export const AuthService = {
  login: async (credentials: LoginData) => {
    const { data } = await api.post<ResponseData>("/auth/login", credentials);
    return data.data as TokenData;
  },
  refreshToken: async (refreshToken: string) => {
    const { data } = await api.post<ResponseData>("/auth/refresh", { refreshToken });
    return data.data as TokenData;
  },
  authorize: async (credentials?: LoginData) => {
    if (!credentials) return null;
    try {
      const { data } = await api.post<ResponseData>("/auth/login", credentials);
      const tokenData = data.data as TokenData;
      return { id: credentials.username, ...tokenData };
    } catch (error) {
      console.error("Login failed", error);
      return null;
    }
  },
};
