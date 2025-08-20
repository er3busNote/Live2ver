"use client";

import { SessionProvider } from "next-auth/react";
import { FC, ReactNode } from "react";

interface AuthProviderProps {
  children: ReactNode;
}

const AuthProvider: FC<AuthProviderProps> = ({ children }) => {
  return (
    <SessionProvider
      refetchInterval={5 * 60} // 5분마다 세션 재검증
      refetchOnWindowFocus={true} // 포커스 시 세션 갱신
    >
      {children}
    </SessionProvider>
  );
};

export default AuthProvider;
