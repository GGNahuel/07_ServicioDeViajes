import "../styles/indexPage.css"

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
        <section className="blank">
          <div className="carrouselContainer">
            <img src="" alt="" />
            <img src="" alt="" />
            <img src="" alt="" />
            <img src="" alt="" />
            <img src="" alt="" />
            <img src="" alt="" />
            <img src="" alt="" />
            <img src="" alt="" />
          </div>
        </section>
        <section id="mapSection">
          <header>
            <h2>Mapa de lugares</h2>
            <a href=""><button>a página de viajes parte de búsqueda</button></a>
          </header>
          <article className="hoveredPlace">
            <div>
              <img src="" alt="" />
              <h3>Nombre lugar</h3>
            </div>
            <div>
              <p>texto</p>
              <ul>
                <li>
                  <img src="" alt="" />
                  <p>Hospedaje</p>
                </li>
                <li>
                  <img src="" alt="" />
                  <p>Hospedaje</p>
                </li>
              </ul>
            </div>
          </article>
          <section id="map">
            <div className="pin">lugar1</div>
            <div className="pin">lugar2</div>
            <div className="pin">lugar3</div>
            <div className="pin">lugar4</div>
            <div className="pin">lugar5</div>
            <div className="pin">lugar6</div>
            <div className="pin">lugar7</div>
            <div className="pin">lugar8</div>
            <div className="pin">lugar9</div>
            <div className="pin">lugar10</div>
          </section>
        </section>
        <section id="paymentPlans">
          <h2>Planes para que puedas disfrutar en grupo, pareja o solo</h2>
          <article>
            <img src="" alt="" />
            <h3>Plan familiar</h3>
          </article>
          <article>
            <img src="" alt="" />
            <h3>Plan grupo de amigos</h3>
          </article>
          <article>
            <img src="" alt="" />
            <h3>Plan Pareja</h3>
          </article>
          <article>
            <img src="" alt="" />
            <h3>Plan Solo</h3>
          </article>
        </section>
        <section id="benefitsSection">
          <h2>Beneficios</h2>
          <section className="valija">
            <article>
              <img src="" alt="" />
              <h3>texto del beneficio</h3>
            </article>
            <article>
              <img src="" alt="" />
              <h3>texto del beneficio</h3>
            </article>
            <article>
              <img src="" alt="" />
              <h3>texto del beneficio</h3>
            </article>
          </section>
        </section>
        <section className="blank">
          <div>
            <img src="" alt="" />
            <a href="">Te asesoramos en lo que necesites</a>
          </div>
        </section>
        <section id="makeYourOwnTravelSection">
          <img src="" alt="" />
          <div>
            <h2>Arma tu viaje personalizado con nosotros</h2>
            <p>texto de para quienes estaría pensado esta opción</p>
            <a href=""><button>Conoce más</button></a>
          </div>
        </section>
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