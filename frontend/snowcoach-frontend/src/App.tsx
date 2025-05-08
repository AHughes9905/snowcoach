import { useState } from 'react'
import PreviewPage from './pages/PreviewPage'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <PreviewPage />
    </>
  )
}

export default App
