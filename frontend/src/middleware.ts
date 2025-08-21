import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import { getToken } from "next-auth/jwt";

export async function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;

  // 보호할 경로 지정
  const protectedPaths = ["/dashboard", "/admin"];
  const isProtected = protectedPaths.some((path) => pathname.startsWith(path));

  if (isProtected) {
    const token = await getToken({
      req: request,
      secret: process.env.NEXT_AUTH_SECRET,
    });
    if (!token) {
      const loginUrl = new URL("/login", request.url);
      loginUrl.searchParams.set("callbackUrl", pathname);
      return NextResponse.redirect(loginUrl);
    }
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/dashboard/:path*", "/admin/:path*"],
};
