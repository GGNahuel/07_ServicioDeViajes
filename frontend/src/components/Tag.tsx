import { css, SerializedStyles } from "@emotion/react";
import { ReactNode } from "react";

export function Tag({children, variant, additionalStyles} : {children: ReactNode, variant?: "default" | "empty", additionalStyles?: SerializedStyles}) {
  const style = css`
    padding: 0.2rem 0.8rem;
    font-weight: 600;
    border-radius: 16px;
    display: flex;
    align-items: center;
    ${variant === "empty" ? 
      "border: 1px solid rgb(10, 110, 110);" : 
      "background-color: var(--mainColor); color: white;"
    }

    ${additionalStyles}
  `
  
  return (
    <span css={style}>
      {children}
    </span>
  )
}