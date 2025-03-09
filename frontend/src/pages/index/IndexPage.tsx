import { Button } from "../../components/Button"
import { CarrouselAuto } from "../../components/CarrouselAuto"
import "../../styles/indexPage.css"
import { Benefits } from "./sections/Benefits"
import { MakeYourTravel } from "./sections/MakeYourTravel"
import { Map } from "./sections/Map"
import { PlaneCTA } from "./sections/PlaneCTA"
import { Plans } from "./sections/Plans"
import { Reviews } from "./sections/Reviews"

export function IndexPage() {
  return (
    <>
      <header className="defaultPadding navbar">
        <div className="hidden">
          <h2>Spacing text</h2>
        </div>
        <nav className="fixed">
          <div>
            <h2>Navbar</h2>
          </div>
        </nav>
      </header>
      <main>
       <section id="presentationSection">
          <div><h1>Journey Joy</h1></div>
          <video src="indexVideo2.mp4" autoPlay muted loop></video>
          <div><h2>Viajes inolvidables, felicidad en el camino</h2></div>
        </section>
        <section className="blank">
          <a href=""><Button variant="default">Sobre nosotros</Button></a>
          <a href=""><Button variant="main">Tenemos el plan perfecto para vos</Button></a>
        </section>
        <section id="popularTravelsSection">
          <h2>Viajes populares</h2>
          <section>
            <article className="travelPostal" style={{rotate: "-4deg"}}>
              <img src="asd.png" alt="img" />
              <div>
              <h3>Nombre viaje</h3>
                <p>texto</p>
                <div>
                  planes diponibles
                </div>
              </div>
            </article>
            <article className="travelPostal" style={{rotate: "-12deg"}}>
              <img src="" alt="" />
              <div>
              <h3>Nombre viaje</h3>
                <p>texto</p>
                <div>
                  planes diponibles
                </div>
              </div>
            </article>
            <article className="travelPostal" style={{rotate: "8deg"}}>
              <img src="" alt="" />
              <div>
              <h3>Nombre viaje</h3>
                <p>texto</p>
                <div>
                  planes diponibles
                </div>
              </div>
            </article>
            <article className="travelPostal" style={{rotate: "-4deg"}}>
              <img src="" alt="" />
              <div>
              <h3>Nombre viaje</h3>
                <p>texto</p>
                <div>
                  planes diponibles
                </div>
              </div>
            </article>
          </section>
          <a href=""><Button variant="default">ver todos</Button></a>
        </section>
        <CarrouselAuto />
        <Map />
        <Plans />
        <Benefits />
        <PlaneCTA />
        <MakeYourTravel />
        <Reviews />
        <section id="contactSection">
          <h2>Escríbanos</h2>
          <div>
            <a href=""><Button variant="default">Sobre nosotros</Button></a>
            <a href=""><Button variant="default">Nuestro contrato</Button></a>
          </div>
          <form action="">
            <label>texto<input type="text" /></label>
            <label>texto<input type="text" /></label>
            <Button variant="default" otherType="submit">enviar</Button>
          </form>
        </section>
      </main>
    </>
  )
}