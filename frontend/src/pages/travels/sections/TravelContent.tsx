import { css } from "@emotion/react";
import { Button } from "../../../components/Button";
import { List } from "../../../components/List";
import { Close } from "../../../components/SvgIcons";
import { Travel } from "../../../types/ApiTypes";
import { formatDate } from "../../../utils/formatDateFromApi";
import { generateImageURL } from "../../../utils/generateImageUrlFromAPI";
import { Carrousel_Basic } from "../../../components/Carrousel_Basic";

export function TravelContent({travel, handleCloseModal} : {travel: Travel, handleCloseModal?: () => void}): JSX.Element {
  const styles = css`
    > header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 1rem;
    
      > div {
        padding: 1rem;
        display: flex;
        gap: 8px;
        flex-wrap: wrap;
        align-items: baseline;
      }

      .icon {
        width: 24px;
        height: 24px;
      }
    }

    > section {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(500px, 1fr));
      gap: 1rem; 

      > div {
        border: 2px solid rgb(115, 115, 115);
        border-radius: 16px;
      }

      > .info {
        background-color: rgb(250,250,250);
        line-height: 1.5rem;
        padding: 1rem; 
      }

      .imagesContainer > img {
        border-radius: 12px;
      }
    }
  `
  
  return (
    <section css={styles}>
      <header>
        <div>
          <h3>{travel.name}</h3>
          <h4>{travel.rating} (Valoraciones: {travel.reviews?.length})</h4>
        </div>
        {handleCloseModal && <Button variant="default" onClick={handleCloseModal} additionalStyles={css`display: flex;`}><Close /></Button>}
      </header>
      <section>
        <Carrousel_Basic imagesProps={travel.images.map(imageData => ({src: generateImageURL(imageData)}))} />        
        <div className="info">
          <h4>Destinos</h4>
          <List>
            {travel.destinies.map(destiny => <li key={destiny.place}>{destiny.place}</li>)}
          </List>
          <h4>Fechas disponibles:</h4>
          <List>
            {travel.availableDates.map(date => <li key={formatDate(date)}>{formatDate(date)}</li>)}
          </List>
          <h4>{travel.isAvailable ? "Disponible para la próxima fecha" : "No disponible"}</h4>
          <h4>Capacidad actual:{travel.currentCapacity}</h4>
          <h4>Capacidad máxima: {travel.maxCapacity}</h4>
          <h4>Días de duración en total: {travel.longInDays}</h4>
          <h4>Planes disponibles:</h4>
          <List>
            {travel.payPlans.map(plan => <li key={plan.planFor}>{plan.planFor}: ${plan.price}</li>)}
          </List>
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