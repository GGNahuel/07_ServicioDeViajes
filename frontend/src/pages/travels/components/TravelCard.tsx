import { css } from "@emotion/react";
import { Card } from "../../../components/Card";
import { Travel } from "../../../types/ApiTypes";
import { formatDate } from "../../../utils/fromApi/formatDateFromApi";
import { generateImageURL } from "../../../utils/fromApi/generateImageUrlFromAPI";
import { List } from "../../../components/List";

export function TravelCard({travel, onClick, referencePropsFloatingUI} : 
  {travel: Travel, onClick?: (travel: Travel) => void, referencePropsFloatingUI?: (userProps?: React.HTMLProps<Element>) => Record<string, unknown>}
) : JSX.Element {
  if (!referencePropsFloatingUI) {
    referencePropsFloatingUI = () => ({})
  }

  const styles = css`
    img {
      width: 100%;
      max-width: 250px;
      aspect-ratio: 8/6;
      background-size: cover;
      background-position: center;
    }
  `

  return (
    <Card whenClicked={() => onClick && onClick(travel)} additionalStyles={styles} {...referencePropsFloatingUI()}>
      {travel.images[0] && <img src={generateImageURL(travel.images[0])} alt={travel.images[0].name} />}
      <h3>{travel.name}</h3>
      <h4>{travel.rating}</h4>
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
    </Card>
  )
}