import { Card } from "../../components/Card"

export function Plans(): JSX.Element {
  return (
    <section id="paymentPlans">
      <h2>Planes para que puedas disfrutar en grupo, pareja o individualmente</h2>
      <div style={{
        marginTop: "2rem",
        display: "flex",
        width: "100%",
        justifyContent: "space-evenly",
        alignItems: "stretch",
        flexWrap: "wrap",
      }}>
        <PlanCard imgSrc="/personUiFamily.webp" name="Plan familiar"/>
        <PlanCard imgSrc="/personUiFriends.webp" name="Plan para grupo de amigos" />
        <PlanCard imgSrc="/personUiCouple.webp" name="Plan en pareja" />
        <PlanCard imgSrc="/personUiSolo.webp" name="Plan individual" />
      </div>
    </section>
  )
}

function PlanCard({imgSrc, name} : {imgSrc: string, name: string}): JSX.Element {
  return (
    <Card styles={{
      display: "flex",
      width: "15ch",
      flexDirection: "column",
      alignItems: "center",
      textAlign: "center",
    }}>
      <img src={imgSrc} alt="" style={{
        width: "100%",
        aspectRatio: "1",
        filter: "drop-shadow(3px 3px 1px rgba(57,57,57,0.6))"
      }} />
      <h3>{name}</h3>
    </Card>
  )
}