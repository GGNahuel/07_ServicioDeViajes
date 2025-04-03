import { ChangeEvent, useEffect, useState } from "react";
import { Button } from "../../components/Button";
import { MainSection } from "../../components/MainSection";
import { AddIcon, CheckIcon, PencilIcon, TrashCanIcon } from "../../components/SvgIcons";
import { Person, Travel } from "../../types/ApiTypes";
import { formatDate } from "../../utils/fromApi/formatDateFromApi";
import { Card } from "../../components/Card";
import { css } from "@emotion/react";
import { Carrousel_Basic } from "../../components/Carrousel_Basic";
import { payPlansTranslations } from "../../const/ApiConstants";

type actionsToPersonList = "add" | "delete"
type PersonCardType = {id: number, data: Person | null}
type HandleActionParameters = (card: PersonCardType, action: actionsToPersonList) => void

export function MakeRequestPage({travel} : {travel?: Travel}) {
  const [cardList, setCardList] = useState<PersonCardType[]>([])
  const [carrouselIndex, setCarrouselIndex] = useState<number>(0)

  const handleChangesInPersonsAdded: HandleActionParameters = (card, action) => {   
    let newList: PersonCardType[] = []
    if (action == "add") {
      newList = cardList.map(cardInList => 
        cardInList.id !== card.id ? cardInList : card
      )
    } else {
      newList = cardList.filter(cardInList => cardInList.id !== card.id)
    }

    setCardList(newList)
  }

  const changeCarrouselIndex = (index: number) => {
    setCarrouselIndex(index)
  }

  const styles = css`
    form {
      width: 100%;

      .inputsZone {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 0.5rem;
        border: 2px solid rgb(115, 115, 115);
        border-radius: 16px;
        background-color: rgb(250,250,250);
        padding: 1rem;
      }

      .personsZone {
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100%;
        
        > header {
          display: flex;
          align-items: center;
          gap: 1rem;
        }
        
        > div {
          padding: 1rem;
          display: grid;
          grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
          gap: 16px;
          width: 100%;
        }
      }
    }
  `

  return (
    <MainSection styles={styles}>
      <h2>Formulario para {travel?.name}</h2>
      <form>
        <Carrousel_Basic 
          listLength={2} isNotCyclic includesSelector={false}
          nextButton={carrouselIndex == 0 ? "Siguiente" : "Enviar solicitud"} canGoNext={cardList.length > 0 && cardList.every(card => card.data != null)}
          prevButton={"Anterior"} canGoPrevious={carrouselIndex == 1}
          indexGetter={changeCarrouselIndex}
        >
          <div className="inputsZone">
            <label>Ingrese su email: <input type="email" name="email" required /></label>
            <label>Seleccione una de las fechas disponibles <select name="date" required>
              {travel?.availableDates.map((date, i) => <option key={i} value={formatDate(date)}>{formatDate(date)}</option>)}  
            </select></label>

            <section className="personsZone">
              <header>
                <h3>Ingrese los datos de las personas que viajaran</h3>
                <Button variant="rounded" type="button" onClick={() => {
                  if (cardList.length > 0 && cardList[cardList.length -1].data == null) return
                  
                  const id = cardList.length > 0 ? cardList[cardList.length - 1].id + 1 : 0
                  setCardList(prev => [...prev, {id, data: null}])
                }}><AddIcon /></Button>
              </header>
              <div>
                {cardList.map(card => <PersonCardInRequestForm key={card.id} card={card} handleAction={handleChangesInPersonsAdded} />)}
              </div>
            </section>
          </div>
          <div className="inputsZone">
            <p>Seleccione el plan de pago</p>
            {travel?.payPlans.map(payPlan => <label key={payPlan.planFor}>
              <input type="radio" name="payPlan" value={payPlan.planFor}/> {payPlansTranslations[payPlan.planFor]}: ${payPlan.price}
            </label>)}
            <label>Ingrese la cantidad a pagar<input type="number" name="amountPaid" /></label>
            <h3>Datos de tarjeta</h3>
            <label>Número de tarjeta <input type="text" value={"xxxx-xxxx-xxxx-xxxx"} disabled /></label>
            <label>Fecha de vencimiento <input type="text" value={"xx/xx"} disabled /></label>
            <label>Titular <input type="text" disabled /></label>
            <label>Código de seguridad <input type="text" value={"xxx"} disabled/></label>
          </div>
        </Carrousel_Basic>
      </form>
    </MainSection>
  )
}

