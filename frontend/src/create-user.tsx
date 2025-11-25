import type { ComponentProps } from "react";
import { twMerge } from "tailwind-merge";

interface CreateUserProps extends ComponentProps<"button"> { }

export function CreateUser({ className, ...props}: CreateUserProps) {
    return (
        <button className={twMerge(
            "w-full mt-4 py-4 px-1 border-2 bg-zinc-50 rounded-md",
            "text-zinc-900 font-bold text-lg",
            "hover:opacity-80 transition-opacity duration-200 cursor-pointer",
            className
        )}
        {...props}
        >
            Create TaskManager
        </button>
    )
}