import { css } from "@emotion/react";
import { MainSection } from "../../components/MainSection";
import { Travel } from "../../types/ApiTypes";
import { useWindowProps } from "../../hooks/useWindowsProps";
import { TravelContent } from "./sections/TravelContent";

export function SpecificTravelPage({travel} : {travel: Travel}) {
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
    <main>
      <MainSection css={styles}>
        <TravelContent travel={travel} />
      </MainSection>
      <MainSection>
        Recomendaciones y otros viajes
      </MainSection>
    </main>
  )
}