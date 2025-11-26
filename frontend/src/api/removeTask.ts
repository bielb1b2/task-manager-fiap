import { api } from "../lib/api";

export async function removeTask(userId: string, taskId: string): Promise<boolean> {
  if (!userId) return false;

  const response = await api.delete(`api/task/${userId}/${taskId}`)
  if(response.status !== 204) return false;
  return true
}