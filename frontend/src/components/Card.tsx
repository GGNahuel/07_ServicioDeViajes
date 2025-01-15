import { CSSProperties, ReactNode, useState } from "react";

export function Card({children, styles, onHover} : {children: ReactNode, styles?: CSSProperties, onHover?: () => void}) {
  const [isHovered, setIsHovered] = useState(false)

  return (
    <article onMouseEnter={() => {
      if (onHover) onHover()
      setIsHovered(true)
    }} onMouseLeave={
      () => setIsHovered(false)
    } style={{
      boxShadow: !isHovered ? 
        "3px 3px 3px rgba(57, 57, 57, 0.5), -1px -1px 3px rgba(57, 57, 57,0.5)" 
        : "5px 5px 6px 2px rgba(57, 57, 57,0.4), -1px -1px 6px 1px rgba(57, 57, 57,0.4)",
      borderRadius: "16px",
      padding: "1rem",
      transition: "box-shadow 100ms",
      ...styles,
    }}>
      {children}
    </article>
  )
}