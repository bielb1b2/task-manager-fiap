import { create } from "zustand";
import type { ITask } from "../input/ITask";

interface Tasks {
    tasks: ITask[] | null;
    setTasks: (Tasks: ITask[]) => void;
}

export const useTask = create<Tasks>()((set) => ({
    tasks: null,
    setTasks: (tasks) => set(() => ({ tasks }))
}));