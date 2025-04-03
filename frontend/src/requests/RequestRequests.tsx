import { FormEvent, useContext, useState } from "react";
import { handleRequest } from "./_RequestHandler";
import { PayPlan, PayPLansType, Person, Request, Travel } from "../types/ApiTypes";
import { MessageContext, MessageContextInterface } from "../contexts/MessageContext";
import { formatDateToApi } from "../utils/fromApi/formatDateFromApi";

export function useRequestCreator() {
  const [returned, setReturned] = useState<Request | unknown>()
  const {setValue} = useContext(MessageContext) as MessageContextInterface

  const sendRequest = async (e: FormEvent<HTMLFormElement>, persons: Person[], associatedTravel: Travel) => {
    e.preventDefault()
    const form = new FormData(e.currentTarget)

    // API will check or set the total price if it's needed
    const planFor = form.get("payPlan") as PayPLansType;
    const associatedPlan = associatedTravel.payPlans.find(payPlan => payPlan.planFor == planFor)
    const newRequest: Request = {
      selectedPlan: associatedPlan as PayPlan,
      persons: persons,
      email: {
        email: form.get("email") as string,
        owner: persons[0].name
      },
      amountPaid: Number(form.get("amountPaid")),
      totalPrice: associatedPlan?.price,
      associatedTravel,
      selectedDate:formatDateToApi(form.get("date") as string)
    }
    console.log(newRequest)

    const apiReturn = await handleRequest("/request", "POST", {
      objectToStringify: newRequest
    }, setValue)

    setReturned(apiReturn)
  }

  return {returned, sendRequest}
}