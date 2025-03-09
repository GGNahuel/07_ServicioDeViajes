import { css, SerializedStyles } from "@emotion/react";
import { ReactNode, useState } from "react";

export function Card({children, additionalStyles, onHover} : {children: ReactNode, additionalStyles?: SerializedStyles, onHover?: () => void}) {
  const [isHovered, setIsHovered] = useState(false)
  const style = css`
    box-shadow: ${!isHovered ? 
      "3px 3px 3px rgba(57, 57, 57, 0.5), -1px -1px 3px rgba(57, 57, 57,0.5)" 
      : "5px 5px 6px 2px rgba(57, 57, 57,0.4), -1px -1px 6px 1px rgba(57, 57, 57,0.4)"};
    border-radius: 16px;
    padding: 1rem;
    transition: box-shadow 100ms;
    ${additionalStyles || ""}
  `

  return (
    <article 
      onMouseEnter={() => {
        if (onHover) onHover()
        setIsHovered(true)
      }} 
      onMouseLeave={
        () => setIsHovered(false)
      }
      css={style}
    >
      {children}
    </article>
  )
}