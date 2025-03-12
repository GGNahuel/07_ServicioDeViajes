export function getCookie(name: string) : string {
  const value = `; ${document.cookie}`
  const parts = value.split(`; ${name}=`)

  return parts.length === 2 ? parts[1] : ""
}