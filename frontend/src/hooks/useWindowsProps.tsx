import { useEffect, useState } from "react";

export function useWindowProps() {
  const [windowsProps, setWindowsProps] = useState<{width: number}>({width: window.innerWidth})

  useEffect(() => {
    const handleResize = () => {
      setWindowsProps(prev => ({
        ...prev,
        width: window.innerWidth
      }))
    }
    handleResize()

    window.addEventListener("resize", handleResize)

    return () => {
      window.removeEventListener("resize", handleResize)
    }
  }, [])

  return windowsProps
}