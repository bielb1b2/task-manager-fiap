import type { ComponentProps } from "react";
import { twMerge } from "tailwind-merge";
import { Chechbox } from "./chebox";
import { PencilLine, Trash } from "lucide-react";
import type { ITask } from "../input/ITask";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { removeTask } from "../api/removeTask";
import { useUser } from "../hooks/useUser";
import toast from "react-hot-toast";
import { finishTask } from "../api/finishTask";

interface CardBoxProps extends ComponentProps<"span"> {
    task: ITask
}

export function CardBox({ task, className, ...props }: CardBoxProps) {

    const { userId } = useUser();
    const queryClient = useQueryClient();

    const mutation = useMutation({
        mutationFn: async () =>{ 
            const response = await removeTask(userId!, task.uuid)
            response ? toast.success("Task removed") : toast.error("Task could not be removed")
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["tasks"] })
        }
    })

    const mutationFinish = useMutation({
        mutationFn: async () =>{ 
            const response = await finishTask(userId!, task.uuid)
            response ? toast.success("Task updated") : toast.error("Task could not be updated")
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["tasks"] })
        }
    })

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
                <Chechbox onCheckedChange={() => {
                    mutationFinish.mutate()
                }} />
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
                    <div className="flex items-center gap-1 mt-4">
                        <span className="text-sm text-zinc-500 underline underline-offset-4 leading-none">
                            {task.uuid}
                        </span>

                        <span className="text-zinc-500"> • </span>

                        <span className="text-sm text-zinc-500 underline underline-offset-4 leading-none">
                            {task.createdAt}
                        </span>
                    </div>
                </div>
            </div>

            <div className="flex gap-2 items-center justify-center">
            <PencilLine 
                size={20} 
                className="opacity-0 group-hover:opacity-100 transition-opacity duration-200 cursor-pointer"  
                onClick={() => {
                    console.log("Not implemented")
                }}
            />

            <Trash 
                size={20} 
                className="opacity-0 group-hover:opacity-100 transition-opacity duration-200 cursor-pointer"  
                onClick={() => {
                    mutation.mutate()
                }}
            />
            </div>
        </span>
    )
}