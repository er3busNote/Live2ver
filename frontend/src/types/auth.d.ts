interface LoginData {
  username: string;
  password: string;
}

interface TokenData {
  accessToken: string;
  refreshToken: string;
}

export { LoginData, TokenData };
