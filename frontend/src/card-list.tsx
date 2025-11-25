import { useEffect, useState, type ComponentProps } from "react";
import { CardBox } from "./components/card-box";
import { twMerge } from "tailwind-merge";
import type { ITask } from "./input/ITask";


interface CardListProps extends ComponentProps<"div"> {}


const data: ITask[] = [
    {
        uuid: "695876859685765",
        title: "Task 1",
        description: "Description 1",
        finished: false,
        createdAt: new Date().toString()
    },
    {
        uuid: "3423423423432",
        title: "Task 2",
        description: "Description 2",
        finished: true,
        createdAt: new Date().toString()
    }
]



export function CardList({ className, ...props }: CardListProps) {

    const [tasks, setTasks] = useState<ITask[]>([])


    useEffect(() => {
        setTasks(data)
    }, [tasks, data])

    return(
        <div className={twMerge(
            "flex flex-col gap-3 mt-4 w-full",
            className
        )} 
        {...props}
        >

            {tasks.map((task, index) => 
                <CardBox
                    key={index}
                    task={task}
                />
            )}

        </div>
    )
}