import { css } from "@emotion/react";
import { Button } from "../../../components/Button";
import { PopularTravel } from "../../../types/ApiTypes";
import { Card } from "../../../components/Card";

const dataFromApi: (PopularTravel & {angle: number})[] = [
  {
    name: "Viaje 1",
    description: "sadasdasd asda asdasdas",
    placesNames: ["place1, place2"],
    angle: -4
  },
  {
    name: "Viaje 2",
    description: "asdasdsad sadsad sadas",
    placesNames: ["place1", "place2", "place3"],
    angle: -12
  },
  {
    name: "Viaje 3",
    description: "asdasdsad asdasdsadas",
    placesNames: ["place1, place2, place3, place4"],
    angle: 8
  },
  {
    name: "Viaje 4",
    description: "adssadasdasasdasd sadsadsa sdad",
    placesNames: ["place1, place2"],
    angle: 6
  }
]

export function PopularOnes() {
  const style = css`
    padding: 1rem;
    background-image: url("woodTexture.jpg");

    > section {
      width: 100%;
      display: grid;
      row-gap: 1.5rem;
      grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
      justify-items: center;
      padding: 3rem 0;
    }
  `

  return (
    <section id="popularTravelsSection" css={style}>
      <h2>Viajes populares</h2>
      <section>
        {dataFromApi.map(element => <PostalCard key={element.name} angle={element.angle} travel={element} />)}
      </section>
      <a href=""><Button variant="default">ver todos</Button></a>
    </section>
  )
}

function PostalCard({angle, travel} : {angle: number, travel: PopularTravel}) {
  const style = css`
    rotate: ${angle}deg;
    max-width: 470px;
    width: 100%;
    border-radius: 0;
    display: grid;
    grid-template-columns: 50% 50%;
    transition: rotate 300ms ease;
    box-sizing: border-box;
    box-shadow: 10px 10px 2px rgb(73, 73, 73, 0.8);
    background-color: rgb(255, 247, 240);
    background-image: url("${Math.random() > 0.5 ? "postal1.webp" : "postal2.webp"}");
    background-size: 100% 100%;
    
    &:nth-child(even) {
      justify-self: self-start;
      translate: -20px 0;
    }
    &:nth-child(odd) {
      justify-self: self-end;
      translate: 20px 0;
    }
    
    &:hover {
      rotate: 0deg;
      z-index: 8;
      scale: 1.1;
      box-shadow: 18px 18px 3px rgba(73, 73, 73, 0.6);
      transition: all 300ms;
    }

    ul {
      padding-left: 1rem;
    }
  `

  return (
    <Card additionalStyles={style}>
      <img src="/dsad.png" alt="" />
      <div>
        <h3>{travel.name}</h3>
        <p>{travel.description}</p>
        <ul>
          {travel.placesNames.map((element, index) => <li key={index}>{element}</li>)}
        </ul>
      </div>
    </Card>
  )
}