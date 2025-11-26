import type { ITask } from "../input/ITask";
import { api } from "../lib/api";

export async function getTasks(userId: string): Promise<ITask[]> {
  if (!userId) return [];

  const res = await api.get(`api/task/${userId}`).json<ITask[]>();
  return res;
}