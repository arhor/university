import React, { useState, useEffect } from 'react'
import axios from 'axios'

export default function Home() {
  const [msg, setMsg] = useState('loading...')
  useEffect(async () => {
    try {
      const { data } = await axios.get('http://localhost:8080/api/users/')
      setMsg(data)
    } catch (error) {
      console.error(error)
    }
  }, [])
  return (
    <>
      {msg}
    </>
  )
}