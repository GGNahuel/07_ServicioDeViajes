import { css } from "@emotion/react";
import { Button } from "../../components/Button";
import { Card } from "../../components/Card";
import { MainSection } from "../../components/MainSection";
import { useTravelSearcher } from "../../requests/TravelRequests";
import { useWindowProps } from "../../hooks/useWindowsProps";

export function TravelsPage() {
  const {response, makeRequest} = useTravelSearcher()

  const {width} = useWindowProps()
  const styles = css`
    width: 100%;
    border: 1px solid rgb(150,150,150);
    padding: 1rem;
    box-sizing: border-box;
    
    div {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(${width > 720 ? "400px" : "200px"}, 1fr));
      gap: 1rem;  

      > label { 
        display: flex;
        width: 100%;
        flex-wrap: wrap;
        justify-content: space-between;
        gap: 8px;
        align-items: center;
      }
    }

    > button {
      margin-top: 1rem;
      float: right; 
    }
  `

  return (
    <main>
      <MainSection>
        <form onSubmit={(e) => makeRequest(e)} css={styles}>
          <div>
            <label>Lugar<input type="text" name="place" /></label>
            <label>Capacidad deseada<input type="number" name="desiredCapacity" /></label>
            <label>Mínimo días de duración<input type="number" name="minDays" /></label>
            <label>Máximo días de duración<input type="number" name="maxDays" /></label>
            <label>Disponibilidad
              <select name="available">
                <option value="null">Todas</option>
                <option value="true">Disponibles</option>
                <option value="false">No disponibles</option>  
              </select>
            </label>
          </div>
          <Button variant="default" otherType="submit">Aplicar</Button>
        </form>
      </MainSection>
      <MainSection variant="row" styles={css`
        gap: 1rem;
        flex-wrap: wrap;
        align-items: stretch;
      `}>    
        {response?.map(travel => 
          <Card key={travel.id}>
            <h3>{travel.name}</h3>
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
      </MainSection>
    </main>
  )
}