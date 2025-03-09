import { ReactNode } from "react"
import { Card } from "../../components/Card"
import { useWindowProps } from "../../hooks/useWindowsProps"
import { css } from "@emotion/react"

export function Benefits() {
  const {width} = useWindowProps()

  return (
    <section id="benefitsSection">
      <h2>Beneficios</h2>
      <section className="valija" style={{
        width: "100%",
        position: "relative",
      }}>
        <img src="/maleta.jpg" alt="" style={{width: "100%"}} />
        <BenefitCardContainer side="left">
          <BenefitCard name="Planes Personalizados" />
          <BenefitCard name="Asesoramiento Experto" variation />
          <BenefitCard name="Asistencia 24/7" variation />
          {width > 930 && <>
            <BenefitCard name="Flexibilidad en Pagos" />
            <BenefitCard name="Seguros de Viaje Incluidos" />
            <BenefitCard name="Reservas Seguras y Rápidas" variation />
          </>}
        </BenefitCardContainer>
        {width <= 930 && <BenefitCardContainer side="right">
          <BenefitCard name="Flexibilidad en Pagos" />
          <BenefitCard name="Seguros de Viaje Incluidos" />
          <BenefitCard name="Reservas Seguras y Rápidas" />
        </BenefitCardContainer>}
      </section>
    </section>
  )
}

function BenefitCardContainer({side, children} : {side: "left" | "right", children: ReactNode}) {
  const style = css`
    position: absolute;
    width: 36%;
    height: 68%;
    top: 14%;
    left: ${side == "left" ? "7.5" : "52.5"}%;
    padding: 1rem;
    display: flex;
    flex-wrap: wrap;
    align-items: stretch;
  `

  return (
    <div css={style}>
      {children}
    </div>
  )
}

function BenefitCard({name, variation}: {name: string, variation?: boolean}) {
  const style = css`
    display: flex;
    align-items: center;
    color: rgb(54, 150, 72);
    background-image: url("${variation ? "rollo2.png" : "rollo.png"}");
    background-size: 100% 100%;
    width: 100%;

    h3 {
      border-radius: 8px;
      padding: 8px;
      background: rgba(255, 255, 255, 0.75);
    }
  `

  return (
    <Card additionalStyles={style}>
      <h3>{name}</h3>
    </Card>
  )
}