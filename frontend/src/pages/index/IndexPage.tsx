import { css } from "@emotion/react"
import { Button } from "../../components/Button"
import { CarrouselAuto } from "../../components/CarrouselAuto"
import { MainSection } from "../../components/MainSection"
import "../../styles/indexPage.css"
import { Benefits } from "./sections/Benefits"
import { MakeYourTravel } from "./sections/MakeYourTravel"
import { Map } from "./sections/Map"
import { PlaneCTA } from "./sections/PlaneCTA"
import { Plans } from "./sections/Plans"
import { PopularOnes } from "./sections/PopularOnes"
import { Reviews } from "./sections/Reviews"
import { Navbar } from "../../components/Navbar"

export function IndexPage() {
  return (
    <>
      <Navbar />
      <main>
       <MainSection id="presentationSection">
          <div><h1>Journey Joy</h1></div>
          <video src="indexVideo2.mp4" autoPlay muted loop></video>
          <div><h2>Viajes inolvidables, felicidad en el camino</h2></div>
        </MainSection>
        <MainSection variant="row" styles={css`min-height: 128px; gap: 1rem;`}>
          <a href=""><Button variant="default">Sobre nosotros</Button></a>
          <a href=""><Button variant="main">Tenemos el plan perfecto para vos</Button></a>
        </MainSection>
        <PopularOnes />
        <CarrouselAuto />
        <Map />
        <Plans />
        <Benefits />
        <PlaneCTA />
        <MakeYourTravel />
        <Reviews />
        <MainSection id="contactSection">
          <h2>Escríbanos</h2>
          <div>
            <a href=""><Button variant="default">Sobre nosotros</Button></a>
            <a href=""><Button variant="default">Nuestro contrato</Button></a>
          </div>
          <form action="">
            <label>texto<input type="text" /></label>
            <label>texto<input type="text" /></label>
            <Button variant="default" type="submit">enviar</Button>
          </form>
        </MainSection>
      </main>
    </>
  )
}