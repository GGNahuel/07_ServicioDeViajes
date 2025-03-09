import { css } from "@emotion/react"
import { Card } from "../../../components/Card"

export function Reviews() {
  const reviewsSectionStyles = css`
    display: grid !important;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    align-items: stretch;
    gap: 1rem;
  `

  return (
    <section id="reviewsSection" css={reviewsSectionStyles}>
      <ReviewCard imgSrc="/reviews/person1.webp" text="Muy buen servicio" />
      <ReviewCard imgSrc="/reviews/person2.webp" text="Una opción diferente que vale totalmente darle la oportunidad" />
      <ReviewCard imgSrc="/reviews/person3.webp" text="Muy buena organización durante el viaje y muy buena atención" />
      <ReviewCard imgSrc="/reviews/person4.png" text="La pasamos genial en familia" />
    </section>
  )
}

function ReviewCard({imgSrc, text} : {imgSrc: string, text: string}) {
  const style= css`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;

    & > img {
      width: 100%;
      object-fit: cover;
      aspect-ratio: 1;
      border-radius: 100%;
    }
  `

  return (
    <Card additionalStyles={style}>
      <img src={imgSrc} alt="Foto del rostro de uno de los clientes" />
      <p>{text}</p>
    </Card>
  )
}
