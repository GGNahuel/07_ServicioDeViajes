import { css } from "@emotion/react";
import { Button } from "../../../components/Button";
import { Close } from "../../../components/SvgIcons";
import { Travel } from "../../../types/ApiTypes";
import { formatDate } from "../../../utils/formatDateFromApi";
import { generateImageURL } from "../../../utils/generateImageUrlFromAPI";
import { useWindowProps } from "../../../hooks/useWindowsProps";

export function TravelContent({travel, handleCloseModal} : {travel: Travel, handleCloseModal?: () => void}): JSX.Element {
  const {width} = useWindowProps()
  
  const styles = css`
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
  
  return (
    <section css={styles}>
      <header>
        <div>
          <h3>{travel.name}</h3>
          <h4>{travel.rating} (Valoraciones: {travel.reviews?.length})</h4>
        </div>
        {handleCloseModal && <Button variant="default" onClick={handleCloseModal}><Close /></Button>}
      </header>
      <section>
        <div css={css`
          img {
            max-width: 300px;
          }
        `}>
          {travel.images[0] && travel.images.map(imageData => <img key={imageData.id} src={generateImageURL(imageData)} />)}
        </div>
        <div className="info">
          <h4>Destinos</h4>
          <ul>
            {travel.destinies.map(destiny => <li key={destiny.place}>{destiny.place}</li>)}
          </ul>
          <h4>Fechas disponibles:</h4>
          <ul>
            {travel.availableDates.map(date => <li key={formatDate(date)}>{formatDate(date)}</li>)}
          </ul>
          <h4>{travel.isAvailable ? "Disponible para la próxima fecha" : "No disponible"}</h4>
          <h4>Capacidad actual:{travel.currentCapacity}</h4>
          <h4>Capacidad máxima: {travel.maxCapacity}</h4>
          <h4>Días de duración en total: {travel.longInDays}</h4>
          <h4>Planes disponibles:</h4>
          <ul>
            {travel.payPlans.map(plan => <li key={plan.planFor}>{plan.planFor}: ${plan.price}</li>)}
          </ul>
        </div>
      </section>
      <section>
        info de los destinos
      </section>
      <section>
        preguntas frecuentes
      </section>
    </section>
  )
}