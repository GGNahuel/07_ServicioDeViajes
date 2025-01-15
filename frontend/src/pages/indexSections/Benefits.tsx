import { Card } from "../../components/Card"

export function Benefits() {
  return (
    <section id="benefitsSection">
      <h2>Beneficios</h2>
      <section className="valija" style={{
        width: "100%",
        position: "relative",
      }}>
        <img src="/maleta.jpg" alt="" style={{width: "100%"}} />
        <div style={{
          position: "absolute",
          width: "36%",
          height: "68%",
          top: "14%",
          left: "7.5%",
          padding: "1rem",
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-around"
        }}>
          <BenefitCard name="Planes Personalizados" />
          <BenefitCard name="Asesoramiento Experto" />
          <BenefitCard name="Asistencia 24/7" />
          <BenefitCard name="Flexibilidad en Pagos" />
          <BenefitCard name="Seguros de Viaje Incluidos" />
          <BenefitCard name="Reservas Seguras y Rápidas" />
        </div>
      </section>
    </section>
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