import { css, SerializedStyles } from "@emotion/react";
import { ButtonHTMLAttributes, ReactNode } from "react";

export function Button(
  {variant, children, additionalStyles, ...attrs} : 
  {variant: "default" | "main" | "secondary" | "secondary-noBorder" | "rounded", children: ReactNode, additionalStyles?: SerializedStyles} 
  & ButtonHTMLAttributes<HTMLButtonElement>
) {
  const style = css`
    appearance: none;
    display: flex;
    padding: ${variant != "secondary-noBorder" ? "0.4rem 0.6rem" : "0"};
    font-size: 1.05rem;
    border-radius: ${
      variant != "rounded" ? "8px" :
      "100%"
    };
    border: ${
      variant != "secondary-noBorder" ? "1px solid rgb(115, 115, 115)" : 
      "0"
    };
    background-color: ${
      variant == "default" ? "rgb(224, 233, 255);" : 
      variant == "main" ? "var(--mainColor);" : 
      "rgba(0, 0, 0, 0)"
    };

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
    <button css={style} {...attrs}>
      {children}
    </button>
  )
}