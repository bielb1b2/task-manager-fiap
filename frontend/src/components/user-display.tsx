import { Copy } from "lucide-react";
import type { ComponentProps } from "react";
import toast from "react-hot-toast";
import { twMerge } from "tailwind-merge";

interface UserDisplayProps extends ComponentProps<"div"> {
    userId: string;
}

export function UserDisplay({ userId, className, ...props }: UserDisplayProps) {

    const copyToClipboard = () => {
        navigator.clipboard.writeText(userId);
        toast.success("Copiado")
    }

    return (
        <div
            className={twMerge(
                "group flex w-full items-center justify-center mt-1 gap-2",
                className
            )}
            {...props}
        >
            <span className="text-zinc-500">
                UserID:
            </span>

            <span
                onClick={copyToClipboard}
                className="
                    text-zinc-500 underline underline-offset-4 cursor-pointer group-hover:text-zinc-300 transition-colors"
            >
                {userId.toString()}
            </span>

            <Copy
                strokeWidth={2.5}
                size={16}
                onClick={copyToClipboard}
                className="text-zinc-500 cursor-pointer group-hover:text-zinc-300 transition-colors"
            />
        </div>
    );
}