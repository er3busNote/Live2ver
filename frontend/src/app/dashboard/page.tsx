import { getServerSession } from "next-auth";
import { redirect } from "next/navigation";
import { authOptions } from "@/lib/auth/authOptions";

export default async function DashboardPage() {
  const session = await getServerSession(authOptions);
  if (!session) redirect("/login");

  return (
    <>
      <ol className="font-mono list-inside list-decimal text-sm/6 text-center sm:text-left">
        <li className="mb-2 tracking-[-.01em]">
          Get started by editing{" "}
          <code className="bg-black/[.05] dark:bg-white/[.06] font-mono font-semibold px-1 py-0.5 rounded">
            src/app/dashboard/page.tsx
          </code>
          .
        </li>
        <li className="tracking-[-.01em]">
          {session.user?.name}님 환영합니다.
        </li>
      </ol>
    </>
  );
}
