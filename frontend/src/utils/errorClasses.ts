export class CriticalError extends Error {
  constructor(message: string) {
    super(message)
    this.name = "Critical_Error"
  }
}

export class WarnError extends Error {
  constructor(message: string) {
    super(message)
    this.name = "Warn_Error"
  }
}
