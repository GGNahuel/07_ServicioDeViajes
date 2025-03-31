import { css } from "@emotion/react";
import { Button } from "../../../components/Button";
import { List } from "../../../components/List";
import { Close } from "../../../components/SvgIcons";
import { Travel } from "../../../types/ApiTypes";
import { formatDate } from "../../../utils/fromApi/formatDateFromApi";
import { generateImageURL } from "../../../utils/fromApi/generateImageUrlFromAPI";
import { Carrousel_Basic } from "../../../components/Carrousel_Basic";
import { Tag } from "../../../components/Tag";
import { payPlansTranslations, transportTranslations } from "../../../const/ApiConstants";

export function TravelContent({travel, handleCloseIfModal} : {travel: Travel, handleCloseIfModal?: () => void}): JSX.Element {
  const styles = css`
    display: flex;
    flex-direction: column;
    gap: 1rem;

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

    > section.mainInfo {
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
        display: flex;
        flex-direction: column;
      }

      .imagesContainer > img {
        border-radius: 12px;
      }

      .actionZone {
        margin-top: 2rem;
        align-self: flex-end;
        display: flex;
        gap: 8px;
        align-items: center;
      }
    }

    > section:not(.mainInfo) {
      border: 1px solid rgb(110,110,110);
      border-radius: 16px;
      padding: 1rem;
      display: flex;
      flex-direction: column;
      gap: 0.5rem;
    }
  `
  
  return (
    <section css={styles}>
      <header>
        <div>
          <h3>{travel.name}</h3>
          <h4>{travel.rating} (Valoraciones: {travel.reviews?.length})</h4>
        </div>
        {handleCloseIfModal && 
          <Button variant="secondary-noBorder" onClick={handleCloseIfModal}><Close /></Button>
        }
      </header>
      <section className="mainInfo">
        <Carrousel_Basic listLength={travel.images.length}>
          {travel.images.map((image, index) => <img key={index} src={generateImageURL(image)} alt={image.name} />)}
        </Carrousel_Basic>      
        <div className="info">
          <h4>Destinos</h4>
          <List>
            {travel.destinies.map(destiny => <li key={destiny.place}>{destiny.place}</li>)}
          </List>
          <h4>Fechas disponibles:</h4>
          <List>
            {travel.availableDates.map(date => <li key={formatDate(date)}>{formatDate(date)}</li>)}
          </List>
          <h4>Capacidad actual:{travel.currentCapacity}</h4>
          <h4>Capacidad máxima: {travel.maxCapacity}</h4>
          <h4>Días de duración en total: {travel.longInDays}</h4>
          <h4>Planes disponibles:</h4>
          <List>
            {travel.payPlans.map(plan => <li key={plan.planFor}>{payPlansTranslations[plan.planFor]}: ${plan.price}</li>)}
          </List>
          <div className="actionZone">
            <Tag variant={travel.isAvailable ? "default" : "empty"} 
              additionalStyles={travel.isAvailable ? css`background-color: rgb(0, 82, 0)` : css`color: red;`}
              >
              <h4>{travel.isAvailable ? "Disponible para la próxima fecha" : "No disponible"}</h4>
            </Tag>
            <Button variant="default" disabled={!travel.isAvailable}>Solicitar viaje</Button>
          </div>
        </div>
      </section>
      <section>
        <h3>Detalle de destinos:</h3>
        {travel.destinies.map(destiny => <div>
          <h4>Lugar: {destiny.place}</h4>
          <h4>Transporte: {transportTranslations[destiny.transport]}</h4>
          <h4>Llegada en el día {destiny.leaveDay}</h4>
          <h4>Salida en el día {destiny.returnDay}</h4>
          <h4>Lugar de estadía: {destiny.stayPlaceId.name}. ⭐{destiny.stayPlaceId.rating}</h4>
        </div>)}
      </section>
      <section>
        <h3>Preguntas frecuentes</h3>
        <div>
          <h4>¿Cuáles son los medios de pago disponibles?</h4>
          <p>Aceptamos pagos con tarjeta de crédito, débito, transferencias bancarias y billeteras digitales. También ofrecemos planes de pago en cuotas según el destino y la modalidad del viaje.</p>
        </div>
        <div>
          <h4>¿Puedo viajar solo o necesito ir acompañado?</h4>
          <p>¡Ambas opciones son posibles! Contamos con planes para viajeros individuales, parejas, familias y grupos de amigos.</p>
        </div>
        <div>
          <h4>¿Qué incluye el precio del viaje?</h4>
          <p>Generalmente, los paquetes cubren el transporte, hospedaje, excursiones y algunas comidas. Puedes contactar con nosotros en caso de que quieras saber en especifico para un viaje.</p>
        </div>
        <div>
          <h4>¿Qué pasa si no puedo viajar en la fecha reservada?</h4>
          <p>Si necesitas modificar tu fecha de viaje, contáctenos lo antes posible. Dependiendo de la disponibilidad y la política del viaje, podremos ofrecerte un cambio de fecha o un reembolso parcial.</p>
        </div>
        <div>
          <h4>¿Los viajes incluyen seguro de viajero?</h4>
          <p>Algunos paquetes incluyen seguro de viajero. Si no está incluido, te recomendamos contratar uno para mayor tranquilidad durante tu viaje.</p>
        </div>
        <div>
          <h4>¿Puedo personalizar mi viaje?</h4>
          <p>¡Por supuesto! Si deseas una experiencia más personalizada, podemos armar un itinerario a medida según tus preferencias y necesidades.</p>
        </div>
        <div>
          <h4>¿Cómo puedo recibir ofertas y novedades?</h4>
          <p>Puedes suscribirte a nuestro boletín para recibir descuentos exclusivos, nuevas rutas y promociones especiales.</p>
        </div>
      </section>
    </section>
  )
}