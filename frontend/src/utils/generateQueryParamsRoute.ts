export function generateQueryParamsRoute(params: object): string {
  let result = ""
  const entries = Object.entries(params)
  const values = Object.values(params)

  const valueIsNotNullOrEmpty = (value: unknown) => {
    return value != null && value !== ""
  }

  if (values.some(value => valueIsNotNullOrEmpty(value))) {
    result += "?"
    let hasPreviousQueryValue = false
  
    entries.forEach(entry => {
      const [key, value] = entry
      const keyValueFormatted = `${key}=${value}`
      
      result += (valueIsNotNullOrEmpty(value)) ? 
        (hasPreviousQueryValue) ?
          "&" + keyValueFormatted : 
          keyValueFormatted : 
        ""
      
      if (!hasPreviousQueryValue) hasPreviousQueryValue = true
    })
  }

  return result
}