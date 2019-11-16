import React, { useState, useEffect } from 'react'
import axios from 'axios'

export default function Home() {
  const [msg, setMsg] = useState(['loading...'])
  useEffect(() => {
    async function fetchData() {
      try {
        const { data } = await axios.get('http://localhost:8080/api/v1/langs')
        setMsg(data.map(lang => `${lang.label} `))
      } catch (error) {
        console.error(error)
      }
    }
    fetchData()
  }, [])
  return (
    <>
      {msg}
    </>
  )
}