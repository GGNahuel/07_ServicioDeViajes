import { css } from "@emotion/react"
import { Card } from "../../../components/Card"
import { MainSection } from "../../../components/MainSection"

export function Plans(): JSX.Element {
  const cardsContainerStyle = css`
    margin-top: 2rem;
    width: 100%;
    display: flex;
    justify-content: space-evenly;
    align-items: stretch;
    flex-wrap: wrap;
  `

  return (
    <MainSection id="paymentPlans">
      <h2>Planes para que puedas disfrutar en grupo, pareja o individualmente</h2>
      <div css={cardsContainerStyle}>
        <PlanCard imgSrc="/personUiFamily.webp" name="Plan familiar"/>
        <PlanCard imgSrc="/personUiFriends.webp" name="Plan para grupo de amigos" />
        <PlanCard imgSrc="/personUiCouple.webp" name="Plan en pareja" />
        <PlanCard imgSrc="/personUiSolo.webp" name="Plan individual" />
      </div>
    </MainSection>
  )
}

function PlanCard({imgSrc, name} : {imgSrc: string, name: string}): JSX.Element {
  const style = css`
    display: flex;
    width: 15ch;
    flex-direction: column;
    align-items: center;
    text-align: center;

    & > img {
      width: 100%;
      aspect-ratio: 1;
      filter: drop-shadow(3px 3px 1px rgba(57,57,57,0.6));
    }
  `

  return (
    <Card additionalStyles={style}>
      <img src={imgSrc} alt="Siluetas de personas representativa del plan" />
      <h3>{name}</h3>
    </Card>
  )
}