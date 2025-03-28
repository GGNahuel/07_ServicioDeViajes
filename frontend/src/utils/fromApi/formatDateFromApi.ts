export function formatDate(date: [number, number, number]) : string {
  const year = date[0]
  const month = date[1]
  const day = date[2]

  return day.toString().padStart(2, "0") + "/" + month.toString().padStart(2, "0") + "/" + year 
}