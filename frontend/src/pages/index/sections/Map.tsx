import { offset, shift, size, useFloating, useFocus, useHover, useInteractions } from "@floating-ui/react";
import { useState } from "react";
import { LocationSvg } from "../../../components/SvgIcons";
import mapPlaces from "../../../const/mapPlaces";
import { Button } from "../../../components/Button";
import { MainSection } from "../../../components/MainSection";
import { css } from "@emotion/react";

export type mapPlaceType = {
  pinPositionPercentage: {x: number, y: number},
  name: string,
  text: string,
  hotels: {name: string, img: string, rating: number}[]
}

export function Map() {
  const [hoveredPin, setHoveredPin] = useState<mapPlaceType | null>(null)

  const mapStyles = css`
    width: 100%;
    position: relative;

    img {
      max-width: 100%;
    }

    .pin {
      display: flex;
      position: absolute;
      transform: translate(-50%, -100%);
    }
  `

  return (
    <MainSection id="mapSection">
      <header>
        <h2>Mapa de lugares</h2>
        <a href=""><Button variant="default">a página de viajes parte de búsqueda</Button></a>
      </header>
      <section id="map" css={mapStyles}>
        <img src="/map.jpg" alt="" />
        {mapPlaces.map(place => (
          <PlacePin key={place.name} place={place} isHovered={hoveredPin?.name == place.name} onHover={setHoveredPin} />
        ))}
      </section>
    </MainSection>
  )
}

function PlacePin({place, isHovered, onHover} : {
    place: mapPlaceType, isHovered: boolean, 
    onHover: (place: mapPlaceType | null) => void
  }
) {
  const {refs, context, floatingStyles} = useFloating({
    open: isHovered,
    onOpenChange: (open) => onHover(open? place : null),
    placement: "bottom-start",
    middleware: [
      shift({padding: 16}), offset(4), 
      size({apply({ availableWidth, elements }) {
        elements.floating.style.maxWidth = `${availableWidth}px`;
      }})
    ],
  })

  const byHover = useHover(context)
  const byFocus = useFocus(context)

  const {getReferenceProps, getFloatingProps} = useInteractions([byHover, byFocus])
  
  const style = css`
    left: ${place.pinPositionPercentage.x}%;
    top: ${place.pinPositionPercentage.y}%;
  `

  return (
    <>
      <div className="pin" 
        ref={refs.setReference}
        css={style}
        {...getReferenceProps()}
      >
        <LocationSvg />
      </div>

      {isHovered && <article className="hoveredPlace" 
        ref={refs.setFloating} 
        {...getFloatingProps()} 
        style={{
          ...floatingStyles,
        }}
      >
        <div>
          <img src="" alt="" />
          <h3>{place.name}</h3>
        </div>
        <div>
          <p>{place.text}</p>
          <ul>
            {place.hotels.map(hotel => (
              <li key={hotel.name}>
                <p>{hotel.name}</p>
                <img src={hotel.img} alt="" />
              </li>
            ))}
          </ul>
        </div>
      </article>}
    </>
  )
}