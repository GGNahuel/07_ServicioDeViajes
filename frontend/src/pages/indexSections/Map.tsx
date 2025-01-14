import { FloatingPortal, offset, shift, size, useFloating, useFocus, useHover, useInteractions } from "@floating-ui/react";
import { CSSProperties, useState } from "react";
import { LocationSvg } from "../../components/SvgIcons";

export type mapPlaceType = {
  pinPositionPercentage: {x: number, y: number},
  name: string,
  text: string,
  hotels: {name: string, img: string, rating: number}[]
}

const mapPlaces: mapPlaceType[] = [
  {
    pinPositionPercentage: {x: 55, y: 24},
    name: "Paris, France",
    text: "The city of love, known for its iconic Eiffel Tower, art museums like the Louvre, and charming streets.",
    hotels: [
      { name: "Hôtel Ritz Paris", img: "https://example.com/ritz-paris.jpg", rating: 5 },
      { name: "Le Bristol Paris", img: "https://example.com/bristol-paris.jpg", rating: 5 },
      { name: "Hôtel de Crillon", img: "https://example.com/crillon.jpg", rating: 4.8 },
    ],
  },
  {
    pinPositionPercentage: {x: 15, y: 14},
    name: "New York City, USA",
    text: "The city that never sleeps, featuring Times Square, Central Park, and the Statue of Liberty.",
    hotels: [
      { name: "The Plaza", img: "https://example.com/the-plaza.jpg", rating: 5 },
      { name: "The St. Regis New York", img: "https://example.com/st-regis.jpg", rating: 4.9 },
      { name: "The Langham", img: "https://example.com/langham.jpg", rating: 4.8 },
    ],
  },
  {
    pinPositionPercentage: {x: 80, y: 50},
    name: "Tokyo, Japan",
    text: "A bustling metropolis blending traditional culture and modern technology, famous for its cherry blossoms and Shibuya Crossing.",
    hotels: [
      { name: "Aman Tokyo", img: "https://example.com/aman-tokyo.jpg", rating: 5 },
      { name: "Park Hyatt Tokyo", img: "https://example.com/park-hyatt.jpg", rating: 4.9 },
      { name: "Shangri-La Tokyo", img: "https://example.com/shangri-la.jpg", rating: 4.8 },
    ],
  },
  {
    pinPositionPercentage: {x: 58, y: 45},
    name: "Rome, Italy",
    text: "The Eternal City, home to the Colosseum, Vatican City, and delicious Italian cuisine.",
    hotels: [
      { name: "Hotel Eden", img: "https://example.com/hotel-eden.jpg", rating: 5 },
      { name: "Hotel de Russie", img: "https://example.com/hotel-russie.jpg", rating: 4.9 },
      { name: "Palazzo Naiadi", img: "https://example.com/palazzo-naiadi.jpg", rating: 4.8 },
    ],
  },
  {
    pinPositionPercentage: {x: 70, y: 70},
    name: "Sydney, Australia",
    text: "Known for its Sydney Opera House, Harbour Bridge, and stunning beaches like Bondi.",
    hotels: [
      { name: "Park Hyatt Sydney", img: "https://example.com/park-hyatt-sydney.jpg", rating: 5 },
      { name: "Shangri-La Sydney", img: "https://example.com/shangri-la-sydney.jpg", rating: 4.9 },
      { name: "The Langham Sydney", img: "https://example.com/langham-sydney.jpg", rating: 4.8 },
    ],
  },
  {
    pinPositionPercentage: {x: 55, y: 84},
    name: "Cape Town, South Africa",
    text: "Famous for Table Mountain, Robben Island, and its vibrant cultural scene.",
    hotels: [
      { name: "One&Only Cape Town", img: "https://example.com/one-and-only.jpg", rating: 5 },
      { name: "Cape Grace Hotel", img: "https://example.com/cape-grace.jpg", rating: 4.9 },
      { name: "The Silo Hotel", img: "https://example.com/silo-hotel.jpg", rating: 4.8 },
    ],
  },
  {
    pinPositionPercentage: {x: 65, y: 34},
    name: "Bangkok, Thailand",
    text: "A city with bustling markets, grand temples like Wat Arun, and vibrant nightlife.",
    hotels: [
      { name: "Mandarin Oriental Bangkok", img: "https://example.com/mandarin-oriental.jpg", rating: 5 },
      { name: "The Peninsula Bangkok", img: "https://example.com/peninsula-bangkok.jpg", rating: 4.9 },
      { name: "Shangri-La Bangkok", img: "https://example.com/shangri-la-bangkok.jpg", rating: 4.8 },
    ],
  },
  {
    pinPositionPercentage: {x: 85, y: 34},
    name: "Dubai, UAE",
    text: "A city of luxury and innovation, home to the Burj Khalifa and stunning desert landscapes.",
    hotels: [
      { name: "Burj Al Arab", img: "https://example.com/burj-al-arab.jpg", rating: 5 },
      { name: "Atlantis The Palm", img: "https://example.com/atlantis.jpg", rating: 4.9 },
      { name: "Jumeirah Emirates Towers", img: "https://example.com/emirates-towers.jpg", rating: 4.8 },
    ],
  },
  {
    pinPositionPercentage: {x: 35, y: 64},
    name: "Rio de Janeiro, Brazil",
    text: "Known for its iconic Christ the Redeemer statue, Copacabana Beach, and vibrant Carnival.",
    hotels: [
      { name: "Copacabana Palace", img: "https://example.com/copacabana-palace.jpg", rating: 5 },
      { name: "Fasano Rio de Janeiro", img: "https://example.com/fasano.jpg", rating: 4.9 },
      { name: "Emiliano Rio", img: "https://example.com/emiliano.jpg", rating: 4.8 },
    ],
  },
  {
    pinPositionPercentage: {x: 95, y: 14},
    name: "Bali, Indonesia",
    text: "A tropical paradise with beautiful beaches, temples, and lush greenery.",
    hotels: [
      { name: "Four Seasons Resort Bali", img: "https://example.com/four-seasons-bali.jpg", rating: 5 },
      { name: "Ayana Resort Bali", img: "https://example.com/ayana.jpg", rating: 4.9 },
      { name: "Mandapa, a Ritz-Carlton Reserve", img: "https://example.com/mandapa.jpg", rating: 4.8 },
    ],
  },
]

