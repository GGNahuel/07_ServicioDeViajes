import { css } from "@emotion/react";
import { Button } from "../../../components/Button";
import { MainSection } from "../../../components/MainSection";
import { useWindowProps } from "../../../hooks/useWindowsProps";

export function TravelsSearchForm({makeRequest} : {makeRequest: (e: React.FormEvent<HTMLFormElement>) => Promise<void>}) {
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
  )
}