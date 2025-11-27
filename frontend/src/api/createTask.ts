import type { ITask } from "../input/ITask";
import { api } from "../lib/api";

export interface CreateTaskProps {
    userId: string
    title: string
    description: string
}

export async function createTask(task: CreateTaskProps): Promise<ITask> {
  if (!task.userId) throw new Error("Cannot perform this req without userId");

  const res = await api.post(`api/task`, {
    json: {
        personId: task.userId,
        title: task.title,
        description: task.description
    } 
  });

  const data = await res.json<ITask>();
  return data;
}