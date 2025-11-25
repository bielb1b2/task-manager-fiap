import type { ComponentProps } from "react";
import { twMerge } from "tailwind-merge";
import { Chechbox } from "./chebox";
import { Trash } from "lucide-react";
import type { ITask } from "../input/ITask";

interface CardBoxProps extends ComponentProps<"span"> {
    task: ITask
}

export function CardBox({ task, className, ...props }: CardBoxProps) {
    return (
        <span
            className={twMerge(
                "group flex w-full justify-between items-center py-1 px-3 rounded-xl",
                "border-2 border-solid",
                className
            )}
            {...props}
        >
            <div className="flex w-full items-center">
                <Chechbox />
                <div className="flex flex-col ml-4">
                    <span className={twMerge(
                        "text-lg leading-tight",
                        task.finished && "line-through text-zinc-500"
                    )}>
                        {task.title}
                    </span>
                    <span className={twMerge(
                        "text-sm leading-tight text-zinc-300",
                        task.finished && "line-through text-zinc-500"
                    )}>
                        {task.description}
                    </span>
                    <span className="text-sm mt-4 text-zinc-500 underline underline-offset-4">{task.createdAt}</span>
                </div>
            </div>

            <Trash size={20} className="opacity-0 group-hover:opacity-100 transition-opacity duration-200 cursor-pointer" />
        </span>
    )
}