import { createContext, Dispatch, ReactNode, SetStateAction, useState } from "react"
import { messageType } from "../types/UtilsTypes";

export interface MessageContextInterface {
  readonly value: {
    message: string,
    type: messageType
  },
  readonly setValue: Dispatch<SetStateAction<{
    message: string;
    type: messageType;
  }>>
}

export const MessageContext = createContext<MessageContextInterface | undefined>(undefined)

export function MessageContextProvider({children} : {children: ReactNode}) {
  const [value, setValue] = useState<MessageContextInterface["value"]>({
    message: "", type: "null"
  })

  return (
    <MessageContext.Provider value={{value, setValue}}>
      {children}
    </MessageContext.Provider>
  )
}
