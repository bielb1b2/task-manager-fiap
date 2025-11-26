import type { ComponentProps } from "react";
import { Plus } from "lucide-react";
import { twMerge } from "tailwind-merge";

interface CreateTaskProps extends ComponentProps<"button"> {

}

export function CreateTask({ className, ...props }: CreateTaskProps) {
  return (
    <button className={twMerge(
      "flex justify-center items-center gap-2 bg-zinc-100 p-3 w-full rounded-lg mt-4 cursor-pointer",
      "hover:opacity-60 transition-opacity duration-200 ease-in-out",
      className
    )}
    {...props}
    >
      <span className="font-bold text-zinc-900 text-lg">New Task</span>
      <Plus className="stroke-zinc-900" size={18} strokeWidth={3} />
    </button>
  );
}