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

  const [movingStates, setMovingStates] = useState<{
    isReturning: boolean,
    isArrowHovered: boolean
  }>({ isReturning: false, isArrowHovered: false })

  const [direction, setDirection] = useState<1 | -1>(-1)

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
      if (containerProps.position == 0) {
        setTimeout(() => {
          setMovingStates(prev => ({ ...prev, isReturning:false }))
        }, 400)
      }

      if (!movingStates.isReturning) {
        setContainerProps((prev) => {
          const rem : number = Number ((window.getComputedStyle(document.documentElement).fontSize.match(/[0-9]{1,}/) as RegExpMatchArray)[0])
          const newPosition = prev.position + direction * 1
          const lastPosition = (prev.width - prev.sectionWidth + rem*2) * -1
          const startPosition = 0

          const finalPosition = newPosition == lastPosition ? startPosition : Math.max(lastPosition, Math.min(startPosition, newPosition))

          if (finalPosition == startPosition) {
            setMovingStates(prev => ({ ...prev, isReturning: true }))
          }

          return {
            ...prev,
            position: finalPosition
          }
        })
        animationFrame = requestAnimationFrame(moveCarrousel)
      }
    }

    animationFrame = requestAnimationFrame(moveCarrousel)

    return () => cancelAnimationFrame(animationFrame)
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [movingStates.isReturning, direction])

  const handleHover = (dir: 1 | -1) => {
    setDirection(dir)
    setMovingStates(prev => ({ ...prev, isArrowHovered: true }))
  }

  const stopHover = () => {
    setDirection(-1)
    setMovingStates(prev => ({ ...prev, isArrowHovered: false }))
  }

  return (
    <section className="blank justifyStart" id="carrouselSection" ref={sectionRef}>
      <span className="left" onMouseEnter={() => handleHover(1)} onMouseLeave={stopHover}>
        L
      </span>
      <span className="right" onMouseEnter={() => handleHover(-1)} onMouseLeave={stopHover}>
        R
      </span>
      <div
        className="carrouselContainer"
        ref={containerRef}
        style={{
          transform: `translateX(${containerProps.position}px)`,
          transition: "transform 0.3s ease",
        }}
      >
        {images.map((number) => (
          <img key={number} src={`/carrousel/pic0${number}.webp`} alt={`Image ${number}`} />
        ))}
      </div>
    </section>
  );
}