function PersonCardInRequestForm({handleAction, card} : {handleAction: HandleActionParameters, card: PersonCardType}) {
  const [showingForm, setShowingForm] = useState<boolean>(card.data === null)
  const [personInForm, setPersonInForm] = useState<Person>({
    name: "",
    age: -1,
    identificationNumber: 0,
    contactPhone: 0
  })

  useEffect(() => {
    setShowingForm(card.data === null)
  }, [card])

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>, property: keyof Person) => {
    const newValue = (property === "age" || property == "contactPhone" || property == "identificationNumber") ?
      Number(e.target.value) : e.target.value

    setPersonInForm(prev => ({
      ...prev,
      [property]: newValue
    }))
  }

  const styles = css`
    position: relative;

    > div {
      display: flex;
      flex-direction: column;
      gap: 0.5rem;

      label {
        display: flex;
        flex-direction: column;
      }

      .buttonZone { 
        align-self: flex-end; 
        display: flex;
        gap: 8px;

        > button {
          gap: 8px; 
          align-items: center; 
        }    
      }
    }

    > .buttonZone {
      gap: 8px;
      position: absolute;
      right: -1.2rem;
      top: -1rem;
      width: min-content;
      z-index: 5;
      
      button {
        background-color: rgb(210, 210, 210);
        padding: 8px;
        aspect-ratio: 1;
      }
    }

    &:hover .buttonZone.svgZone > button {
      scale: 1.1;
      transition: scale 200ms:
    }
  `

  return (
    <Card additionalStyles={styles}>
      {showingForm && <div>
        <label>Nombre completo
          <input type="text" name="name" value={personInForm.name} onChange={(e) => handleInputChange(e, "name")}/></label>  
        <label>Edad
          <input type="number" name="age" value={personInForm.age} onChange={(e) => handleInputChange(e, "age")} />
        </label>
        <label>Número de identificación
          <input type="number" name="idNumber" value={personInForm.identificationNumber} onChange={e => handleInputChange(e, "identificationNumber")} />
        </label>
        <label>Número de contacto
          <input type="number" name="contactPhone" value={personInForm.contactPhone} onChange={e => handleInputChange(e, "contactPhone")} />
        </label>
        <div className="buttonZone">
          <Button variant="secondary" type="button" 
            onClick={() => handleAction(card, "delete")}
          >
            Cancelar
          </Button>
          <Button variant={"default"} type="button"
            disabled={personInForm.name === "" || personInForm.age < 0 || personInForm.identificationNumber < 100} 
            onClick={() => handleAction({id: card.id, data: personInForm}, "add")}
          >
            <CheckIcon/>Agregar
          </Button>
        </div>
      </div>}
      {!showingForm && card.data && <>
        <div className="buttonZone svgZone">
          <Button variant="rounded" onClick={() => setShowingForm(true)}><PencilIcon /></Button>
          <Button variant="rounded" onClick={() => handleAction(card, "delete")}><TrashCanIcon /></Button>
        </div>
        <div>
          <p><strong>Nombre completo</strong> {card.data.name}</p>
          <p><strong>Edad</strong> {card.data.age}</p>
          <p><strong>Número de identificación</strong> {card.data.identificationNumber}</p>
          <p><strong>Número de contacto</strong> {card.data.contactPhone}</p>
        </div>
      </>}
    </Card>
  )
}