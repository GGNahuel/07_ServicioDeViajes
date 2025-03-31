import { ChangeEvent, FormEvent, useState } from "react";
import { Button } from "../../components/Button";
import { MainSection } from "../../components/MainSection";
import { AddIcon, CheckIcon, PencilIcon, TrashCanIcon } from "../../components/SvgIcons";
import { Person, Travel } from "../../types/ApiTypes";
import { formatDate } from "../../utils/fromApi/formatDateFromApi";
import { Card } from "../../components/Card";
import { css } from "@emotion/react";

type actionsToPersonList = "add" | "delete"
type PersonCardType = {id: number, data: Person | null}
type HandleActionParameters = (e: FormEvent<HTMLFormElement> | null, card: PersonCardType, action: actionsToPersonList) => void

export function MakeRequestPage({travel} : {travel?: Travel}) {
  const [cardList, setCardList] = useState<PersonCardType[]>([])

  const handleChangesInPersonsAdded: HandleActionParameters = (e, card, action) => {
    e?.preventDefault()
    
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

  return (
    <MainSection>
      <h2>Formulario para {travel?.name}</h2>
      <form>
        <label>Ingrese su email: <input type="email" name="email" /></label>
        <label>Seleccione una de las fechas disponibles<select name="date">
          {travel?.availableDates.map(date => <option value={formatDate(date)}></option>)}  
        </select></label>

        <section>
          <header>
            <h3>Ingrese los datos de las personas que viajaran</h3>
            <Button variant="rounded" onClick={() => {
              const id = cardList.length > 0 ? cardList[cardList.length - 1].id + 1 : 0
              setCardList(prev => [...prev, {id, data: null}])
            }}><AddIcon /></Button>
          </header>
          {cardList.map(card => <PersonCardInRequestForm key={card.id} card={card} handleAction={handleChangesInPersonsAdded} />)}
        </section>
      </form>
    </MainSection>
  )
}

function PersonCardInRequestForm({handleAction, card} : {handleAction: HandleActionParameters, card: PersonCardType}) {
  const [showingForm, setShowingForm] = useState<boolean>(card.data != null)
  const [personInForm, setPersonInForm] = useState<Person>({
    name: "",
    age: -1,
    identificationNumber: 0,
    contactPhone: 0
  })

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>, property: keyof Person) => {
    setPersonInForm(prev => ({
      ...prev,
      [property]: e.target.value
    }))
  }

  return (
    <Card>
      {showingForm && <form onSubmit={e => handleAction(e, {id: card.id, data: personInForm}, "add")}>
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
        <Button variant={"default"} type="submit" additionalStyles={css`gap: 8px; align-items: center;`}
          disabled={personInForm.name === "" || personInForm.age >= 0}
        >
          <CheckIcon/>Agregar
        </Button>
      </form>}
      {!showingForm && card.data && <>
        <div>
          <Button variant="rounded" onClick={() => setShowingForm(true)}><PencilIcon /></Button>
          <Button variant="rounded" onClick={() => handleAction(null, card, "delete")}><TrashCanIcon /></Button>
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