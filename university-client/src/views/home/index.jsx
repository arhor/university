import React, { useState, useEffect } from 'react';
import axios from 'axios';
import HomeService from './service/HomeService.js';

const service = new HomeService();

export default function Home() {
  const [langs, setLangs] = useState(['loading langs...']);
  const [roles, setRoles] = useState(['loading roles...']);

  useEffect(() => {
    service.fetchLangs().then(res => setLangs(res));
    service.fetchRoles().then(res => setRoles(res));
  }, []);

  return (
    <>
      <h2>Welcome to the University App</h2>

      {langs}
      <br />
      {roles}
    </>
  );
}