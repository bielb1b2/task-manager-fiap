import * as DialogRadix from "@radix-ui/react-dialog"
import { useEffect, useState, type ComponentProps } from "react";
import { twMerge } from "tailwind-merge";
import { PencilLine, Plus, X } from "lucide-react";
import toast from "react-hot-toast";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { InfinitySpin } from "react-loader-spinner";

import type { ITask } from "./input/ITask";
import { useUser } from "./hooks/useUser";
import { editTaskApi, type EditTaskApiProps } from "./api/editTask";


interface EditTaskProps extends ComponentProps<"button"> {
    task: ITask
}

export function EditTask({ task, className, ...props }: EditTaskProps) {

    const { userId } = useUser();
    const queryClient = useQueryClient()

    const [open, setOpen] = useState(false)
    const [title, setTitle] = useState(task.title);
    const [description, setDescription] = useState(task.description)

    const mutation = useMutation({
        mutationFn: async (payload: EditTaskApiProps) => {
            return await editTaskApi(payload)
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["tasks"] })

            setOpen(false);
            setTitle("");
            setDescription("");

            toast.success("Task created successfully.");
        },
        onError: () => {
            toast.error("Failed to create the task. Please try again.");
        }
    })

    const handleSave = () => {
        if (!userId) {
            toast.error("User ID is required.");
            return;
        }

        if (!title.trim()) {
            toast.error("A title is required.");
            return;
        }

        if (!description.trim()) {
            toast.error("A description is required.");
            return;
        }

        mutation.mutate({
            taskId: task.uuid,
            personId: userId as string,
            title: title.trim(),
            description: description.trim(),
            finished: task.finished
        });
    };

    return (
        <DialogRadix.Root open={open} onOpenChange={setOpen}>
            <DialogRadix.Trigger asChild>
                <PencilLine
                    size={20}
                    className="opacity-0 group-hover:opacity-100 transition-opacity duration-200 cursor-pointer"
                    onClick={() => {
                        console.log("Not implemented")
                    }}
                />
            </DialogRadix.Trigger>

            <DialogRadix.Portal>
                <DialogRadix.Overlay
                    className={twMerge(
                        "fixed inset-0 bg-black/60",
                        "animate-radix-overlay-in"
                    )}
                />

                <DialogRadix.Content
                    className={twMerge(
                        "fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[90vw] max-w-md max-h-[85vh] bg-white dark:bg-zinc-900",
                        "shadow-lg rounded-lg p-6",
                        "animate-radix-content-in outline-none"
                    )}
                >
                    <DialogRadix.Title className="m-0 text-zinc-900 dark:text-zinc-100 font-medium text-lg">
                        Edit Task
                    </DialogRadix.Title>

                    <DialogRadix.Description id="dialog-desc" className="mt-2 text-sm text-zinc-600 dark:text-zinc-300">
                        Stay focused. Small steps create big results.
                    </DialogRadix.Description>

                    <fieldset className="flex flex-col gap-1 mb-4 mt-4">
                        <label className=" ext-sm text-zinc-700 dark:text-zinc-300">Title</label>
                        <input
                            className="flex-1 px-3 py-2 rounded-md text-sm bg-zinc-800 border-zinc-700 border focus:outline-none focus:ring-2  focus:ring-zinc-50"
                            id="title"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                        />
                    </fieldset>

                    <fieldset className="flex flex-col gap-1 mb-4 mt-4">
                        <label className=" ext-sm text-zinc-700 dark:text-zinc-300">Description</label>
                        <input
                            className="flex-1 px-3 py-2 rounded-md text-sm bg-zinc-800 border-zinc-700 border focus:outline-none focus:ring-2  focus:ring-zinc-50"
                            id="description"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                        />
                    </fieldset>

                    <div className="mt-6 flex justify-end gap-3">
                        <DialogRadix.Close asChild>
                            <button
                                onClick={handleSave}
                                disabled={mutation.isPending}
                                className="px-4 py-2 bg-zinc-100 text-zinc-800 rounded-md text-sm font-bold hover:opacity-70 cursor-pointer duration-200"
                            >
                                {mutation.isPending ? <InfinitySpin width="200" color="#fafaf9" /> : <span>Save changes</span>}
                            </button>
                        </DialogRadix.Close>
                    </div>

                    <DialogRadix.Close asChild>
                        <button
                            aria-label="Close"
                            className={twMerge(
                                "absolute top-3 right-3 inline-flex items-center justify-center",
                                "h-9 w-9 rounded-full bg-zinc-800",
                                "hover:bg-zinc-700",
                                "focus:outline-none focus:ring-2 focus:ring-zinc-100"
                            )}
                        >
                            <X size={20} />
                        </button>
                    </DialogRadix.Close>
                </DialogRadix.Content>
            </DialogRadix.Portal>

        </DialogRadix.Root>
    )
}