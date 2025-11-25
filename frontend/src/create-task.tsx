import { Plus } from "lucide-react";

export function CreateTask() {
  return (
    <div className="flex w-full items-center mt-4 border-zinc-200 rounded">
      <input
        placeholder="Comprar ovo..."
        className="flex w-full border-2 outline-none border-zinc-200 p-3 rounded-l-md"
      />

      <button
        className="
          flex items-center justify-center
          bg-zinc-200 hover:bg-zinc-300 active:bg-zinc-400
          transition-colors
          rounded-r-sm
          p-3
          h-full
        "
      >
        <Plus size={20} className="text-zinc-950 hover:opacity-50 cursor-pointer" />
      </button>
    </div>
  );
}