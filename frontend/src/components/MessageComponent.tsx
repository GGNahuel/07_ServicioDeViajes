import { css } from "@emotion/react"
import { Close } from "./SvgIcons"
import { messageType } from "../types/UtilsTypes"


export function MessageComponent({message, messageType, isOverlapping} : {message: string, messageType: messageType, isOverlapping: boolean}) {
  const style = css`
    width: 100%;
    max-width: 700px;
    border-radius: 8px;
    padding: 1rem;
    font-size: 1.1rem;
    color: white;
    background-color: ${
      messageType == "success" ? "rgb(22, 122, 29)" :
      messageType == "error" ? "rgb(122,22,29)" :
      messageType == "warn" ? "rgb(114, 122, 22)" : ""
    };
    
    > div {
      width: 100%;
      position: relative; 

      svg {
        position: absolute; 
        top: -0.5rem;
        right: -0.5rem;
        width: 20px;
        aspect-ration: 1;
        border: 2px solid rgb(100,100,100);
        border-radius: 8px;
      }
    }
  `
  
  return messageType != "null" && (
    <div css={style}>
      <div>
        <Close />
        <p>{message}</p>
      </div>
    </div>
  )
}