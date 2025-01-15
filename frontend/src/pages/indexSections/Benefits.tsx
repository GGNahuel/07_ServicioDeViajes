import { ReactNode } from "react"
import { Card } from "../../components/Card"
import { useWindowProps } from "../../hooks/useWindowsProps"

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
          <BenefitCard name="Asesoramiento Experto" />
          <BenefitCard name="Asistencia 24/7" />
          {width > 930 && <>
            <BenefitCard name="Flexibilidad en Pagos" />
            <BenefitCard name="Seguros de Viaje Incluidos" />
            <BenefitCard name="Reservas Seguras y Rápidas" />
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
  return (
    <div style={{
      position: "absolute",
      width: "36%",
      height: "68%",
      top: "14%",
      left: side == "left" ? "7.5%" : "52.5%",
      padding: "1rem",
      display: "flex",
      flexDirection: "column",
      justifyContent: "space-around"
    }}>
      {children}
    </div>
  )
}

function BenefitCard({name, desc}: {name: string, desc?: string}) {
  return (
    <Card styles={{
      display: "flex",
      alignItems: "center",
      color: "aliceblue"
    }}>
      <h3>{name}</h3>
      <p>{desc}</p>
    </Card>
  )
}