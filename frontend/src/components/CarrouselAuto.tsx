import { useState, useEffect, useRef } from "react";

export function CarrouselAuto() {
  const images = [1, 2, 3, 4, 5, 6, 7, 8, 9]
  const containerRef = useRef<HTMLDivElement>(null)
  const sectionRef = useRef<HTMLDivElement>(null)

  const [containerPosition, setContainerPosition] = useState<number>(0)

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
    <section
      className="blank justifyStart"
      id="carrouselSection"
      ref={sectionRef}
    >
      <div
        className="carrouselContainer"
        ref={containerRef}
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
    </section>
  )
}
