import { css } from "@emotion/react";
import { List } from "./List";
import { Next, Previous } from "./SvgIcons";
import { useEffect, useRef, useState } from "react";

export function Carrousel_Basic({imagesProps}: {imagesProps: {src: string, alt?: string}[]}) {
  const [selectedImg, setSelected] = useState<number>(0)
  const [carrouselWidth, setCarrouselWidth] = useState<number>(0)
  const carrouselRef = useRef<HTMLDivElement>(null)
  
  const style = css`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    box-sizing: border-box;
    overflow: hidden;

    > div {
      display: flex;
      flex-wrap: no-wrap;
      align-items: center; 
    }

    .imagesContainer {
      translate: ${carrouselWidth * selectedImg * (-1)}px;
      transition: translate 300ms;
        
      img {
        width: ${carrouselWidth}px;
      }
    }

    .buttonsZone {
      width: 100%;
      justify-content: center;
      gap: 24px;

      svg, input {
        cursor: pointer;
      }

      svg:hover {
        scale: 1.3;
        transition: scale 300ms ease;
      }
    }
  `

  useEffect(() => {
    const handleResize = () => {
      if (carrouselRef.current == null) return

      setCarrouselWidth(carrouselRef.current.clientWidth)
    }
    handleResize()

    window.addEventListener("resize", handleResize)

    return () => {
      window.removeEventListener("resize", handleResize)
    }
  }, [])

  const handleChangeImg = (newValue: number, isRelativeValue: boolean) => {
    const finalValue = 
      !isRelativeValue ?
        newValue >= imagesProps.length ? imagesProps.length-1 : 
        newValue < 0 ? 0 :
        newValue
      : (selectedImg + newValue) >= imagesProps.length ?
        0
      : (selectedImg + newValue) < 0 ?
        imagesProps.length-1
      : selectedImg + newValue

    setSelected(finalValue)
  }

  return (
    <div css={style} ref={carrouselRef}>
      <div className="imagesContainer">
        {imagesProps.map((image, index) => <img key={index} src={image.src} alt={image.alt} />)}
      </div>
      <div className="buttonsZone">
        <Previous onClick={() => handleChangeImg(-1, true)}/>
        <List variant={"inline"}>
          {imagesProps.map((_, index) => 
            <li key={index}><input type="radio" name="carrousel" value={index} checked={selectedImg == index} onChange={() => handleChangeImg(index, false)}/></li>
          )}
        </List>
        <Next onClick={() => handleChangeImg(1, true)}/>
      </div>
    </div>
  )
}