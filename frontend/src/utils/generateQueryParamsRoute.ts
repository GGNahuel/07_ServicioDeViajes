export function generateQueryParamsRoute(params: object): string {
  let result = ""
  const entries = Object.entries(params)
  const values = Object.values(params)

  if (values.some(value => value && value != "")) {
    result += "?"
    let hasPreviousQueryValue = false
  
    entries.forEach(entry => {
      const [key, value] = entry
      const keyValueFormatted = `${key}=${value}`
      
      result += (value && value != "") ? 
        (hasPreviousQueryValue) ?
          "&" + keyValueFormatted : 
          keyValueFormatted : 
        ""
      
      if (!hasPreviousQueryValue) hasPreviousQueryValue = true
    })
  }

  return result
}