import { api } from "../lib/api";

export async function finishTask(userId: string, taskId: string): Promise<boolean> {
  if (!userId) return false;

  const response = await api.post(`api/task/${userId}/${taskId}/finish`)
  if(response.status !== 200) return false;
  return true
}