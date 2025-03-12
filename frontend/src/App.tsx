import { MessageContextProvider } from './contexts/MessageContext'
import { IndexPage } from './pages/index/IndexPage'
import { TravelsPage } from './pages/travels/TravelsPage'
import { csrfTokenGetter } from './requests/UserRequests'

function App() {
  csrfTokenGetter()

  return (
    <MessageContextProvider>
      <IndexPage />
      <TravelsPage />
    </MessageContextProvider>
  )
}

export default App
