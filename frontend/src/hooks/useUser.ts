import { create } from "zustand";
import { persist } from "zustand/middleware";

interface User {
    userId: string | null;
    setUser: (userId: string) => void;
}

export const useUser = create<User>()(
    persist(
        (set) => ({
            userId: null,
            setUser: (id) => set({ userId: id })
        }),
        {
            name: "taskManager@user", // chave no localStorage
        }
    )
);