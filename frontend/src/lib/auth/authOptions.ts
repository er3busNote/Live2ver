import CredentialsProvider from "next-auth/providers/credentials";
import { JWT } from "next-auth/jwt";
import { Session } from "next-auth";
import { TokenManager } from "@/lib/auth/TokenManager";
import { AuthService } from "@/services/AuthService";

const tokenManager = new TokenManager();

export const authOptions = {
  providers: [
    CredentialsProvider({
      name: "Credentials",
      credentials: { username: {}, password: {} },
      authorize: AuthService.authorize,
    }),
  ],
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
  },
};
