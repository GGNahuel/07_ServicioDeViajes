import { css, SerializedStyles } from "@emotion/react";
import { ReactNode } from "react";

export function MainSection({children, id, styles, variant} 
  : {children: ReactNode, id?: string, styles?: SerializedStyles, variant?: "row"}
) {
  const style = css`
    width: 100%;
    max-width: 1136px;
    box-sizing: border-box;
    padding: 1rem;

    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: ${variant == "row" || variant == "padding-row" ? "row" : "column"};

    ${styles || ""}
  `

  return (
    <section id={id} css={style}>
      {children}
    </section>
  )
}