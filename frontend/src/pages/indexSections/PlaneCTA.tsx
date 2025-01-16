import { css } from "@emotion/react"

export function PlaneCTA() {
  const planeCtaStyles = css`
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    gap: 1rem;

    > img {
      width: 100%;
      max-width: 500px;
    }

    > a {
      font-size: 2rem;
      max-width: 20ch;
      text-align: center;
    }
  `

  return (
    <section css={planeCtaStyles} className="blank">
      <img src="/plane.webp" alt="" />
      <a href="">Te asesoramos en lo que necesites</a>
    </section>
  )
}