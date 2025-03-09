import { css, SerializedStyles } from "@emotion/react";
import { ReactNode } from "react";

export function Card({children, additionalStyles, onHover} : {children: ReactNode, additionalStyles?: SerializedStyles, onHover?: () => void}) {
  const style = css`
    box-shadow: 3px 3px 3px rgba(57, 57, 57, 0.5), -1px -1px 3px rgba(57, 57, 57,0.5);
    border-radius: 16px;
    padding: 1rem;
    transition: box-shadow 100ms;

    &:hover {
      box-shadow: 5px 5px 6px 2px rgba(57, 57, 57,0.4), -1px -1px 6px 1px rgba(57, 57, 57,0.4);
    }

    ${additionalStyles || ""}
  `

  return (
    <article 
      onMouseEnter={() => {
        if (onHover) onHover()
      }} 
      css={style}
    >
      {children}
    </article>
  )
}