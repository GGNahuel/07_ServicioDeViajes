import { MessageContextProvider } from './contexts/MessageContext'
import { IndexPage } from './pages/index/IndexPage'
import { TravelsPage } from './pages/travels/TravelsPage'

function App() {
  return (
    <MessageContextProvider>
      <IndexPage />
      <TravelsPage />
    </MessageContextProvider>
  )
}

export default App
