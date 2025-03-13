import { Button } from "../../components/Button";
import { Card } from "../../components/Card";
import { MainSection } from "../../components/MainSection";
import { useTravelSearcher } from "../../requests/TravelRequests";

export function TravelsPage() {
  const {response, makeRequest} = useTravelSearcher()

  return (
    <main>
      <MainSection>
        <form onSubmit={(e) => makeRequest(e)}>
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
          <Button variant="default" otherType="submit">Aplicar</Button>
        </form>
      </MainSection>
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
    </main>
  )
}