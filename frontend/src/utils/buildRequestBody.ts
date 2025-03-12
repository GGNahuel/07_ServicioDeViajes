import { ApiMethods } from "../types/ApiTypes"
import { getCookie } from "./cookieGetter"

export function buildRequestBody(
  {method, headers, alternativeBody, objectToStringify} :
  {method: ApiMethods, headers?: Record<string, string>, alternativeBody?: BodyInit, objectToStringify?: unknown}
) : RequestInit {
  const requestBody: RequestInit = {
    method: method,
    headers: {
      ...headers
    },
    body: alternativeBody ? alternativeBody : objectToStringify ? JSON.stringify(objectToStringify) : undefined,
    credentials: "include"
  }

  if (method != "GET" && !headers?.["Content-Type"])
    requestBody.headers = {...requestBody.headers, "Content-Type": "application/json"}
  if (getCookie("XSRF-TOKEN") != "") {
    requestBody.headers = {
      ...requestBody.headers,
      "X-XSRF-TOKEN" : getCookie("XSRF-TOKEN")
    }
  }

  return requestBody
}