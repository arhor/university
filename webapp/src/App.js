import React, { useState, useEffect } from 'react'
import axios from 'axios'
import logo from './logo.svg'
import './App.css'

export default function App() {
  const [msg, setMsg] = useState('loading...')
  useEffect(async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/users/')
      console.log(res)
      setMsg(res.data)
    } catch (error) {
      console.error(error)
    }
  }, [])
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        <p>Message from server: {msg}</p>
      </header>
    </div>
  )
}
