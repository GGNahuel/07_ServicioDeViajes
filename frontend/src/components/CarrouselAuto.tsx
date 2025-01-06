import { useState, useEffect, useRef } from "react";

export function CarrouselAuto() {
  const images = [1, 2, 3, 4, 5, 6, 7, 8, 9]
  const containerRef = useRef<HTMLDivElement>(null)
  const sectionRef = useRef<HTMLDivElement>(null)

  const [containerProps, setContainerProps] = useState<{
    sectionWidth: number,
    width: number;
    position: number
  }>({ sectionWidth: 0, width: 0, position: 0 })

  const [isMoving, setIsMoving] = useState(false)

  const [direction, setDirection] = useState<1 | -1 | null>(null)

  useEffect(() => {
    const setContainerWidth = () => {
      if (containerRef.current) {
        setContainerProps((prev) => ({
          ...prev,
          sectionWidth: sectionRef.current?.offsetWidth || 0,
          width: containerRef.current?.scrollWidth || 0,
        }))
      }
    }

    setContainerWidth()
    window.addEventListener("resize", setContainerWidth)

    return () => {
      window.removeEventListener("resize", setContainerWidth)
    }
  }, [])

  useEffect(() => {
    let animationFrame: number

    const moveCarrousel = () => {
      if (isMoving && direction !== null) {
        setContainerProps((prev) => {
          const rem : number = Number ((window.getComputedStyle(document.documentElement).fontSize.match(/[0-9]{1,}/) as RegExpMatchArray)[0])
          const newPosition = prev.position + direction * 3
          const leftPosition = (prev.width - prev.sectionWidth + rem*2) * -1
          const rightPosition = 0

          return {
            ...prev,
            position: Math.max(leftPosition, Math.min(rightPosition, newPosition))
          }
        })
        animationFrame = requestAnimationFrame(moveCarrousel)
      }
    };

    animationFrame = requestAnimationFrame(moveCarrousel)

    return () => cancelAnimationFrame(animationFrame)
  }, [isMoving, direction])

  const handleHover = (dir: 1 | -1) => {
    setDirection(dir)
    setIsMoving(true)
  }

  const stopHover = () => {
    setDirection(null)
    setIsMoving(false)
  }

  return (
    <section className="blank justifyStart" id="carrouselSection" ref={sectionRef}>
      <span className="left" onMouseEnter={() => handleHover(-1)} onMouseLeave={stopHover}>
        L
      </span>
      <span className="right" onMouseEnter={() => handleHover(1)} onMouseLeave={stopHover}>
        R
      </span>
      <div
        className="carrouselContainer"
        ref={containerRef}
        style={{
          transform: `translateX(${containerProps.position}px)`,
          transition: isMoving ? "none" : "transform 0.3s ease-out",
        }}
      >
        {images.map((number) => (
          <img key={number} src={`/carrousel/pic0${number}.webp`} alt={`Image ${number}`} />
        ))}
      </div>
    </section>
  );
}
