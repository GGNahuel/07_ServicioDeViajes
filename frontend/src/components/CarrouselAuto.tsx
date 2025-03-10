import { useEffect, useState } from "react";
import { MainSection } from "./MainSection";
import { css } from "@emotion/react";

export function CarrouselAuto() {
  const images = [1, 2, 3, 4, 5, 6, 7, 8, 9]

  const [containerPosition, setContainerPosition] = useState<number>(0)

  const styles = css`
    padding: 0;
    overflow-x: hidden;
    justify-content: flex-start;
    
    .carrouselContainer {
      display: flex;
      align-items: center;
    }
    
    img {
      width: 200px;
    }
  `

  useEffect(() => {
    let animationFrame: number;

    const moveCarrousel = () => {
      setContainerPosition((prev) => {
        const rem = parseFloat(getComputedStyle(document.documentElement).fontSize)
        const itemWidth = 200 + rem
        const newPosition = prev - 1
        const resetPosition = (images.length * itemWidth) * -1

        if (newPosition <= resetPosition) {
          return 0
        }

        return newPosition
      })

      animationFrame = requestAnimationFrame(moveCarrousel)
    };

    animationFrame = requestAnimationFrame(moveCarrousel)

    return () => cancelAnimationFrame(animationFrame)
  }, [images.length]);

  return (
    <MainSection variant="row" id="carrouselSection" styles={styles}>
      <div
        className="carrouselContainer"
        style={{
          transform: `translateX(${containerPosition}px)`
        }}
      >
        {[...images, ...images].map((number, index) => (
          <img
            key={index}
            src={`/carrousel/pic0${number}.webp`}
            alt={`Image ${number}`}
            style={{ width: "200px", marginRight: "1rem" }}
          />
        ))}
      </div>
    </MainSection>
  )
}
