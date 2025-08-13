import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";
import { getSession } from "next-auth/react";

class Api {
  private instance: AxiosInstance;

  constructor() {
    this.instance = axios.create({
      baseURL: process.env.NEXT_PUBLIC_API_BASE_URL,
      withCredentials: true,
      headers: { "Content-Type": "application/json" },
    });

    // 요청 인터셉터
    this.instance.interceptors.request.use(async (config) => {
      const session = await getSession();
      if (session?.accessToken) {
        config.headers.Authorization = `Bearer ${session.accessToken}`;
      }
      return config;
    });

    // 응답 인터셉터
    this.instance.interceptors.response.use(
      (response: AxiosResponse) => response,
      async (error) => {
        if (error.response?.status === 401) {
          // TODO: refresh token 로직 추가
        }
        return Promise.reject(error);
      },
    );
  }

  get<T>(url: string, config?: AxiosRequestConfig) {
    return this.instance.get<T>(url, config);
  }

  post<T>(url: string, data?: any, config?: AxiosRequestConfig) {
    return this.instance.post<T>(url, data, config);
  }

  put<T>(url: string, data?: any, config?: AxiosRequestConfig) {
    return this.instance.put<T>(url, data, config);
  }

  delete<T>(url: string, config?: AxiosRequestConfig) {
    return this.instance.delete<T>(url, config);
  }
}

export const api = new Api();
