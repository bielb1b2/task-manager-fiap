import { Search } from "lucide-react";
import type { ComponentProps } from "react";

interface CreateTaskProps extends ComponentProps<"div"> {}


export function CreateTask() {
  return (
    <div className="flex w-full items-center mt-4 border-zinc-200 rounded">
      <input
        placeholder="Comprar ovo..."
        className="flex w-full border-2 border-zinc-200 p-2 rounded-l-md"
      />

      <button
        className="
          flex items-center justify-center
          bg-zinc-200 hover:bg-zinc-300 active:bg-zinc-400
          transition-colors
          rounded-r-sm
          p-2
          h-full
        "
      >
        <Search size={20} className="text-zinc-600 hover:opacity-50 cursor-pointer" />
      </button>
    </div>
  );
}