export type ApiMethods = "GET" | "POST" | "PUT" | "PATCH" | "DELETE"

export interface PopularTravel {
  name: string,
  description: string,
  placesNames: string[]
}