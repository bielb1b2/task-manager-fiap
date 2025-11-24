import * as RadixCheckbox from "@radix-ui/react-checkbox"
import { CheckIcon } from "lucide-react"
import { twMerge } from "tailwind-merge"

interface CheckboxProps extends RadixCheckbox.CheckboxProps {

}

export function Chechbox(props: CheckboxProps) {    
    return (
        <RadixCheckbox.Root
            className={twMerge(
                "flex size-4 shrink-0 items-center justify-center rounded border boder-zinc-600 bg-zinc-800 transition-colors hover:border-zinc-500",
                "focus-visible:ouline-none focus-visible:ring-2 focus-visible:ring-zinc-400 focus-visible:ring-offset-2",
                "data-[state=unchecked]:border-zinc-50 data-[state=checked]:bg-zinc-400"
            )}
            {...props}
        >
            <RadixCheckbox.Indicator className="flex items-center justify-center text-zinc-900">
                <CheckIcon className="size-3" strokeWidth={3} />
            </RadixCheckbox.Indicator>
        </RadixCheckbox.Root>
    )
}
