import { Button } from "../../components/Button";
import { MainSection } from "../../components/MainSection";

export function TravelsPage() {
  return (
    <main>
      <MainSection>
        <form>
          <label>Lugar<input type="text" /></label>
          <label>Capacidad deseada<input type="number" /></label>
          <label>Mínimo días de duración<input type="number" name="" /></label>
          <label>Máximo días de duración<input type="number" name="" /></label>
          <label>Disponibilidad
            <select>
              <option value="null">Todas</option>
              <option value="true">Disponibles</option>
              <option value="false">No disponibles</option>  
            </select>
          </label>
          <Button variant="default" otherType="submit">Aplicar</Button>
        </form>
      </MainSection>
    </main>
  )
}