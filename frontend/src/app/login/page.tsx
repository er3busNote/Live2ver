"use client";

import { FC } from "react";
import { signIn } from "next-auth/react";
import { useForm } from "react-hook-form";
import { useSearchParams } from "next/navigation";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";

const loginSchema = z.object({
  username: z.string().min(1, "아이디를 입력해주세요"),
  password: z.string().min(4, "비밀번호는 4자 이상이어야 합니다"),
});

type LoginForm = z.infer<typeof loginSchema>;

const LoginPage: FC = () => {
  const searchParams = useSearchParams();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginForm>({
    resolver: zodResolver(loginSchema),
  });

  const onSubmit = async (data: LoginForm) => {
    await signIn("credentials", {
      username: data.username,
      password: data.password,
      redirect: true,
      callbackUrl: searchParams.get("callbackUrl") || "/dashboard",
    });
  };

  return (
    <>
      <ol className="font-mono list-inside list-decimal text-sm/6 text-center sm:text-left">
        <li className="mb-2 tracking-[-.01em]">
          Get started by editing{" "}
          <code className="bg-black/[.05] dark:bg-white/[.06] font-mono font-semibold px-1 py-0.5 rounded">
            src/app/login/page.tsx
          </code>
          .
        </li>
        <li className="tracking-[-.01em]"></li>
      </ol>
      <form onSubmit={handleSubmit(onSubmit)}>
        <ul>
          <li className="tracking-[-.01em]">
            ID : <input {...register("username")} placeholder="아이디" />
            {errors.username && <p>{errors.username.message}</p>}
          </li>
          <li className="tracking-[-.01em]">
            PW :{" "}
            <input
              {...register("password")}
              type="password"
              placeholder="비밀번호"
            />
            {errors.password && <p>{errors.password.message}</p>}
          </li>
          <li className="tracking-[-.01em]">
            <button type="submit">로그인</button>
          </li>
        </ul>
      </form>
    </>
  );
};

export default LoginPage;
