export function formatDate(date: [number, number, number]) : string {
  const year = date[0]
  const month = date[1]
  const day = date[2]

  return day.toString().padStart(2, "0") + "/" + month.toString().padStart(2, "0") + "/" + year 
}

export function formatDateToApi(date: string) : [number, number, number] {
  const array = date.split("/")

  return [Number(array[2]), Number(array[1]), Number(array[0])]
}