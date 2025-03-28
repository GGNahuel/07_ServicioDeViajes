import { PayPLansType, Transports } from "../types/ApiTypes"

export const API_PREFIX = "http://localhost:8080/api"

export const transportTranslations: Record<Transports, string> = {
  BUS: "colectivo",
  PLANE: "avión",
  BOAT: "barco",
  TRAIN: "tren"
}

export const payPlansTranslations: Record<PayPLansType, string> = {
  family: "Familiar",
  friends: "Grupo de amigos",
  couple: "Pareja",
  individual: "Individual"
}