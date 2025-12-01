import type { ITask } from "../input/ITask";
import { api } from "../lib/api";

export interface EditTaskApiProps {
    taskId: string
    personId: string
    title: string
    description: string
    finished: boolean
}

export async function editTaskApi(task: EditTaskProps): Promise<ITask> {

  const res = await api.patch(`api/task/${task.personId}/${task.taskId}`, {
    json: {
        title: task.title,
        description: task.description,
        finished: task.finished
    } 
  });

  const data = await res.json<ITask>();
  return data;
}