export function Map() {
  return (
    <section id="mapSection">
      <header>
        <h2>Mapa de lugares</h2>
        <a href=""><button>a página de viajes parte de búsqueda</button></a>
      </header>
      <section id="map">
        <img src="/map.png" alt="" />
        {mapPlaces.map(place => (
          <PlacePin place={place} />
        ))}
      </section>
    </section>
  )
}

function HoveredMapPlace({place, floatingStyles, ...otherProps} : {place: mapPlaceType, floatingStyles: CSSProperties, otherProps: unknown}) {
  return (
    <article className="hoveredPlace" {...otherProps} style={{
      ...floatingStyles,
    }}>
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
    </article>
    )
}

function PlacePin({place} : {place: mapPlaceType}) {
  const [isOpen, setIsOpen] = useState(false)
  
  const {refs, context, floatingStyles} = useFloating({
    open: isOpen,
    onOpenChange: setIsOpen,
    placement: "bottom-start",
    middleware: [shift({padding: 8}), offset(8), size()],
  })

  const onHover = useHover(context)
  const onFocus = useFocus(context)

  const {getReferenceProps, getFloatingProps} = useInteractions([onHover, onFocus])

  return (
    <>
      <div className="pin" 
        ref={refs.setReference}
        style={{
          left: place.pinPositionPercentage.x + "%",
          top: place.pinPositionPercentage.y + "%"
        }}
        {...getReferenceProps()}
      >
        <LocationSvg />
      </div>
      {isOpen && (<FloatingPortal>
          <HoveredMapPlace place={place} floatingStyles={floatingStyles} otherProps={{ref: refs.setFloating, ...getFloatingProps()}} />
        </FloatingPortal>)
      }
    </>
  )
}