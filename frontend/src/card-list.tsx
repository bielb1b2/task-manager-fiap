import type { ComponentProps } from "react";
import { CardBox } from "./components/card-box";
import { twMerge } from "tailwind-merge";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { getTasks } from "./api/getTasks";
import { useUser } from "./hooks/useUser";


interface CardListProps extends ComponentProps<"div"> {}



export function CardList({ className, ...props }: CardListProps) {

    const { userId } = useUser()
    
    const queryClient = useQueryClient()
    const { isPending, error, data } = useQuery({
        queryKey: ["tasks"],
        queryFn: async () => {
            return await getTasks(userId as string)
        }
    })

    if (isPending) return "Loading..."

    if (error) return "Happen an error"

    return(
        <div className={twMerge(
            "flex flex-col gap-3 mt-4 w-full",
            className
        )} 
        {...props}
        >

            {data.map((task, index) => 
                <CardBox
                    key={index}
                    task={task}
                />
            )}

        </div>
    )
}