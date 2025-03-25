import { css } from "@emotion/react";
import { ReactNode } from "react";

export function List({children, variant} : {children: ReactNode, variant?: "inline" | "no-dots"}) {
  const style = css`
    padding-left: 1.5rem;
    ${variant && variant == "inline" && `
      padding: 0;
      list-style: none;
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      gap: 8px;
    `}
  `

  return (
    <ul css={style}>
      {children}
    </ul>
  )
}