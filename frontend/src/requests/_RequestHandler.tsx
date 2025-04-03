import { API_PREFIX } from "../const/ApiConstants"
import { MessageContextInterface } from "../contexts/MessageContext"
import { ApiMethods } from "../types/ApiTypes"
import { buildRequestBody } from "../utils/buildRequestBody"
import { CriticalError, WarnError } from "../utils/errorClasses"

type ReqBodyHeaders = Record<string, string> |& {
  "Content-Type": "application/x-www-form-urlencoded" | "application/json"
}

export async function handleRequest(
  relativePath: string, method: ApiMethods, {headers, objectToStringify, alternativeBody} : {
    headers?: ReqBodyHeaders,
    objectToStringify?: unknown, alternativeBody?: BodyInit
  }, contextSetter: MessageContextInterface["setValue"]
) {
  const requestBody = buildRequestBody({method, headers, alternativeBody, objectToStringify})

  try {
    const request = await fetch(API_PREFIX + relativePath, requestBody)
    console.log(request)

    let response;
    try {
      response = await request.json();
    } catch (jsonError) {
      console.warn("Error al parsear JSON:", jsonError);
      response = null; 
    }
    console.log(response);
    
    if (!request.ok) {
      if (request.status == 500)
        throw new CriticalError(response)
      throw new WarnError(response)
    }

    return response 
  } catch (error) {
    if (error instanceof CriticalError) {
      contextSetter({
        message: "Ocurrió un error inesperado, vuelva a intentar más tarde",
        type: "error"
      })
    }
    else {
      contextSetter({
        message: (error as Error).message,
        type: "warn"
      })
    }
  }
}
