/* eslint-disable @typescript-eslint/no-unused-vars */

import NextAuth from "next-auth";

declare module "next-auth" {
  interface Session {
    accessToken?: string;
    refreshToken?: string;
    user?: {
      id: string;
      name?: string | null;
      email?: string | null;
    };
  }

  interface JWT {
    accessToken?: string;
    refreshToken?: string;
  }
}
