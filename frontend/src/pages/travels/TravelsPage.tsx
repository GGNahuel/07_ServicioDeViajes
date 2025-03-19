import { css } from "@emotion/react";
import { useClick, useDismiss, useFloating, useInteractions } from "@floating-ui/react";
import { ReactNode, useState } from "react";
import { Card } from "../../components/Card";
import { MainSection } from "../../components/MainSection";
import { useTravelSearcher } from "../../requests/TravelRequests";
import { Travel } from "../../types/ApiTypes";
import { TravelsSearchForm } from "./sections/TravelsSearchForm";
import { Button } from "../../components/Button";
import { Close } from "../../components/SvgIcons";
import { useWindowProps } from "../../hooks/useWindowsProps";
import { generateImageURL } from "../../utils/generateImageUrlFromAPI";

export function TravelsPage() {
  const {response, makeRequest} = useTravelSearcher()
  const [selectedTravel, setSelectedTravel] = useState<Travel>()
  const [isModalOpen, setIsModalOpen] = useState(false)

  const {context} = useFloating({
    open: isModalOpen,
    onOpenChange: setIsModalOpen,
  });

  const click = useClick(context);
  const dismiss = useDismiss(context);
  const {getReferenceProps, getFloatingProps} = useInteractions([click, dismiss]);

  const handleOnClickInTravelCard = (travel: Travel) => {
    setSelectedTravel(travel)
    setIsModalOpen(true)
  }

  const {width} = useWindowProps()

  const modalStyles = css`
    background-color: rgb(220,220,220);
    border: 2px solid black;
    position: fixed;
    top: 50%;
    transform: translateY(-50%);
    z-index: 15;
    padding: 1rem;
    width: 100%;
    max-width: 1136px;
    border-radius: 8px;

    > header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 1rem;
    
      > div {
        display: flex;
        gap: 8px;
        flex-wrap: wrap;
        align-items: baseline;
      }
    }

    > section {
      display: flex;
      justify-content: center;
      ${width < 720 ? "flex-direction: column;" : ""}
      gap: 1rem; 
    }
  `

  const travelCardStyles = css`
    img {
      width: 100%;
      max-width: 250px;
      aspect-ratio: 8/6;
      background-size: cover;
      background-position: center;
    }
  `

  return (
    <main>
      <TravelsSearchForm makeRequest={makeRequest} />
      <MainSection variant="row" styles={css`
        gap: 1rem;
        flex-wrap: wrap;
        align-items: stretch;
      `}>    
        {response?.map(travel => 
          <Card key={travel.id} whenClicked={() => handleOnClickInTravelCard(travel)} additionalStyles={travelCardStyles} {...getReferenceProps()}>
            {travel.images[0] && <img src={generateImageURL(travel.images[0])} alt={travel.images[0].name} />}
            <h3>{travel.name}</h3>
            <h4>{travel.rating}</h4>
            <h4>Destinos</h4>
            <ul>
              {travel.destinies.map(destiny => <li key={destiny.place}>{destiny.place}</li>)}
            </ul>
            <h4>Fechas disponibles:</h4>
            <ul>
              {travel.availableDates.map(date => <li key={date}>{date}</li>)}
            </ul>
            <h4>{travel.isAvailable ? "Disponible para la próxima fecha" : "No disponible"}</h4>
            <h4>Capacidad actual:{travel.currentCapacity}</h4>
            <h4>Capacidad máxima: {travel.maxCapacity}</h4>
          </Card>
        )}
        {isModalOpen && <section css={modalStyles} {...getFloatingProps()}>
          <header>
            <div>
              <h3>{selectedTravel?.name}</h3>
              <h4>{selectedTravel?.rating} (Valoraciones: {selectedTravel?.reviews?.length})</h4>
            </div>
            <Button variant="default" onClick={() => setIsModalOpen(false)}><Close /></Button>
          </header>
          <section>
            <div>
              carrousel de imagenes con botones para desplazar
            </div>
            <div className="info">
              <h4>Destinos</h4>
              <ul>
                {selectedTravel?.destinies.map(destiny => <li key={destiny.place}>{destiny.place}</li>)}
              </ul>
              <h4>Fechas disponibles:</h4>
              <ul>
                {selectedTravel?.availableDates.map(date => <li key={date}>{date}</li>)}
              </ul>
              <h4>{selectedTravel?.isAvailable ? "Disponible para la próxima fecha" : "No disponible"}</h4>
              <h4>Capacidad actual:{selectedTravel?.currentCapacity}</h4>
              <h4>Capacidad máxima: {selectedTravel?.maxCapacity}</h4>
              <h4>Días de duración en total: {selectedTravel?.longInDays}</h4>
              <h4>Planes disponibles:</h4>
              <ul>
                {selectedTravel?.payPlans.map(plan => <li key={plan.planFor}>{plan.planFor}: ${plan.price}</li>)}
              </ul>
            </div>
          </section>
        </section>}
      </MainSection>
    </main>
  )
}

export function Modal({children} : {children: ReactNode}) {
  return (
    <section>
      {children}
    </section>
  )
}