import { css, SerializedStyles } from "@emotion/react";
import { ButtonHTMLAttributes, ReactNode } from "react";

export function Button(
  {variant, children, additionalStyles, otherType, onClick} : 
  {variant: "default" | "main" | "secondary", children: ReactNode, 
    additionalStyles?: SerializedStyles, otherType?: ButtonHTMLAttributes<HTMLButtonElement>["type"],
    onClick?: () => void
  }
) {
  const style = css`
    appearance: none;
    padding: 0.6rem;
    font-size: 1.05rem;
    border-radius: 8px;
    border: 1px solid rgb(115, 115, 115);
    ${variant == "default" ? "background-color: rgb(224, 233, 255);" 
      : variant == "main" ? "background-color: var(--mainColor);" 
    : ""}

    &:hover {
      ${variant == "default" ?
        "background-color: rgb(192, 203, 232)" : 
        variant == "main" ?
          "box-shadow: 3px 1px 4px rgba(79, 79, 100, 0.4), -3px 3px 4px rgba(79, 79, 100, 0.4)" : 
        ""
      };
      cursor: pointer;
      transition: all 200ms;
    }

    ${additionalStyles || ""}
  `

  return (
    <button css={style} type={otherType || "button"} onClick={onClick}>
      {children}
    </button>
  )
}