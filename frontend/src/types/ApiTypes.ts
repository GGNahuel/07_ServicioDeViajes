export type ApiMethods = "GET" | "POST" | "PUT" | "PATCH" | "DELETE"

enum Transports { PLANE, BUS, TRAIN, BOAT }
enum PayPLansType { friends, family, couple, individual }

export interface PopularTravel {
  name: string,
  description: string,
  placesNames: string[]
}

export interface Travel {
  id?: string,
  name: string,
  longInDays: number, 
  maxCapacity: number,
  currentCapacity?: number,
  isAvailable: boolean,
  availableDates: string[],
  rating?: number,

  destinies: Destiny[],
  payPlans: PayPlan[],
  reviews: Review[],
}

interface Destiny {
  place: string,
  leaveDay: number,
  returnDay: number,
  transport: Transports,

  stayPlaceId: string
}

interface PayPlan {
  price: number,
  planFor: PayPLansType
}

export interface Review {
  id?: string,
  userName: string,
  comment: string,
  rating: number,

  userImage: Image
}

export interface Image {
  id: string,
  name: string,
  contentType: string,
  data: string
}

export interface StayPlace {
  id?: string,
  from: string,
  name: string,
  description?: string
  rating: number
}

export interface Request {
  id?: string,
  selectedPlan: PayPLansType,
  persons: Person,
  email: {
    id: string,
    email: string,
    owner: string
  },
  amountPaid: number,
  totalPrice: number,

  associatedTravel: string,
  selectedDate: string
}

interface Person {
  name: string,
  age: number,
  identificationNumber: number,
  contactPhone: number
}

export interface User {
  id?: string,
  username: string,
  password: string
}