import { useContext, useState } from "react"
import { Travel } from "../types/ApiTypes"
import { handleRequest } from "./_RequestHandler"
import { MessageContext, MessageContextInterface } from "../contexts/MessageContext"
import { generateQueryParamsRoute } from "../utils/generateQueryParamsRoute"

const TRAVEL_API_PREFIX = "/travels"

export function useTravelSearcher() {
  const [response, setResponse] = useState<Travel[]>()
  const {setValue} = useContext(MessageContext) as MessageContextInterface

  interface requestParams {
    available?: boolean | string | null,
    desiredCapacity?: number,
    place?: string,
    minDays?: number,
    maxDays?: number,
  }

  const makeRequest = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    const form = e.currentTarget
    
    const paramsValues: requestParams = {
      available: form.available.value != "null" ? form.available.value == "true" : null,
      desiredCapacity: form.desiredCapacity.value,
      place: form.place.value,
      minDays: form.minDays.value,
      maxDays: form.maxDays.value
    }
    const queryParamsRouteValue = generateQueryParamsRoute(paramsValues)

    const fetchResponse = await handleRequest(TRAVEL_API_PREFIX + "/search" + queryParamsRouteValue, "GET", {}, setValue)
    setResponse(fetchResponse as Travel[])
  }
  
  return { response, makeRequest }
}