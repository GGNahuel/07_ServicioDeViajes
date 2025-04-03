import { FormEvent, useContext, useState } from "react";
import { handleRequest } from "./_RequestHandler";
import { PayPLansType, Person, Request, Travel } from "../types/ApiTypes";
import { MessageContext, MessageContextInterface } from "../contexts/MessageContext";

export function useRequestCreator() {
  const [returned, setReturned] = useState<Request | unknown>()
  const {setValue} = useContext(MessageContext) as MessageContextInterface

  const sendRequest = async (e: FormEvent<HTMLFormElement>, persons: Person[], associatedTravel: Travel) => {
    e.preventDefault()
    const form = new FormData(e.currentTarget)

    // API will check or set the total price if it's needed
    const totalPrice = associatedTravel.payPlans.find(payPlan => payPlan.planFor == planFor)?.price
    const planFor = form.get("payPlan") as PayPLansType;
    const newRequest: Request = {
      selectedPlan: planFor,
      persons: persons,
      email: {
        email: form.get("email") as string,
        owner: persons[0].name
      },
      amountPaid: Number(form.get("amountPaid")),
      totalPrice: totalPrice,
      associatedTravel,
      selectedDate: form.get("date") as string
    }

    const apiReturn = await handleRequest("/requests", "POST", {
      objectToStringify: newRequest
    }, setValue)

    setReturned(apiReturn)
  }

  return {returned, sendRequest}
}