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
    console.log(newList)
  }

  return (
    <MainSection>
      <h2>Formulario para {travel?.name}</h2>
      <form>
        <Carrousel_Basic listLength={2}
          nextButton={"Siguiente"}
        >
          <div>
            <label>Ingrese su email: <input type="email" name="email" required /></label>
            <label>Seleccione una de las fechas disponibles<select name="date" required>
              {travel?.availableDates.map((date, i) => <option key={i} value={formatDate(date)}>{formatDate(date)}</option>)}  
            </select></label>

            <section>
              <header>
                <h3>Ingrese los datos de las personas que viajaran</h3>
                <Button variant="rounded" type="button" onClick={() => {
                  const id = cardList.length > 0 ? cardList[cardList.length - 1].id + 1 : 0
                  setCardList(prev => [...prev, {id, data: null}])
                }}><AddIcon /></Button>
              </header>
              <div>
                {cardList.map(card => <PersonCardInRequestForm key={card.id} card={card} handleAction={handleChangesInPersonsAdded} />)}
              </div>
            </section>
          </div>
          <div>
            <p>Seleccione el plan de pago</p>
            {travel?.payPlans.map(payPlan => <label key={payPlan.planFor}>{payPlansTranslations[payPlan.planFor]}: ${payPlan.price}
              <input type="radio" name="payPlan" value={payPlan.planFor}/>
            </label>)}
            <label>Ingrese la cantidad a pagar<input type="number" name="amountPaid" /></label>
            <h3>Datos de tarjeta</h3>
            <label>Número de tarjeta <input type="text" value={"xxxx-xxxx-xxxx-xxxx"} disabled /></label>
            <label>Fecha de vencimiento<input type="text" value={"xx/xx"} disabled /></label>
            <label>Titular<input type="text" disabled /></label>
            <label>Código de seguridad<input type="text" value={"xxx"} disabled/></label>
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

  return (
    <Card>
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
        <Button variant={"default"} type="button" additionalStyles={css`gap: 8px; align-items: center;`}
          disabled={personInForm.name === "" || personInForm.age < 0 || personInForm.identificationNumber < 100} 
          onClick={() => handleAction({id: card.id, data: personInForm}, "add")}
        >
          <CheckIcon/>Agregar
        </Button>
      </div>}
      {!showingForm && card.data && <>
        <div>
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