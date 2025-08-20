import CredentialsProvider from "next-auth/providers/credentials";
import { JWT } from "next-auth/jwt";
import { Session, AuthOptions } from "next-auth";
import { TokenManager } from "@/lib/auth/TokenManager";
import { AuthService } from "@/services/AuthService";

const tokenManager = new TokenManager();

export const authOptions: AuthOptions = {
  providers: [
    CredentialsProvider({
      name: "Credentials",
      credentials: {
        username: { label: "아이디", type: "text" },
        password: { label: "비밀번호", type: "password" },
      },
      authorize: AuthService.authorize,
    }),
  ],
  session: { strategy: "jwt", maxAge: 60 * 60 * 4 }, // session 자체의 만료시간 (4시간)
  jwt: { maxAge: 60 * 60 * 4 }, // JWT 자체의 만료시간 (4시간)
  cookies: {
    sessionToken: {
      name:
        process.env.NODE_ENV === "production"
          ? "__Secure-next-auth.session-token"
          : "next-auth.session-token",
      options: {
        httpOnly: true,
        sameSite: "lax",
        path: "/",
        secure: process.env.NODE_ENV === "production",
      },
    },
  },
  pages: {
    signIn: "/login",
  },
  callbacks: {
    jwt: async ({ token, user }: { token: JWT; user?: any }) => {
      if (user) {
        tokenManager.setTokens({
          accessToken: user.accessToken!,
          refreshToken: user.refreshToken!,
        });
      }

      const accessToken = await tokenManager.getValidAccessToken();
      token.accessToken = accessToken;
      token.refreshToken = tokenManager.getRefreshToken();
      return token;
    },
    session: async ({ session, token }: { session: Session; token: JWT }) => {
      session.accessToken = token.accessToken as string;
      session.refreshToken = token.refreshToken as string;
      return session;
    },
    redirect: async ({ url, baseUrl }: { url: string; baseUrl: string }) => {
      if (url.startsWith(baseUrl)) {
        return url;
      }
      return `${baseUrl}/dashboard`;
    },
  },
};
