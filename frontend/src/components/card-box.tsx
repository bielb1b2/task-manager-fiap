import type { ComponentProps } from "react";
import { twMerge } from "tailwind-merge";
import { Chechbox } from "./chebox";
import { Trash } from "lucide-react";

interface CardBoxProps extends ComponentProps<"span"> {
    cardTitle: String
    description: String
}

export function CardBox({ className, cardTitle, description, ...props }: CardBoxProps) {
    return (
        <span
            className={twMerge(
                "flex w-full justify-between items-center py-1 px-3 rounded-xl mt-6",
                "border-2 border-solid",
                className
            )}
            {...props}
        >
            <div className="flex w-full items-center">
                <Chechbox />
                <div className="flex flex-col ml-4">
                    <span className="text-lg leading-tight">{cardTitle}</span>
                    <span className="text-sm leading-tight text-zinc-200">{description}</span>
                </div>
            </div>

            <Trash size={20} />
        </span>
    )
}