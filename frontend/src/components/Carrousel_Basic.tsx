import { css } from "@emotion/react";
import { List } from "./List";
import { Next, Previous } from "./SvgIcons";
import { ReactNode, useEffect, useRef, useState } from "react";
import { Button } from "./Button";

export function Carrousel_Basic(
  {children, listLength, canGoNext, canGoPrevious, nextButton, prevButton} : 
  {children: ReactNode, listLength: number, canGoNext?: boolean, canGoPrevious?: boolean, nextButton?: ReactNode, prevButton?: ReactNode}
) {
  const [selectedITem, setSelected] = useState<number>(0)
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

    .itemContainer {
      translate: ${carrouselWidth * selectedITem * (-1)}px;
      transition: translate 300ms;
        
      > * {
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

  const handleChangeItem = (newValue: number, isRelativeValue: boolean) => {
    const finalValue = 
      !isRelativeValue ?
        newValue >= listLength ? listLength - 1 : 
        newValue < 0 ? 0 :
        newValue
      : (selectedITem + newValue) >= listLength ?
        0
      : (selectedITem + newValue) < 0 ?
        listLength - 1
      : selectedITem + newValue

    setSelected(finalValue)
  }

  return (
    <div css={style} ref={carrouselRef}>
      <div className="itemContainer">
        {children}
      </div>
      <div className="buttonsZone">
        <Button variant="secondary-noBorder" onClick={() => handleChangeItem(-1, true)} disabled={canGoPrevious !== false}>
          {prevButton || <Previous/>}
        </Button>
        <List variant={"inline"}>
          {Array.from({length: listLength}).map((_, index) => 
            <li key={index}><input type="radio" name="carrousel" value={index} checked={selectedITem == index} onChange={() => handleChangeItem(index, false)}/></li>
          )}
        </List>
        <Button variant={nextButton ? "default" : "secondary-noBorder"} onClick={() => handleChangeItem(1, true)} disabled={canGoNext !== false}>
          {nextButton || <Next/>}
        </Button>
      </div>
    </div>
  )
}