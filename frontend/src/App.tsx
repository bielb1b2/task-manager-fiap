import { twMerge } from "tailwind-merge"
import { CardBox } from "./components/card-box"

function App() {

  return (
    <main className="flex flex-col h-screen w-screen items-center bg-zinc-900">
      <section className={twMerge(
        "flex flex-col w-[640px] items-center mt-10 p-4 rounded-l border-zinc-700 shadow",
        "border-2"
      )}>
        <h1 className="text-4xl font-bold">
          TaskManager AI
        </h1>

        <div className="gap-2 mt-4 w-full">

        <CardBox
          cardTitle="Lorem Ipsium"
          description="Descricao pai"
        />

        </div>
      </section>
    </main>
  )
}

export default App
