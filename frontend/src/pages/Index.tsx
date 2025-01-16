import { CarrouselAuto } from "../components/CarrouselAuto"
import "../styles/indexPage.css"
import { Benefits } from "./indexSections/Benefits"
import { MakeYourTravel } from "./indexSections/MakeYourTravel"
import { Map } from "./indexSections/Map"
import { PlaneCTA } from "./indexSections/PlaneCTA"
import { Plans } from "./indexSections/Plans"

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
      <main className="defaultPadding">
       <section id="presentationSection">
          <div><h1>Journey Joy</h1></div>
          <video src="indexVideo2.mp4" autoPlay muted loop></video>
          <div><h2>Viajes inolvidables, felicidad en el camino</h2></div>
        </section>
        <section className="blank">
          <button><a href="">Sobre nosotros</a></button>
          <button><a href="">Tenemos el plan perfecto para vos</a></button>
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
          <a href=""><button>ver todos</button></a>
        </section>
        <CarrouselAuto />
        <Map />
        <Plans />
        <Benefits />
        <PlaneCTA />
        <MakeYourTravel />
        <section id="opinionsSection">
          <article>
            <img src="" alt="" />
            <p>texto de valoración</p>
          </article>
          <article>
            <img src="" alt="" />
            <p>texto de valoración</p>
          </article>
          <article>
            <img src="" alt="" />
            <p>texto de valoración</p>
          </article>
        </section>
        <section id="contactSection">
          <h2>Escríbanos</h2>
          <div>
            <a href=""><button>Sobre nosotros</button></a>
            <a href=""><button>Nuestro contrato</button></a>
          </div>
          <form action="">
            <label>texto<input type="text" /></label>
            <label>texto<input type="text" /></label>
            <button type="submit">enviar</button>
          </form>
        </section>
      </main>
    </>
  )
}