import { uuidv7 } from "uuidv7"

import { twMerge } from "tailwind-merge"
import { CardList } from "./card-list"
import { CreateUser } from "./create-user"
import { useUser } from "./hooks/useUser"
import { UserDisplay } from "./components/user-display"
import { Toaster } from "react-hot-toast"

function App() {
  const { userId, setUser } = useUser()

  const createNewUser = () => {
    setUser(uuidv7().toString())
  }

  return (
    <>
      <Toaster 
        position="top-right"
      />
      <main className="flex flex-col h-screen w-screen p-1 items-center bg-zinc-900">
        <section className={twMerge(
          "flex flex-col w-[640px] items-center mt-10 p-4 max-h-full overflow-y-auto",
          "border-2 rounded-l border-zinc-700 shadow",
          ""
        )}>
          <h1 className="text-4xl font-bold">
            TaskManager
          </h1>
          {userId && <UserDisplay userId={userId} />}


          {userId ? <CardList /> : <CreateUser onClick={createNewUser} />}
        </section>
          
      </main>
    </>
  )
}

export default App
