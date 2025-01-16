import { css } from "@emotion/react"
import { useWindowProps } from "../../hooks/useWindowsProps"

export function MakeYourTravel() {
  const {width} = useWindowProps()

  const makeYourTravelStyles = css`
    background-color: rgb(54, 16, 191);
    color: aliceblue;
    max-width: none;
    
    > section {
      ${width > 720 ? 
        `display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        align-items: center;
        ` 
        : 
        `display: flex;
        flex-direction: column-reverse;
        `
      }
      max-width: 1136px;
  
      > img {
        width: 100%;
        max-width: 600px;
        aspect-ratio: 1; 
        filter: drop-shadow(2px 2px 3px rgba(0,0,0,1))
      }
      
      > div {
        min-height: ${width <= 720 ? "none" : "450px"};
        align-self: flex-start;
        display: flex;
        justify-content: space-between;
        flex-direction: column;
        gap: 1rem;
      }
    }
      
    h2 {
      width: auto;
    }
          
    p {
      max-width: 45ch;
    }

    .textsArea {
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }
  `

  return (
    <section id="makeYourOwnTravelSection" css={makeYourTravelStyles} className="blank">
      <section>
        <img src="/bus.webp" alt="" />
        <div>
          <h2>Arma tu viaje personalizado con nosotros</h2>
          <div className="textsArea">
            <p>
              Diseñado para quienes buscan una experiencia única y adaptada a sus intereses. 
            </p>
            <p>
              Desde elegir destinos hasta coordinar actividades, trabajamos contigo para crear el viaje perfecto. 
              Ideal para viajeros que desean explorar nuevas opciones y vivir momentos inolvidables.
            </p>
          </div>
          <div>
            <h4>¡Haz de tu próximo viaje una aventura a tú medida!</h4>
            <a href=""><button>Conoce más</button></a>
          </div>
        </div>
      </section>
    </section>
  )
}