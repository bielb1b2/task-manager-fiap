import { uuidv7 } from "uuidv7"

import { twMerge } from "tailwind-merge"
import { CardList } from "./card-list"
import { CreateUser } from "./create-user"
import { useUser } from "./hooks/useUser"
import { UserDisplay } from "./components/user-display"
import { Toaster } from "react-hot-toast"
import { CreateTask } from "./create-task"
import { QueryClientProvider } from "@tanstack/react-query"
import { queryClient } from "./lib/query-client"
import StarfieldBackground from "./components/starfield-background"

function App() {
  const { userId, setUser } = useUser()

  const createNewUser = () => {
    setUser(uuidv7().toString())
  }

  return (
    <QueryClientProvider client={queryClient}>
      <StarfieldBackground />
      <Toaster 
        position="top-right"
      />
      <main className="flex flex-col h-screen w-screen p-1 items-center relative">
        <section className={twMerge(
          "flex flex-col w-[640px] items-center mt-10 p-4 max-h-full overflow-y-auto",
          "border-2 rounded-lg border-zinc-700 shadow"
        )}>
          <h1 className="text-4xl font-bold bg-gradient-to-r from-blue-400 via-purple-400 to-pink-400 bg-clip-text text-transparent animate-pulse">
            TaskManager Starfield
          </h1>

          {userId == null ?  <CreateUser onClick={createNewUser} />
            :
            <>
              <UserDisplay userId={userId} />
              <CreateTask />
              <CardList />
            </>
          }
        </section>
          
      </main>
    </QueryClientProvider>
  )
}

export default App